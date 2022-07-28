package com.LeetCode;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author faye
 * @className Leet40
 * @Description TODO
 * @Date 2022/7/21 9:41
 * @Version 1.0
 */
public class Leet40 {
    public static void main(String[] args) {
        int[] candidates = new int[]{10,1,2,7,6,1,5};
        int target = 8;
        Solution40 solution = new Solution40();
        List<List<Integer>> lists = solution.combinationSum2(candidates, target);
        System.out.println(lists.toString());

    }

}
class Solution40 {
    List<List<Integer>> res = new LinkedList<>();
    LinkedList<Integer> stack  = new LinkedList<>();
    int sum = 0;
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        backTrack(candidates,target,0);
        return res;
    }

    //每个数字在每个组合只能使用一次，
    //判断重复情况:
    //开始下标，就相当于使用了一个used[放入索引]的操作，只不过
    public void backTrack(int[] candidates,int target,int index){
        if(sum==target){
            res.add(new LinkedList(stack));
            return;
        }
        //超出结果的部分也需要return 不然就会无限递归
        if(sum>target) return;
        //剪枝
        for(int i=index;i<candidates.length;i++){

            sum+=candidates[i];
            stack.add(candidates[i]);
            //
            backTrack(candidates,target,i+1);
            //
            sum-=candidates[i];
            while(i<(candidates.length-1)&&candidates[i]==candidates[i+1]) i++;
            stack.removeLast();
        }
    }
}
