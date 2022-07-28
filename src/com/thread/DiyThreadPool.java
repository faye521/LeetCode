package com.thread;

/**
 * @author faye
 * @className DiyThreadPool
 * @Description TODO
 * @Date 2022/7/26 10:21
 * @Version 1.0
 */

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 自定义一个简易版线程池
 * 1.编写任务类MyTask 实现Runnable接口
 * 2.编写线程类MyWorker，用于执行任务,需要持有任务
 * 3.编写线程池MyThreadPool1 包含提交任务，执行任务的能力
 * 4.测试
 */

/**
 * 实际流程:(随着任务量的增加)
 * 1.首先增加核心线程数的数量来提升处理任务的能力
 * 2.核心线程满了之后，把任务存放到阻塞任务队列里面
 * 3.当阻塞队列满了，开始创建非核心线程立即执行一个任务(此时执行任务速度加快，队列可能会空出来)
 * 4.当阻塞队列再次满的时候，尝试创建非核心线程(因为非核心线程有生命周期，所以队列满的时候不能确定此时非核心线程的数量)失败，执行拒绝策略
 * end:由于生命周期和第2步不太容易模拟，所以本次测试直接一次性拉满所有的线程数量
 */

public class DiyThreadPool {
    /**
     * 成员变量
     * 1.任务队列 2.当前线程数量 3.核心线程数量 4.最大线程数量
     * 5.任务队列长度
     */
    public static void main(String[] args) {
        /**
         * 测试
         * 1.创建线程池类，
         * 2.提交多个任务
         */
//        1.创建线程池
        MyThreadPool1 pool = new MyThreadPool1(2,4,20);
//        2.提交多个任务,根据任务多少i,可以观察到线程池中的不同状态
        //当任务量超过核心线程数时，创建非核心线程；超过非核心现场时，进入任务队列；超过任务队列时，拒绝。
        for(int i=0;i<30;i++){
            MyTask mt = new MyTask(i);
            pool.submit(mt);
        }

    }

}
//核心线程池类----------------------------------------------------
class MyThreadPool1{
    /**
     * 成员变量
     * 1.任务队列 2.当前线程数量 3.核心线程数量 4.最大线程数量
     * 5.任务队列长度
     */
    //创建控制线程安全的任务队列
    private List<MyTask> tasks = Collections.synchronizedList(new LinkedList<>());
    //    当前线程数量,int类型可以省略初始化默认是0，如果是integer就必须赋值为0
    private int num;
    //    核心线程数量
    private Integer corePoolSize;
    //    最大线程数量
    private Integer maxPoolSize;
    //    任务队列长度
    private Integer workSize;
    //
    public MyThreadPool1(Integer corePoolSize,Integer maxPoolSize,Integer workSize){
        this.corePoolSize=corePoolSize;
        this.maxPoolSize=maxPoolSize;
        this.workSize=workSize;
    }
    //1.提交任务,将Mytask添加到任务队列,假如任务队列未满，则添加，否则执行拒绝策略
    public void submit(MyTask r){
        //判断任务队列tasks中的数量是否超出最大任务数量workSize
        //--实际上任务队列满了的时候，需要判断一下正在运行的线程数是否是最大线程数，是的话执行拒绝策略，否则的话应该是
        //创建一个非核心线程执行任务，这里的流程不是很正确
        if(tasks.size()>=workSize){
            System.out.println("任务"+r+"被丢弃了");
        }else{
            //每添加一次任务，(异步)判断一下是否需要新的线程
            tasks.add(r);
            //执行任务
            execTask(r);
        }
    }
    //2.执行任务，实际上设计的线程类使用while循环使得每一个线程都有连续复用的能力，
    //所以当任务量超出当前线程量的处理能力时，才会执行execTask，尝试产生新的线程
    private void execTask(MyTask r) {
        //判断当前线程池中的线程数量，是否超出了核心线程数量
        if(num<corePoolSize){
            //tasks中保存着任务队列，每次执行任务的时候，开启工作线程从tasks中取出队头MyTask
            //小于核心线程总量，创建核心线程。
            new MyWorker("核心线程:"+num,tasks).start();
            num++;
        }else if(num<maxPoolSize){
            //核心线程满足不了时，创建非核心线程
            new MyWorker("非核心线程:"+num,tasks).start();
            num++;
        }else{
            //这里的逻辑是非核心线程也满足不了时，放入缓存队列，把队列当做最后的手段
            //实际上应该是核心线程满足不了时，就开始放入缓存队列，当缓存队列满时，尝试创建非核心线程
            //也就是说应该在75行判断下，如果非核心线程未满就立即创建一个非核心线程获取这个任务
            //但是在只有核心线程的时候，放满缓存队列，容易直接报拒绝策略的错，这里就偷下懒，直接把非核心线程创建了。
            System.out.println("任务"+r+"被缓存了");
        }
    }
}
//任务类------------------------------------------------------------
class MyTask implements Runnable{
    private Integer id;
    public MyTask(Integer id){
        this.id = id;
    }
    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.println("线程："+name+" 即将执行任务"+id);
        try {
            //每个线程休眠200毫秒，表示每个任务需要0.2s处理的时间
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程："+name+" 完成了任务"+id);
    }

    @Override
    public String toString() {
        return "MyTask{id="+id+"}";
    }
}
//线程类-----------------------------------------------------------------------
class MyWorker extends Thread{
    //把name注释掉 不影响获取线程名字，应该是Thread内置了一个别的name属性
//    private String name;
    private List<MyTask> tasks;//保存任务, 任务队列
//    构造方法赋值
    public MyWorker(String name,List<MyTask> tasks){
        //这里需要使用父类的构造方法，否则获取不到，推断父类构造方法获取的不是成员变量name
        super(name);
        this.tasks=tasks;
    }
    @Override
    public void run() {
        //判断是否有任务，有的话就执行，有任务时，单线程能连续工作，模拟线程池中线程不销毁的操作
        while(tasks.size()>0){
            //如果有任务，就从队头取出一个任务
            MyTask r = tasks.remove(0);
            //这里是MyTask 还没有启动线程
            r.run();
        }
    }
}
