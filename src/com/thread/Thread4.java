package com.thread;

/**
 * @author faye
 * @className Thread4
 * @Description TODO
 * @Date 2022/8/31 9:29
 * @Version 1.0
 */

/**
 * 创建两个线程，一个只打印数字，一个只打印字母
 */
public class Thread4 {
    static String str = "12java345C6jk90";
    static int i=0;
    public static void main(String[] args) {
        Object o=new Object();
        new Thread(
                ()->{
                    while(i<str.length()){
                        synchronized (o){
                            char c = str.charAt(i);
                            if(c>='0'&&c<='9'){
                                //如果是数字
                                String name=Thread.currentThread().getName();
                                System.out.println(name+"拿到了"+c);
                                i++;
                            }else{
                                //如果不是数字，唤醒其他线程，自己进入阻塞
                                o.notify();
                                try{
                                    o.wait();
                                }catch (Exception e){

                                }
                            }
                        }
                    }
                },"数字线程"
        ).start();
        new Thread(
                ()->{
                    while(i<str.length()){
                        synchronized (o){
                            char c = str.charAt(i);
                            if(c>='0'&&c<='9'){
                                //如果是数字，唤醒其他线程，自己进入阻塞
                                o.notify();
                                try{
                                    o.wait();
                                }catch (Exception e){

                                }
                            }else{
                                //如果不是数字，输出
                                String name=Thread.currentThread().getName();
                                System.out.println(name+"拿到了"+c);
                                i++;

                            }
                        }
                    }
                },"字母线程"
        ).start();

    }
}
