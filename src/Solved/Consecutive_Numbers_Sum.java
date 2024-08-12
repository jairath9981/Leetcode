package Solved;

import java.util.Scanner;

/*
https://leetcode.com/problems/consecutive-numbers-sum/

Input1: 5
Input Meaning : n = 5
Output: 2
Explanation: 5 = 2 + 3

Input2: 9
Output: 3
Explanation: 9 = 4 + 5 = 2 + 3 + 4

Input3: 15
Output: 4
Explanation: 15 = 8 + 7 = 4 + 5 + 6 = 1 + 2 + 3 + 4 + 5
*/


public class Consecutive_Numbers_Sum {

    public static void main(String[] args) {
        Consecutive_Numbers_Sum consecutive_numbers_sum = new Consecutive_Numbers_Sum();
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
            if(choice == 1) {

                System.out.println("Enter 'n' For Which You Want To Find Out Number Of Ways To Write 'n' " +
                        "As The Sum Of Consecutive Positive Integers: ");
                int n = input.nextInt();

                int ways = consecutive_numbers_sum.consecutiveNumbersSum(n);
                System.out.println("Number Of Ways To Write 'n' As The Sum Of Consecutive" +
                        " Positive Integers: "+ways);
            } else {
                int n = 5;

                int ways = consecutive_numbers_sum.consecutiveNumbersSum(n);
                System.out.println("Number Of Ways To Write 'n' As The Sum Of Consecutive" +
                        " Positive Integers: "+ways);
            }
            t--;
        }
    }

    public int consecutiveNumbersSum(int n) {
        int totalConsecutiveSum = 1;
        for(int i = 1; i<n; i++){
            int dividend = n - sumOfNConsecutiveNum(i);
            int rem = dividend%(i+1);
            if(dividend<=0) {
                break;
            }
            if(rem == 0) {
                totalConsecutiveSum++;
            }
        }
        return totalConsecutiveSum;
    }

    private int sumOfNConsecutiveNum(int n) {
        return (n*(n+1))/2;
    }
}
