package com;

import java.util.ArrayList;
import java.util.Arrays;

public class Test1 {
    public static void main(String[] args) {
        int[] nums = {1, 9, 2, 8, 3, 7, 4, 6, 5};
        Arrays.stream(nums).filter(x->x>4).sorted().forEach(System.out::println);
    }
}
