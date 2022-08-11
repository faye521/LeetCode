package com.test;

import java.util.Map;
import java.util.Scanner;

/**
 * @author faye
 * @className 网易2021测开1
 * @Description TODO
 * @Date 2022/8/11 15:18
 * @Version 1.0
 */

/**
 * 输入一个只包含大小写字母的字符，该字符串可能不是回文，在字符串末尾可添加任意字符使其成为回文
 * 输出可得到的最小长度回文  输入的字符长度至少为1
 * eg: noon  noon
 * eg: mak   makam
 * eg: dadkmk  dadkmkdad
 */
public class 网易2021测开1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str = sc.next();
        //在str尾部加入最少的字符将其变成一个回文字符,要求结果最短
        //stringgni  stringgni rts
        // mjkak jm
        //aabbmjk kjmbbaa
        //暴力一下试试
        //从后往前遍历，找到str末尾最长的回文长度，例如mjkak  找到kak为满足后缀的最长回文子串
        int len=0;
        for(int i=str.length()-1;i>=0;i--){
            len=Math.max(len,huiWenLenth(str,i,i));
            len=Math.max(len,huiWenLenth(str,i-1,i));
            System.out.println("回文长度:"+len);
        }
        //需要补全的长度
        int blen=str.length()-len;
        String res = "";
        for(int i=blen-1;i>=0;i--){
            res+=str.charAt(i);
        }
        str+=res;
        System.out.println(str);

    }
    //从lo hi往两端扩散 符合回文的长度
    public static int huiWenLenth(String str,int lo,int hi){
        int length=0;
        //符合回文时扩散，直到不符合回文，或者lo=-1 hi=str.length() ,hi为有效值
        while(lo>=0&&hi<str.length()){
            //当不符合回文的时候 结束
            if(str.charAt(lo)!=str.charAt(hi)) break;
            lo--;hi++;
        }
        //当hi==str.length()时，这部分回文才有效
        if(hi==str.length()) length=hi-lo-1;
        return length;
    }
}
