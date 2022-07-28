package com;

/**
 * @author faye
 * @className QuickSort
 * @Description TODO
 * @Date 2022/7/26 9:05
 * @Version 1.0
 */
public class QuickSort {
    public static void main(String[] args) {
        int[] nums = {5,4,6,9,7,3,8,2,9,1,0};
        quicksort(nums,0,nums.length-1);
        for(int i:nums){
            System.out.print(i+" ");
        }

    }
    public static void quicksort(int[] nums,int start,int end){
        if(start<end){
            int lo = start,hi=end,target=nums[lo];
            while(lo<hi){
                while(lo<hi&&nums[hi]>=target) hi--;
                nums[lo]=nums[hi];
                while(lo<hi&&nums[lo]<target) lo++;
                nums[hi]=nums[lo];
            }
            nums[lo]=target;
            quicksort(nums,start,lo-1);
            quicksort(nums,lo+1,end);
        }


    }
}
