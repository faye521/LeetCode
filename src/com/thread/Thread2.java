package com.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author faye
 * @className Thread2
 * @Description 创建线程的2种方法
 * @Date 2022/7/26 9:38
 * @Version 1.0
 */
public class Thread2 {
    public static void main(String[] args) {
//实现接口的可以直接传入线程名，继承的不行，CurrentThread().getName()获取不到自己重写的构造方法里的Name 只能使用setName
        MyThread3 t1 = new MyThread3("继承生成的线程");
//        t1.setName("这么原始的命名方案吗");
        t1.start();
//调用Runnable实现类
        MyRunnable1 mr = new MyRunnable1();
        Thread t2 = new Thread(mr,"实现Runnalbe的线程");
        t2.start();
        //简洁写法
        new Thread(new MyRunnable1(), "实现Runnalbe的线程1").start();
//调用callable
        MyCallable mc = new MyCallable();
        FutureTask<Integer> ft = new FutureTask<>(mc);
        Thread t3 = new Thread(ft, "callable实现的线程");
        t3.start();
        //简洁写法
        new Thread(new FutureTask<Integer>(new MyCallable()),"callable实现的线程2").start();
        try {
            System.out.println(ft.get());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

//第一种 继承Thread类
class MyThread3 extends Thread {
    public MyThread3(){

    }
    public MyThread3(String name){
        super(name);
    }
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + ":继承Thread生成线程，不方便做扩展，不是很推荐这种方法");
    }
}

//第二种 实现runnable接口
class MyRunnable1 implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + ":多实现比单继承更为灵活");
    }
}

//第二种 实现Callable接口 callable继承runnable
class MyCallable implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName() + ":可以返回一个值,比如说状态码:");
        return 500;
    }
}
