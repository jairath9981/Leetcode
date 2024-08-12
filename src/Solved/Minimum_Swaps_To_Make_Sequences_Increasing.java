package Solved;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
https://leetcode.com/problems/minimum-swaps-to-make-sequences-increasing/

Input 1:
1 3 5 4 -999
1 2 3 7 -999
Input 1 Meaning: nums1 = [1,3,5,4], nums2 = [1,2,3,7]
Output: 1
Explanation:
Swap nums1[3] and nums2[3]. Then the sequences are:
nums1 = [1, 3, 5, 7] and nums2 = [1, 2, 3, 4]
which are both strictly increasing.

Input 2:
0 3 5 8 9 -999
2 1 4 6 9 -999
Output: 1

Input 3:
0 4 4 5 9 -999
0 1 6 8 10 -999
Output: 1

Input 4:
3 3 8 9 10 -999
1 7 4 6 8 -999
Output: 1

Input 5:
0 3 4 9 10 -999
2 3 7 5 6 -999
Output: 1

Input 5:
0 7 8 10 10 11 12 13 19 18 -999
4 4 5  7 11 14 15 16 17 20 -999
Output: 4
 */

public class Minimum_Swaps_To_Make_Sequences_Increasing {

    public static void main(String[] args) {
        Minimum_Swaps_To_Make_Sequences_Increasing minimum_swaps_to_make_sequences_increasing =
                new Minimum_Swaps_To_Make_Sequences_Increasing();
        Scanner input = new Scanner(System.in);
        int t;
//        System.out.println("Enter Number Of Test Cases = ");
//        t = input.nextInt();
        t = 123;
        while (t > 0) {
            int choice;
            System.out.println("Enter Your Choice, You Want to Test Your Own Input Or Want To Test With " +
                    "Example Input, Press 1 For Your Input Else Press Any Num Key = ");
            choice = input.nextInt();
            if (choice == 1) {
                int x;
                List<Integer> listOfNum1 = new ArrayList<>();
                System.out.println("Enter Your Integer Array1, For Stop Insertion Press -999");
                x = input.nextInt();
                while (x != -999) {
                    listOfNum1.add(x);
                    x = input.nextInt();
                }
                List<Integer> listOfNum2 = new ArrayList<>();
                System.out.println("Enter Your Integer Array2, For Stop Insertion Press -999");
                x = input.nextInt();
                while (x != -999) {
                    listOfNum2.add(x);
                    x = input.nextInt();
                }
                int[] num1 = listOfNum1.stream().mapToInt(i -> i).toArray();
                int[] num2 = listOfNum2.stream().mapToInt(i -> i).toArray();
                int minSwapReq = minimum_swaps_to_make_sequences_increasing.minSwap(num1, num2);
                System.out.println("Min Swaps Required To Arrange Both Arrays In Increasing " +
                        "Sequence: " + minSwapReq);
            } else {
                //43
                int[] num1 = {2,1,6,7,8,13,15,11,18,13,20,24,17,28,22,23,36,37,39,34,43,38,48,41,46,48,49,50,56,55,59,60,62,64,66,75,69,70,71,74,87,78,95,97,81,99,85,101,90,91,93,95,107,109,101,111,106,114,115,117,118,115,121,122,123,124,125,126,134,131,133,136,142,149,151,152,145,156,158,150,162,159,161,165,169,170,169,174,172,176,177,181,183,192,186,188,189,196,198,200};
                int[] num2 = {0,4,10,11,12,9,10,16,12,19,15,16,25,20,33,34,27,29,32,40,35,45,40,50,51,52,53,55,52,58,58,61,62,66,71,68,78,81,83,84,75,91,79,80,98,83,100,89,102,103,105,106,96,98,110,105,113,109,110,111,112,120,116,118,126,130,131,133,129,137,138,140,137,138,140,142,154,147,149,159,152,163,164,163,166,168,171,170,175,176,177,181,186,184,193,194,195,190,195,200};
                int minSwapReq = minimum_swaps_to_make_sequences_increasing.minSwap(num1, num2);
                System.out.println("Min Swaps Required To Arrange Both Arrays In Increasing " +
                        "Sequence: " + minSwapReq);
            }
            t--;
        }
    }

    public int minSwap(int[] num1, int[] num2) {
        int[][] dp = new int[num1.length][2];
        dp[0][0] = 0;
        dp[0][1] = 1;
//        System.out.println(dp[0][0]+"   "+dp[0][1]);
        int minSwapsWithDp = minSwapSolveWithDp(num1, num2, dp);
        System.out.println("minSwapsWithDp: "+minSwapsWithDp);
        int minSwaps = minSwapDpOptimize(num1, num2);
        System.out.println("minSwaps: "+minSwaps);
        return minSwaps;
    }

    //Approach2
    private int minSwapDpOptimize(int[] num1, int[] num2) {
        int n = num1.length;
        int prevWithoutSwap = 0, prevWithSwap = 1;
        int currWithoutSwap = Integer.MAX_VALUE;
        int currWithSwap = Integer.MAX_VALUE;
        for(int i = 1; i<n; i++){

            currWithoutSwap = Integer.MAX_VALUE;
            currWithSwap = Integer.MAX_VALUE;

            // current no swap with prev no swap
            if(num1[i-1]<num1[i] && num2[i-1]<num2[i]){
                currWithoutSwap = Math.min(currWithoutSwap, prevWithoutSwap);
            }
            // current no swap with prev swap
            if(num2[i-1]<num1[i] && num1[i-1]<num2[i]){
                currWithoutSwap = Math.min(currWithoutSwap, prevWithSwap);
            }
            // current swap with no prev swap
            if(num1[i-1]<num2[i] && num2[i-1]<num1[i]){
                currWithSwap = Math.min(currWithSwap, prevWithoutSwap+1);
            }
            // current swap with prev swap
            if(num2[i-1]<num2[i] && num1[i-1]<num1[i]){
                currWithSwap = Math.min(currWithSwap, prevWithSwap+1);
            }
            prevWithoutSwap = currWithoutSwap;
            prevWithSwap = currWithSwap;
        }
        return Math.min(currWithoutSwap, currWithSwap);
    }

    //Approach1
    private int minSwapSolveWithDp(int[] num1, int[] num2,
                                      int[][] dp) {
        int n = num1.length;
        for(int i = 1; i<n; i++){

            dp[i][0] = Integer.MAX_VALUE;
            dp[i][1] = Integer.MAX_VALUE;

            // current no swap with prev no swap
            if(num1[i-1]<num1[i] && num2[i-1]<num2[i]){
                dp[i][0] = Math.min(dp[i][0], dp[i-1][0]);
            }
            // current no swap with prev swap
            if(num2[i-1]<num1[i] && num1[i-1]<num2[i]){
                dp[i][0] = Math.min(dp[i][0], dp[i-1][1]);
            }
            // current swap with no prev swap
            if(num1[i-1]<num2[i] && num2[i-1]<num1[i]){
                dp[i][1] = Math.min(dp[i][1], dp[i-1][0]+1);
            }
            // current swap with prev swap
            if(num2[i-1]<num2[i] && num1[i-1]<num1[i]){
                dp[i][1] = Math.min(dp[i][1], dp[i-1][1]+1);
            }
            //System.out.println(dp[i][0]+"   "+dp[i][1]);
        }
        return Math.min(dp[n-1][0], dp[n - 1][1]);
    }
}
