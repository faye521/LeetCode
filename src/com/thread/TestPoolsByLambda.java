package com.thread;

import java.util.concurrent.*;

/**
 * @author faye
 * @className TestPoolsByLambda
 * @Description TODO
 * @Date 2022/7/28 15:17
 * @Version 1.0
 */
public class TestPoolsByLambda {
    static int EsThreadCount =1;
    static int EsThreadCount1 =1;
    static int RunnableCount=1;
    static int RunnableCount1=1;
    public static void main(String[] args) {
       //原生线程池方式
        ThreadPoolExecutor es = new ThreadPoolExecutor(2, 4, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(6),
        (r)-> new Thread(r,"手动线程池-线程"+ EsThreadCount++)
        ,new ThreadPoolExecutor.AbortPolicy());
        //开启几个任务试一下
        for(int i=1;i<=10;i++){
//            es.submit(new MyRunnable3(i));
            es.submit(
                    ()->{
                        //int n=1;//使用lambda函数，想区分一下的话必须使用全局变量。不能识别局部变量的变化不知道为啥，有空看看源码吧
                        String name = Thread.currentThread().getName();
                        System.out.println(name+"准备执行任务"+RunnableCount++);
                    }
            );
        }

        es.shutdown();
        //工厂创建线程池的方式
        ExecutorService es1 = Executors.newFixedThreadPool(4, (r)-> new Thread(r,"固定线程池-线程"+ EsThreadCount1++));
        //开几个任务
        for(int i=1;i<=9;i++){
            es1.submit(
                    ()->{
                        String name = Thread.currentThread().getName();
                        System.out.println(name+"准备执行任务"+RunnableCount1++);
                    }
            );
        }
        es1.shutdown();


    }
}
//创建一个任务类
//class MyRunnable3 implements Runnable{
//    int id=1;
//    public MyRunnable3(int id){
//        this.id=id;
//    }
//    @Override
//    public void run() {
//        String name = Thread.currentThread().getName();
//        System.out.println(name+" :准备执行任务"+id);
//    }
//}
