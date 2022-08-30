package com.javase;

/**
 * @author faye
 * @className InternTest
 * @Description TODO
 * @Date 2022/8/30 17:42
 * @Version 1.0
 */
public class InternTest {
    public static void main(String[] args) {
        /**
         *
            *  str是一个字符串
         * 表明结论：
             * str.intern() 返回的是一个对象,返回字符串常量池中equals这个str的对象(常量池中相同的数据只会保存一份)
         * 作用：
            * JDK1.6 如果字符串常量池中不包含str，就把str的实例复制进字符串常量池中(相当于是常量池中新创建的一个独立的对象)
            * 然后返回在字符串常量池中的引用
            * str.intern()==str 无论常量池中是否包含str 都不为true 因为 intern指向常量池
            * JDK1.7 如果字符串常量池中不包含str，那么就在常量池中记录一下首次出现的实例引用即可(如果常量池中不为空，一定是堆里的首次对象)
            * 然后返回在字符串常量池中的引用，其实就是堆中首次出现的实例引用
            * 因为1.7之后常量池并入了堆中，所以就不存在把堆中实例复制进常量池中这个操作了
            * str.inter()==str 如果常量池中不包含str 就为true
         *扩展：
            * 所以1.7之后 可以用str.intern()==str检测字符串常量池中是否是第一次出现在常量池中，
            * 1.6之前的这个判断式没有什么意义
            * 因为1.6堆里的对象和常量池中的地址一定不会相等
         */

        //--------------------字符串常量池----------------------------------------
        String str0="xixi";
        String str0c="xixi";
        //字符串常量池缓存了str0
        System.out.println(str0==str0c);

        String str=new String("haha");
        String strc="haha";
        //因为str是新创建了一个对象，肯定触发不了常量池
        System.out.println(str==strc);
        /**
         * intern()
         */
        System.out.println("-----------------intern()----------------------------");
        String str1=new StringBuilder("hel").append("lo").toString();
        //str1第一次出现,所以为true +的优先级比==要高,如果不加()就判断前面一堆和str1比较了
        System.out.println("str1.intern()==str1:"+(str1.intern()==str1));

        String str2="虚拟机";
        String str3=new StringBuilder("虚拟").append("机").toString();
        //str2是才第一次出现的，所以相当于str2==str3,
        System.out.println("str3.intern()==str3:"+(str3.intern()==str3));
        System.out.println("str3.intern()==str2:"+(str3.intern()==str2));

        String str4=new StringBuilder("ja").append("va").toString();
        //在程序启动时，jvm就会装载很多类，创建很多字符常量了,所以一般的敏感词已经存在了
        System.out.println("str4.intern()==str4:"+(str4.intern()==str4));


    }
}
