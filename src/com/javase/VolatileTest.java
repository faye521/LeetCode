package com.javase;

/**
 * @author faye
 * @className VolatileTest
 * @Description TODO
 * @Date 2022/8/8 19:06
 * @Version 1.0
 */


import java.util.concurrent.TimeUnit;

/**
 * Java同步机制:
 * 1.Synchronized重量锁
 * 2.volatile轻量锁
 */
public class VolatileTest {
    public static void main(String[] args) {
        Shop shop = new Shop();
        //可见性测试
//        new Thread(()->{
//            System.out.println("线程A初始化");
//            try {
//                TimeUnit.SECONDS.sleep(3);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            shop.saleOne();
//            System.out.println("线程A购买商品完成，剩余商品量："+shop.a);
//        },"线程A").start();
//
//        while (shop.a == 1){
//
//        }
//
//        System.out.println("主线程，剩余商品量："+shop.a);
        //原子性测试 volatile不保证原子性
        for(int i = 0; i < 188;i++){
            new Thread(()->{
                shop.addGoods();
            }).start();
        }

        //保证所有20个线程都跑完，只剩下2个线程（主线程和GC线程）的时候代码才继续往下走
        //其中 Thread.yield() 方法表示主线程不执行，让给其他线程执行
        while (Thread.activeCount() >2){
            Thread.yield();
        }
        //经过多次运行测试，会出现结果不为189的情况，这就是volatile不保证原子性，
        //两个线程同时获取到a的值，a++不是原子操作，所以有可能被其他线程从中间插入，导致某一个线程提交了无用的结果
        System.out.println("如果保证了原子性，应该的结果是本来的1+188 = 189，但实际的值："+shop.a);
    }

}
class Shop{
//    int a = 1;
    //使用volatile关键字，可以在一个线程对该属性执行写操作之后立刻强行将线程本地工作内存中该属性的值
    //刷新到主内存，并使得其他线程读取的属性无效(其他线程则会刷新，重新获取这个值)
    volatile int a = 1;
    public void addGoods(){
        a++;
    }
    public void saleOne(){
        this.a = a-1;
    }
}


