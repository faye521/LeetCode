package com;


import java.util.*;

/**
 * @author faye
 * @className QS
 * @Description TODO
 * @Date 2022/7/22 11:38
 * @Version 1.0
 */
public class QS {
    public static void main(String[] args) {
        //多态是可以的，但是不能使用实例中私有的方法了,静态方法绑定引用
        //
        LinkedList<Integer> stack = new LinkedList<>();
        Stack<Integer> stack1 = new Stack<>();
        Queue<Integer> queue = new PriorityQueue<>();
        Queue<Integer> queue1 = new ArrayDeque<>();
        stack1.add(1);
        stack1.add(2);
        stack1.add(3);
        stack1.add(1);
        for(int i:stack1){
            System.out.print(i+" ");
        }
    }
}
