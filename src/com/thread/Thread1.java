package com.thread;

/**
 * @author faye
 * @className Thread1
 * @Description 模拟一下利用同步代码块，使用两个线程交替打印奇数和偶数
 * @Date 2022/7/23 8:49
 * @Version 1.0
 */
public class Thread1 {
    public static void main(String[] args) {
        //用两个线程交替奇数和偶数
        MyRunnable mr = new MyRunnable();
        new Thread(mr,"偶数线程").start();
        new Thread(mr, "奇数线程").start();

    }



}
class MyRunnable implements Runnable{
    int nums = 30;
    @Override
    public void run(){
        while(nums>0){
            //同步的是这个类的实例化对象，所以实例化的线程需要用到同一个MyRunnable对象
            synchronized (this){
                System.out.println(Thread.currentThread().getName()+"获取到了"+nums--);
                //阻塞之前唤醒其他线程
                this.notify();
                try{
                    //线程进入阻塞
                    this.wait();
                }catch(Exception e){
                    System.out.println();
                }
            }
        }
    }
}
