package com.LeetCode;

/**
 * @author faye
 * @className PackageQuestion1
 * @Description TODO
 * @Date 2022/8/12 8:34
 * @Version 1.0
 */

/**
 * 使用一维滚动数组实现01背包问题
 */
public class PackageQuestion1 {
    //物体的重量
    static int[] weight = new int[]{1, 3, 4};
    //物体的价值
    static int[] value = {15, 20, 30};
    //背包的大小
    static int bagWeight = 5;

    public static void main(String[] args) {
        bagQuestion();
    }
    //一维滚动数组
    public static void bagQuestion() {
        //确定dp含义,表示挑选当前物品时？, 索引：背包容量 元素：能装的最大价值
        int[] dp =new int[bagWeight+1];
        //base case, 第一轮不取物品 设置为0即可
        //状态转移,先遍历物品
        for(int i=0;i<weight.length;i++){
            //再遍历背包
            /**
             * 在二维dp中，每一行的值可以通过该坐标上一行的左侧确定，并且二维dp正序倒序结果都是一样的
             * 在倒序的情况下，本层dp不会覆盖上层左侧的值，所以可以使用一维数组压缩状态。
             * tips:二维数组每一层的结果来源于上一层，所以base case需要初始化第一层(放进去一个物品)，防止索引异常
             * 一维数组一直在同一层上滚动覆盖，所以不需要这个base case
             */
            for(int j=bagWeight;j>=weight[i];j--){
                dp[j]=Math.max(dp[j],dp[j-weight[i]]+value[i]);
            }
        }
        System.out.println("背包大小为"+bagWeight+"时,能背物品的最大价值为"+dp[bagWeight]);

    }
}
