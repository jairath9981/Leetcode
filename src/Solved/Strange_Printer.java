package Solved;

import java.util.Scanner;

/*
https://leetcode.com/problems/strange-printer/

Input: aaabbb
Output: 2
Explanation: Print "aaa" first and then print "bbb".

Input: aba
Output: 2
Explanation: Print "aaa" first and then print "b" from the second place of the string, which will cover
the existing character 'a'.

Input: abcabc
Output: 5
 */

public class Strange_Printer {

    public static void main(String[] args) {
        Strange_Printer sp = new Strange_Printer();
        Scanner input = new Scanner(System.in);
        int t = 123;
//        System.out.println("Enter Number Of Test Cases = ");
//        t = input.nextInt();
        while (t > 0) {
            int choice;
            System.out.println("Enter Your Choice, You Want to Test Your Own Input Or Want To Test With " +
                    "Example Input, Press 1 For Your Input Else Press Any Num Key = ");
            choice = input.nextInt();
            String s = "";
            if (choice == 1) {
                System.out.println("Enter Code: ");
                input.nextLine();
                s = input.nextLine();
                int minStepsToPrint = sp.strangePrinter(s);
                System.out.println("Min Steps To Print User String: "+minStepsToPrint);
            } else {
                s = "aaabbb";
                int minStepsToPrint = sp.strangePrinter(s);
                System.out.println("Min Steps To Print User String: "+minStepsToPrint);
            }
            t--;
        }
    }

    public int strangePrinter(String s) {
        int dp[][] = buildDp(s);
        //print2DMatrix(dp);
        return dp[0][s.length() - 1];
    }

    private int[][] buildDp(String s) {
        int dp[][] = new int[s.length()][s.length()];
        for(int len = 1; len<=s.length(); len++){
            int j = len - 1;
            for(int i = 0; i<=s.length()-len; i++){
                if(len == 1){
                    dp[i][j] = 1;
                }
                else {
                    dp[i][j] = segregateForEveryChar(s, i, j, dp);
                }
                j++;
            }
        }
        return dp;
    }

    private int segregateForEveryChar(String str, int start, int end, int[][] dp) {
        int min = Integer.MAX_VALUE;

        for(int len = 1; len<= (end - start); len++) {
            for (int i = start; i <= (end-(len-1)); i++) {

                int left = 0, right = 0, middle = 0;
                int leftRightCommon  = 0;

                int leftEnd = i - 1, rightStart = i+len;
                int middleStart = i, middleEnd = i+(len-1);

                if (start <= leftEnd) {
                    int middlePartInLeft = middleCharWithLeftEnd(str, middleStart, middleEnd, leftEnd);
                    middleStart = middleStart + middlePartInLeft;
                    if(leftEnd<end)
                        left = dp[start][leftEnd];
                    leftEnd = leftEnd + middlePartInLeft;
                }

                if (rightStart <= end) {
                    int middlePartInRight = middleCharWithRightStart(str, middleStart, middleEnd, rightStart);
                    middleEnd = middleEnd - middlePartInRight;
                    if(rightStart>start)
                        right = dp[rightStart][end];
                    rightStart = rightStart - middlePartInRight;
                }

                if(middleStart<=middleEnd)
                    middle = dp[middleStart][middleEnd];

                if ((leftEnd >= start && rightStart <= end) &&
                        (str.charAt(leftEnd) == str.charAt(rightStart)))
                    leftRightCommon = 1;

                min = Math.min(min,
                        (left + right + middle - leftRightCommon));
            }
        }
        return min;
    }

    private int middleCharWithRightStart(String str, int middleStart, int middleEnd, int rightStart) {
        int count = 0;
        for (int i = middleEnd; i>=middleStart; i--){
            if(str.charAt(i) == str.charAt(rightStart))
                count++;
            else
                break;
        }
        return count;
    }

    private int middleCharWithLeftEnd(String str, int middleStart, int middleEnd, int leftEnd) {
        int count = 0;
        for (int i = middleStart; i<=middleEnd; i++){
            if(str.charAt(i) == str.charAt(leftEnd))
                count++;
            else
                break;
        }
        return count;
    }

    private void print2DMatrix(int[][] dp) {
        for (int i = 0; i<dp.length; i++){
            for (int j = 0; j<dp[i].length; j++){
                System.out.print(dp[i][j]);
                if(j<dp[i].length-1)
                    System.out.print(", ");
            }
            System.out.println();
        }
    }

}
