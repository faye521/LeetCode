package com.javase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author faye
 * @className ArrayToList
 * @Description TODO
 * @Date 2022/8/9 14:58
 * @Version 1.0
 */
public class ArrayToList {
    public static void main(String[] args) {
        //****************************数组转集合*****************************************
        //必须是包装类才能转化成集合，也只能转化成List
        Integer[] nums = new Integer[]{8,7,6,9,1,2,3,4,5};
        List<Integer> res = Arrays.asList(nums);
        //通过包装类数组转化成的集合，比较特殊，底层仍然是数组
        //所以这个集合只能进行查询和排序等不改变长度结构的操作，不能进行增删操作
        Collections.sort(res,(o1,o2)->o2.compareTo(o1));
        System.out.println(res);
        //如果想要使用增删操作，就需要借助一个新的集合
        //1.推荐做法, 所有集合都拥有一个带有Collection类型参数的构造方法
        List<Integer> list = new ArrayList<>(res);
        list.add(11);
        System.out.println(list);
        //2.可行不推荐做法
        List<Integer> list1 = new ArrayList<>();
        list1.addAll(res);
        list1.remove(list1.size()-1);
        System.out.println(list1);

        //****************************集合转数组*****************************************
        //object[]  toArray()
        //<T> T[]   toArray(T[] a)  所以必须传入参数才能确定转成什么类型的数组
        List<String> slist = new ArrayList<>();
        slist.add("春天");
        slist.add("夏天");
        slist.add("秋天");
        slist.add("冬天");
        String[] strs = slist.toArray(new String[]{});
        System.out.println(Arrays.toString(strs));
        //检查一下类型
        System.out.println(strs.getClass());


    }
}
