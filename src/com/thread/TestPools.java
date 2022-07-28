package com.thread;

import java.util.List;
import java.util.concurrent.*;

/**
 * @author faye
 * @className TestPools
 * @Description
 * Java内置线程池：ExecutorService、ScheduledExecutorService两个接口,使用Executors工厂创建线程池的方法
 * ThreadPoolExecutor手动创建线程池方法
 * @Date 2022/7/23 11:11
 * @Version 1.0
 */

public class TestPools {
    public static void main(String[] args) {
//        test1();
//            test2();
//        test3();
//        test4();
//        test5();
        test6();
//        test7();


    }

    /**
     * ExecutorService的3种获取线程池的方法:
     * 1.newFixedThreadPool 2.newSingleThreadExecutor 3.newCachedThreadPool
     * ScheduledExecutorService是ES的子接口，具有延期定时执行的能力,对应2种获取线程池的方法：
     * 1.newScheduledThreadPool 2.newSingleThreadScheduledExecutor，似乎没有缓存线程对应的定时版
     */
    //ES1:限制固定的线程数，性能比较中庸，服务器压力小
    public static void test1() {

        //1.工厂方法创建一个可重用固定线程数的线程池，必须指定线程的数量,可以选择是否传入一个线程工厂参数
                ExecutorService service = Executors.newFixedThreadPool(5, new ThreadFactory() {
            int n = 1;

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "自动线程" + n++ + ":");
            }
        });
        //2.提交任务
        for (int i = 1; i <= 10; i++) {
            service.submit(new MyTask1(i));
        }
        //3.关闭线程池，仅仅是不再添加新的任务(不能再调用submit)，任务队列中剩余的任务仍然会继续执行
        service.shutdown();
    }

    //ES2:单线程线程池安全度较高，对比普通线程也节省了创建和销毁线程的开销
    public static void test2() {
        //1.工厂方法创建线程池，只包含1个线程,可以无参，或者传入一个线程工厂
        ExecutorService service = Executors.newSingleThreadExecutor(
                new ThreadFactory() {
                    int n = 1;

                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r, "单例线程" + n++ + ":");
                    }
                });
        //2.提交任务
        for (int i = 1; i <= 10; i++) {
            //把任务添加到任务队列队尾，线程池从队头执行任务队列中的任务
            service.submit(new MyTask1(i));
        }
        //3.立即关闭线程池，任务队列中没有执行的任务会直接取消，并可以通过这个方法的返回值接收这些方法
        List<Runnable> list = service.shutdownNow();
        System.out.println(list.toString());


    }

    //ES3:缓存线程池任务优先，线程的数量不做限制，给每一个任务新创建时都会配一个线程，活动的线程数量永远大于等于任务数量
    //服务器性能优越时采用这种方式.由于任务队列和线程双双无限制，更加容易OOM
    public static void test3() {
        //1.工厂方法创建缓存线程池，可以无参，或者传入一个线程工厂
        ExecutorService service = Executors.newCachedThreadPool(
                new ThreadFactory() {
                    int n = 1;

                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r, "缓存线程" + n++ + ":");
                    }
                });
        //2.提交任务
        for (int i = 1; i <= 10; i++) {
            //把任务添加到任务队列队尾，线程池从队头执行任务队列中的任务
            service.submit(new MyTask1(i));
        }
        //3.立即关闭线程池，任务队列中没有执行的任务会直接取消，并可以通过这个方法的返回值接收这些方法
        //因为是性能优先，所以任务队列中是不会存在等待的任务的
        List<Runnable> list = service.shutdownNow();
        System.out.println(list.toString());


    }
    //定时固定大小的线程池
    public static void test4() {
        ScheduledExecutorService es = Executors.newScheduledThreadPool(5, new ThreadFactory() {
            int n = 1;

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "定时固定线程" + n++ + ":");
            }
        });
        //延迟2秒后，提交执行一系列任务(1次)
        for (int i = 1; i <= 10; i++) {
            //schedule提交执行任务
            es.schedule(new MyTask1(i), 2, TimeUnit.SECONDS);
        }
        System.out.println("应该是先输出这句话，然后延迟2秒后执行任务，执行任务时不会延迟，只会执行睡眠的时间");
    }
    //定时单例的线程池
    public static void test5() {
        ScheduledExecutorService es = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
            int n = 1;

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "定时单例线程" + n++ + ":");
            }
        });
        //执行一系列任务，一系列任务算一个任务
        for (int i = 1; i <= 10; i++) {
            //schedule提交执行任务,初始等待1秒，以后每次等待2秒，每次等待2秒包含线程处理任务的时间(假如处理任务超过2s，就不再等了)
            //这个方法会创建多个任务对象，也就是说会一直重复提交这个任务，提交执行一个(系列)任务(无数次)
//            es.scheduleAtFixedRate(new MyTask1(i),1,2,TimeUnit.SECONDS);
            //对比方法,和scheduleAtFixedRate功能一致。只不过2s等待从上一个线程任务执行结束开始算起，也就是至少等2S。循环内等待1.5S休眠不会执行这个2s延迟，两个重复循环之间等待1.5+2S
            /**
             * 这两个方法创建多个任务对象，以for为单位
             * 两个方法对比，在休眠1.5S的情况下(线程执行1.5S的情况下)，schedule循环内休眠(运行)1.5S,不等待。两个循环交接时等待2s(等待和开始运行同时记录时间)
             * scheduleWithFixedDelay:循环内休眠(运行)1.5S,不等待。两个for交接时，等待1.5S+2S,也就是上一个任务对象完全执行结束之后，才开始定时延迟
             */
            es.scheduleWithFixedDelay(new MyTask1(1),1,2,TimeUnit.SECONDS);

        }
        System.out.println("应该是先输出这句话，然后延迟1秒后执行任务，每次任务最多等待2s");
    }
    //线程池提交Callable而非Runnable可获取异步计算的结果。
    public static void test6(){
        //1.创建一个大小为5的线程池
        ExecutorService es = Executors.newFixedThreadPool(5);
        //2.创建Callable类型的任务对象,提交之后会有一个返回值
        Future<Integer> f = es.submit(new MyCallable1(2,3));
        //3.判断任务是否完成
        boolean done=f.isDone();
        System.out.println("第一次判断任务是否完成"+done);
        boolean cancelled = f.isCancelled();
        System.out.println("第一次判断任务是否取消"+cancelled);
//        4.单独测试,取消任务
//        Boolean b = f.cancel(true);
//        System.out.println("任务取消的结果"+b);
        try {
            //异步情况下，默认会无期限地等待处理结果，所以需要处理一下异常
            //可以选择是否给定一个参数，比如说只等待3S,可以无参
            Integer v = f.get(3,TimeUnit.SECONDS);
            System.out.println("任务执行的结果是："+v);
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean done2=f.isDone();
        System.out.println("第2次判断任务是否完成"+done2);
        boolean cancelled2 = f.isCancelled();
        System.out.println("第2次判断任务是否取消"+cancelled2);


    }

    //重点：手动创建线程池
    public static void test7() {
//        1.手动创建一个线程池, 最大线程数+任务队列数 约束处理任务的数量。前几个方法任务队列时无界的，当任务量较大的时候，大量的下发通知积压，会导致OOM
        //可以使用合适的拒绝策略降低损失，避免OOM
        //在这个秒级处理的情况下，最大线程数+任务队列大小=可以放置的任务
        ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 7, 10, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(3),
                new ThreadFactory() {
                    int n = 1;

                    @Override
                    public Thread newThread(Runnable r) {
                        Thread t = new Thread(r, "线程池中手动创建的线程" + n++);
                        return t;
                    }
                }, new ThreadPoolExecutor.AbortPolicy());
        //2.提交任务
        for (int i = 1; i <= 10; i++) {
            //把任务添加到任务队列队尾，线程池从队头执行任务队列中的任务
            executor.submit(new MyTask1(i));
        }
    }
}

//任务对象
class MyTask1 implements Runnable {
    private int id;

    public MyTask1(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "执行了任务：" + id);
    }

    @Override
    public String toString() {
        return "MyTask{" + id + "}";
    }
}
//test5 计算任务
class MyCallable1 implements Callable<Integer>{
    private int a;
    private int b;
    //通过构造方法传递两个数
    public MyCallable1(int a,int b){
        this.a = a;
        this.b = b;
    }
    @Override
    public Integer call() throws Exception {
        String name = Thread.currentThread().getName();
        System.out.println(name+"准备开始计算...");
        Thread.sleep(2000);
        System.out.println("计算完成");
        return a+b;
    }
}
