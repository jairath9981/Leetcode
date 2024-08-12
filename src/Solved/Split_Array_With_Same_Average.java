package Solved;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/*
https://leetcode.com/problems/split-array-with-same-average/

Input:
1 2 3 4 5 6 7 8 -999
Input Meaning: nums = [1,2,3,4,5,6,7,8]
Output: true
Explanation: We can split the array into [1,4,5,8] and [2,3,6,7], and both of them have an
average of 4.5.

Input 2:
3 1 -999
Output: false

Input 3:
2 0 5 6 16 12 15 12 4 -999; sum = 72, avg = 8
Output: true

Input 4:
6 8 18 3 1 -999
Output: false

Input 5:
17 3 7 12 1 -999
Output: false

Input 6:
12 1 17 8 2 -999
Output: true

Input 7:
60  30  30  30  30  30  30  30  30  30  30  30  30  30  30  30  30  30  30  30  30  30  30  30  30  30  30  30  30  30 -999
Output: false

Input 8:
0 13 13 7 5 0 10 19 5 -999
Output: true

Input 9:
10 29 13 53 33 48 76 70 5 5 -999
4  4  4  4  4  4  5  4  4  4  4  4  4  5 -999
0 0 0 0 0 -999
Output: true
*/

class NumberOfWaysTOAchieveXSum_Split_Array_With_Same_Average{
    Set<Integer> numberOfElements;

    public NumberOfWaysTOAchieveXSum_Split_Array_With_Same_Average(){
        this.numberOfElements = new HashSet<>();
    }
}

public class Split_Array_With_Same_Average {

    public static void main(String []args) {
        Split_Array_With_Same_Average splitArrayWithSameAvg = new Split_Array_With_Same_Average();
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
                List<Integer> listOfNumbers = new ArrayList<>();
                System.out.println("Enter Your Integer Array, For Stop Insertion Press -999");
                x = input.nextInt();
                while (x != -999) {
                    listOfNumbers.add(x);
                    x = input.nextInt();
                }
                int[] nums = listOfNumbers.stream().mapToInt(i -> i).toArray();
                boolean ans = splitArrayWithSameAvg.splitArraySameAverage(nums);
                System.out.println("Can We Split The Array Into Equal Average: "+ans);
            } else {
                int[] nums = {1, 2, 3, 4, 5, 6, 7, 8};
                boolean ans = splitArrayWithSameAvg.splitArraySameAverage(nums);
                System.out.println("Can We Split The Array Into Equal Average: "+ans);
            }
            t--;
        }
    }

    public boolean splitArraySameAverage(int[] nums) {
        int totalSum = getSum(nums);
        float avg = (float) totalSum/nums.length;
        if(nums.length>1)
            return createDp(nums, avg);
        return false;
    }

    private boolean createDp(int[] nums, float avg) {
        if(avg == 0)
            return true;
        int cols = detectMaxCols(avg, nums.length/2);
        if(cols == 0)
            return false;
        cols++;

        Arrays.sort(nums);
        NumberOfWaysTOAchieveXSum_Split_Array_With_Same_Average[][] dp =
                initializeDp(cols, nums);;
        int intAvg = (int)avg;
        if(intAvg == avg && dp[0][intAvg].numberOfElements.size() == 1)
            return true;
        for (int i = 1; i<nums.length; i++){
            for (int j = 0; j<cols; j++){
                NumberOfWaysTOAchieveXSum_Split_Array_With_Same_Average curr;
                if(i%2 == 0){
                    curr = populateDp(nums, i, j, dp[1][j],
                            getUpperRowAfterSubCurr(dp[1], j-nums[i]));
                    dp[0][j] = curr;
                }else{
                    curr = populateDp(nums, i, j, dp[0][j],
                            getUpperRowAfterSubCurr(dp[0], j-nums[i]));
                    dp[1][j] = curr;
                }
                if(!curr.numberOfElements.isEmpty() &&
                        isSumAchievableByDesirableNumberOfElement(j, curr, avg)){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isSumAchievableByDesirableNumberOfElement(
      int targetSum,
      NumberOfWaysTOAchieveXSum_Split_Array_With_Same_Average NumberOfWaysTOAchieveXSum,
      float avg) {
        for(int element: NumberOfWaysTOAchieveXSum.numberOfElements) {
           if(targetSum == element*avg){
               return true;
           }
        }
        return false;
    }

    private NumberOfWaysTOAchieveXSum_Split_Array_With_Same_Average populateDp(
        int[] nums, int i, int j,
       NumberOfWaysTOAchieveXSum_Split_Array_With_Same_Average dpOfTopCol,
        NumberOfWaysTOAchieveXSum_Split_Array_With_Same_Average dpOfTopAfterSubCurr) {

        NumberOfWaysTOAchieveXSum_Split_Array_With_Same_Average numberOfWaysTOAchieveXSum =
                new NumberOfWaysTOAchieveXSum_Split_Array_With_Same_Average();

        if(j<nums[i])
            return dpOfTopCol;
        if(j == nums[i]) {
            numberOfWaysTOAchieveXSum.numberOfElements.add(1);
        }
        if(j>=nums[i]){
            if(dpOfTopAfterSubCurr!=null && !dpOfTopAfterSubCurr.numberOfElements.isEmpty()){
                for(int prevSize: dpOfTopAfterSubCurr.numberOfElements){
                    numberOfWaysTOAchieveXSum.numberOfElements.add(1 + prevSize);
                }
            }
        }
        if(!dpOfTopCol.numberOfElements.isEmpty()){
            numberOfWaysTOAchieveXSum.numberOfElements.addAll(dpOfTopCol.numberOfElements);
        }
        return numberOfWaysTOAchieveXSum;
    }

    private NumberOfWaysTOAchieveXSum_Split_Array_With_Same_Average[][] initializeDp(
            int cols, int[] nums) {

        NumberOfWaysTOAchieveXSum_Split_Array_With_Same_Average[][]dp =
           new NumberOfWaysTOAchieveXSum_Split_Array_With_Same_Average[2][cols];

        for(int j = 0; j<cols; j++){
            NumberOfWaysTOAchieveXSum_Split_Array_With_Same_Average numberOfWaysTOAchieveXSum =
                    new NumberOfWaysTOAchieveXSum_Split_Array_With_Same_Average();

            if(nums[0] == j){
                numberOfWaysTOAchieveXSum.numberOfElements.add(1);
                dp[0][j] = numberOfWaysTOAchieveXSum;
            }else{
                dp[0][j] = numberOfWaysTOAchieveXSum;
            }
        }
        return dp;
    }

    private int detectMaxCols(float avg, int n) {
        for(int i = n; i>=1; i--){
            float floatAvg = (avg*i);
            int intAvg = (int) floatAvg;
            if(floatAvg == intAvg)
                return intAvg;
        }
        return 0;
    }

    private NumberOfWaysTOAchieveXSum_Split_Array_With_Same_Average getUpperRowAfterSubCurr(
            NumberOfWaysTOAchieveXSum_Split_Array_With_Same_Average[] dp, int col) {
        if(col<0)
            return null;
        else{
            return dp[col];
        }
    }

    private int firstNoZeroIndex(int[] nums) {
        int index = -1;
        for(int i = 0; i< nums.length; i++){
            if(nums[i]!=0)
                return i;
        }
        return index;
    }

    private int getSum(int[] nums) {

        int sum = 0;
        for(int i = 0; i<nums.length; i++){
            sum+=nums[i];
        }
        return sum;
    }
}
