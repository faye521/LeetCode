package com.thread;

/**
 * @author faye
 * @className Thread3
 * @Description TODO
 * @Date 2022/8/31 9:25
 * @Version 1.0
 */

/**
 * 创建两个线程，一个只打印奇数，一个只打印偶数，无关顺序
 */
public class Thread3 {
    static int[] nums={1,3,4,5,6,8,9,11,12,14,15,17,18,19,21};
    static int i=0;
    public static void main(String[] args) {
        Object o = new Object();
        //两个线程交替打印奇偶数
        new Thread(
                ()->{
                    while(i<nums.length){
                        synchronized(o){
                            //如果下一个数是偶数，唤醒其他的线程,自己进入等待
                            if(nums[i]%2==0){
                                o.notify();
                                try{
                                    o.wait();
                                }catch(Exception e){

                                }
                            }else{
                                //如果下一个数是奇数，准备就绪，等待被其他线程唤醒
                                String name=Thread.currentThread().getName();
                                System.out.println(name+"拿到了"+nums[i++]);
                            }


                        }
                    }
                },"奇数线程"
        ).start();
        new Thread(
                ()->{
                    while(i<nums.length){
                        synchronized(o){
                            //如果下一个数是奇数，唤醒其他的线程,自己进入等待
                            if(nums[i]%2!=0){
                                o.notify();
                                try{
                                    o.wait();
                                }catch(Exception e){

                                }
                            }else{
                                //如果下一个数是偶数，准备就绪，等待被其他线程唤醒
                                String name=Thread.currentThread().getName();
                                System.out.println(name+"拿到了"+nums[i++]);
                            }


                        }
                    }
                },"偶数线程"
        ).start();

    }
}
