package com.design;


/**
 * @author faye
 * @className SingletonTest
 * @Description TODO
 * @Date 2022/8/8 16:42
 * @Version 1.0
 */

/**
 * 单例模式 顾名思义 最多只会存在一个全局实例对象(所以单例类内部必须私有静态常量化这个实例对象保证唯一)
 * 1.单例模式只能有一个实例对象
 * 2.单例类必须自己创建自己的唯一实例
 * 3.单例类必须给所有其他对象提供这一实例
 * 所以可以分为5种  自行实例化的是饿汉式，需要调用方法或者属性后才实例化的是懒汉式
 * 1.饿汉式 2.枚举饿汉式
 * 3.DCL懒汉式 4.双检锁懒汉式 5.内部类懒汉式
 *
 */
public class SingletonTest{
    public static void main(String[] args) throws Exception{
        Singleton instance = Singleton.getInstance();
        SingleEnum singleEnum = SingleEnum.getInstance();
        Singleton1 instance1 = Singleton1.getInstance();
        Singleton2 instance2 = Singleton2.getInstance();
        Singleton3 instance3 = Singleton3.getInstance();
        System.out.println("饿汉式："+instance.getClass()+"||"+instance.toString());
        System.out.println("枚举饿汉式"+singleEnum.getClass()+"||"+singleEnum.toString());
        System.out.println("普通懒汉式:"+instance1.getClass()+"||"+instance1.toString());
        System.out.println("线程安全(DCL)懒汉式:"+instance2.getClass()+"||"+instance2.toString());
        System.out.println("双检锁懒汉式:"+instance3.getClass()+"||"+instance3.toString());
        //内部类懒汉式, 当调用内部类的该属性时才会实例化
        System.out.println("内部类懒汉式:"+GetInstance.singletonTest.getClass()+"||"+GetInstance.singletonTest.toString());
    }

    private SingletonTest(){

    }
    //5.内部类懒汉式
    private static class GetInstance{
        private static final  SingletonTest singletonTest = new SingletonTest();
    }

}
//饿汉式
class Singleton{
    //因为是静态常量，所以在类初始化的时候就已经自行实例化了
    private static Singleton singleton = new Singleton();
    private Singleton(){

    }
    public static Singleton getInstance(){
        return singleton;
    }

}
//枚举饿汉式
enum SingleEnum{
    //枚举类自带public static final属性,可以模拟单例
    Instance;
    public static SingleEnum getInstance(){
        return Instance;
    }


}
//懒汉式
class Singleton1{
    //单例不会自行实例化，只有在第一次调用方法的时候才会实例化
    private static Singleton1 singleton1=null;
    private Singleton1(){

    }
    public static Singleton1 getInstance(){
        if(singleton1==null){
            singleton1=new Singleton1();
        }
        return singleton1;
    }

}
//以上两种都是单线程，接下来写两种以懒汉式为基础的线程安全的写法

//考虑线程安全的写法
class Singleton2{
    private static Singleton2 singleton2= null;
    private Singleton2(){

    }
    public static Singleton2 getInstance(){
        //程序加锁，只能单线程访问,先加上锁再判断单例是否需要创建
        synchronized(Singleton2.class){
            if(singleton2==null){
                singleton2=new Singleton2();
            }
        }
        return singleton2;
    }
}
//双重线程检查的写法

/**
 * volotaile讲解
 * https://blog.csdn.net/J169YBZ/article/details/119151121?spm=1001.2101.3001.6661.1&utm_medium=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-1-119151121-blog-80682208.t5_layer_eslanding_A_0&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-1-119151121-blog-80682208.t5_layer_eslanding_A_0&utm_relevant_index=1
 */
class Singleton3{
    //添加volatile保证原子操作的可见性
    private static volatile Singleton3 singleton3= null;
    private Singleton3(){

    }
    public static Singleton3 getInstance(){
        //在获取锁之前先判断一下单例是否已经存在(如果已经存在就不需要再获取锁了),可以提高线程安全的效率
        if(singleton3==null){
            synchronized(Singleton3.class){
                if(singleton3==null){
                    singleton3=new Singleton3();
                }
            }
        }
        return singleton3;
    }
}



