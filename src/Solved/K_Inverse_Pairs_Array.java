package Solved;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/*
https://leetcode.com/problems/k-inverse-pairs-array/

Input:
3
0
Input meaning: n = 3, k = 0
Output: 1
Explanation: Only the array [1,2,3] which consists of numbers from 1 to 3 has exactly 0 inverse pairs.

Input:
3
1
Output: 2
Explanation: The array [1,3,2] and [2,1,3] have exactly 1 inverse pair.

Input:
1
1
Output: 0

Input:
1000
1000
Output: 663677020
 */

public class K_Inverse_Pairs_Array {
    public static void main(String []args)
    {
        K_Inverse_Pairs_Array k_I_P_A = new K_Inverse_Pairs_Array();
        Scanner input = new Scanner( System.in );
        int t;
//        System.out.println("Enter Number Of Test Cases = ");
//        t = input.nextInt();
        t = 123;
        while(t>0)
        {
            int choice;
            System.out.println("Enter Your Choice, You Want to Test Your Own Input Or Want To Test With " +
                    "Example Input, Press 1 For Your Input Else Press Any Num Key = ");
            choice = input.nextInt();
            int n, k;
            if(choice == 1) {
                System.out.println("Enter Max Range Value(n) = ");
                n = input.nextInt();
                System.out.println("Enter Number Of Inverse Pairs That Should Exactly Be Present In " +
                        "Array (K) = ");
                k = input.nextInt();
                System.out.println("The Number Of Different Arrays Consist Of Numbers From 1 To " +
                        n + " Such That There Are Exactly "+ k + " Inverse Pairs " +
                        k_I_P_A.kInversePairs(n, k));
            }
            else {
                n = 7;
                k = 21;
                System.out.println("The Number Of Different Arrays Consist Of Numbers From 1 To " +
                        n + " Such That There Are Exactly "+ k + " Inverse Pairs " +
                        k_I_P_A.kInversePairs(n, k));
            }
            t--;
        }
    }

    final int mod = 1000000007;
    public int kInversePairs(int n, int k) {
        if(n>=1 && k>=0 && k<=getMaxColumnsForCurrent_N(n)){
            List<List<Long>> dp = new ArrayList<>();
            for(int i = 0; i<=1; i++)
                dp.add(new ArrayList<>()); // initialize
            BuildDp(n,k, dp);

            if(n%2 == 1){
                return (int)(dp.get(0).get(computeColumnIndex(n, k)) % mod);
            }else{
                return (int)(dp.get(1).get(computeColumnIndex(n, k)) % mod);
            }
        }
        return 0;
    }

    private void BuildDp(int n, int k, List<List<Long>> dp) {
        for(int i = 1; i<=n; i++){
            List<Long> row = new ArrayList<>();
            long sum = 0;
            for(int j = 0; j<=Math.min(k, getMaxColumnsForCurrent_N(i)/2); j++){
                sum = sum + (getSumFactorFromPrevRow(i, j, dp) -
                        getSubtractingFactor(i - 1, i, j, dp)) % mod;
                row.add(sum%mod);
            }
            //System.out.println(row);
            if(i%2 == 1){ // if i is odd put in 0th index
                dp.set(0, row);
            }else{ // if i is even put in 1st index
                dp.set(1, row);
            }
        }
    }

    private long getSubtractingFactor(int prevRow, int currentRow, int currentColumn,
                                      List<List<Long>> dp) {
        int maxColumnForPrevRow = getMaxColumnsForCurrent_N(prevRow);
        int maxColumnForCurrentRow = getMaxColumnsForCurrent_N(currentRow);

        int balanceUpToThisColumn = maxColumnForCurrentRow-maxColumnForPrevRow;
        if(balanceUpToThisColumn < currentColumn){
            int increasingFactorInColumn = (currentColumn - balanceUpToThisColumn) - 1;
            if(currentRow%2 == 0){
                return dp.get(0).get(increasingFactorInColumn);
            }else{
                return dp.get(1).get(increasingFactorInColumn);
            }
        }
        return 0;
    }

    private long getSumFactorFromPrevRow(int i, int j, List<List<Long>> dp) {
        if(j == 0) // j = 0 means reverse array
            return 1;
        else{
            if(i%2 == 0){
                return dp.get(0).get(computeColumnIndex(i - 1, j));
            }else{
                return dp.get(1).get(computeColumnIndex(i - 1, j));
            }
        }
    }

    private int computeColumnIndex(int rowIndex, int columnIndex) {
        int maxColumn = getMaxColumnsForCurrent_N(rowIndex);
        if(columnIndex <= maxColumn/2){
            return columnIndex;
        }else{
            return maxColumn - columnIndex;
        }
    }

    private int getMaxColumnsForCurrent_N(int n) {
        if(n == 1)
            return 0;
        int t = n - 1;
        return ((t)*(t+1))/2;
    }
}
