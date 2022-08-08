package com;

import java.security.cert.CollectionCertStoreParameters;
import java.util.*;

/**
 * @author faye
 * @className ComparatorTest
 * @Description 测试一下比较器Comparator，comparable
 * @Date 2022/7/25 9:09
 * @Version 1.0
 */
public class ComparatorTest {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("car");
        list.add("alex");
        list.add("draw");
        list.add("bob");
        //大写字母的优先级是最高的
        list.add("Blexa");
        //构建一个比较器给list排序
//        Comparator<String> comp = new Comparator<String>() {
//            @Override
//            public int compare(String o1, String o2) {
//                return o2.compareTo(o1);
//            }
//        };
        //不添加比较器的话，默认是一个升序排序
//        Collections.sort(list,comp);
        //使用lambda 简化比较器
        Collections.sort(list,(o1,o2)->o2.compareTo(o1));
        System.out.println(list.toString());
        System.out.println("-------------------------------------------------");
        //拥有排序功能的结构才能在初始化的时候传入一个比较器 TreeSet TreeMap
        //map好像默认传入的是key
        Map<String,String> map = new TreeMap<String,String>(
                new Comparator<String>(){
                    @Override
                    public int compare(String s1,String s2){
                        return s2.compareTo(s1);
                    }
                 }
        );
        map.put("alex","zero");map.put("bob","year");map.put("clik","wink");
        System.out.println(map.toString());
        System.out.println("------------------------------");
        UserComparator u1 = new UserComparator("alex",52);
        UserComparator u2 = new UserComparator("zero",22);
        UserComparator u3 = new UserComparator("bob",42);
        List<UserComparator> ulist = new ArrayList<>();
        ulist.add(u1);ulist.add(u2);ulist.add(u3);
        //方法1
        Collections.sort(ulist, new Comparator<UserComparator>() {
            @Override
            public int compare(UserComparator o1, UserComparator o2) {
                return o1.id.compareTo(o2.id);
            }
        });
        for(UserComparator u:ulist){
            System.out.print(u.name+" ");
        }
        System.out.println();
        System.out.println("-------------------------------");
        //方法2 就算实现了comparable接口，只能说明这个结构组(UserComparator)可以被排序
        //不是自动排序的，所以也需要自己再手动排序
        List<UserComparator> ul = new ArrayList<>();
        ul.add(u1);ul.add(u2);ul.add(u3);
        //Arrays.sort Collections.sort 可以给单一元素类型的默认排序，复杂结构需要implements结构，或者创建构造器
        Collections.sort(ul);
        for(UserComparator u:ul){
            System.out.print(u.name+" ");
        }




    }
}
class UserComparator implements Comparable{
    String name;
    Integer id;
    public UserComparator(String name,Integer id){
        this.name = name;
        this.id = id;
    }

    @Override
    public int compareTo(Object o) {
        UserComparator u = (UserComparator)o;
        if(this.id>=u.id){
            //从大到小排序
            return -1;
        }else if(this.id<u.id){
            return 1;
        }
        //默认从大到小
        return 1;
    }
}
