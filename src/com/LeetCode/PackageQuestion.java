package com.LeetCode;

/**
 * @author faye
 * @className PackageQuestion
 * @Description TODO
 * @Date 2022/8/11 14:24
 * @Version 1.0
 */

/**
 * 测试一下01背包问题
 */
public class PackageQuestion {
    //物体的重量
    static int[] weight=new int[]{1,3,4};
    //物体的价值
    static int[] value={15,20,30};
    //背包的大小
    static int bagWeight=4;

    public static void main(String[] args) {
        //最后要输出背包能背的最大价值
        int res = bagQuestion(weight,value,bagWeight);
        System.out.println("背包大小为"+bagWeight+"时，背包能装入最大的价值为"+res);
    }
    //01背包问题
    public static int bagQuestion(int[] weight,int[] value,int bagWeight){
        /**
         *    dp[i][j],背包容量为j，从[0,i]个物品中选取 的最大价值
         *    i取值范围就是物体的索引[0,weight.length-1]
         *    j的取值是物体的重量,但是可以取0 [0,bagweight]
         */
        int[][] dp=new int[weight.length][bagWeight+1];
        //base case 只需要初始化背包增长过程中，只放入第一个物品的dp[0][?] ?->[weight[0],bagweight]
        for(int i=weight[0];i<=bagWeight;i++){
            dp[0][i]=value[0];
        }
        //先遍历物品 dp[0][?]已经被初始化，所以从第二行开始
        for(int i=1;i<weight.length;i++){
            //再遍历背包容量
            for(int j=0;j<=bagWeight;j++){
                if(j<weight[i]){
                    //以状态转移方程可知，j<weight[i]时表示物品i无法放入，此时dp[i][j]值可确定并且方程会出现空指针异常
                    //所以单独拿出来写
                    dp[i][j]=dp[i-1][j];
                }else{
                    dp[i][j]=Math.max(dp[i-1][j],dp[i-1][j-weight[i]]+value[i]);
                }
            }
        }
        //背包容量为4，选择放入[0,2] 3个物品的最大价值
        return dp[weight.length-1][bagWeight];
    }
}
