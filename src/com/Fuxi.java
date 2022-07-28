package com;

import java.util.*;
import java.util.concurrent.*;

/**
 * @author faye
 * @className Fuxi
 * @Description TODO
 * @Date 2022/7/28 8:49
 * @Version 1.0
 */
public class Fuxi {
    public static void main(String[] args) {
        //开启线程池,可以添加线程工厂给线程命名一下
       ThreadPoolExecutor es = new ThreadPoolExecutor(3, 4, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2), new ThreadFactory() {
           private int n=1;
           @Override
           public Thread newThread(Runnable r) {
               return new Thread(r,"手动开启的线程"+n++);
           }
       },new ThreadPoolExecutor.AbortPolicy());
       //执行任务,因为任务秒添加，线程池同时最多只能处理6个任务(4个线程+2个任务队列位置),所以同时处理超过6个任务就会执行拒绝策略
        //所以这里单独开启一个线程分批添加任务
        new Thread(){
            @Override
            public void run(){
                for(int i=1;i<=10;i++){
                    es.submit(new MyTask(i));
                    //每添加一个任务让浅浅地睡一会
                    try {
                        Thread.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();



    }
}
//任务类
class MyTask implements Runnable{
    private int id;
    public MyTask(int id){
        this.id=id;
    }
    @Override
    public void run(){
        String name = Thread.currentThread().getName();
        System.out.println(name+"正在运行任务"+id);
    }
}

