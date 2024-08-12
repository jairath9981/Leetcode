package Solved;

import java.util.Scanner;

/*
https://leetcode.com/problems/kth-smallest-number-in-multiplication-table/

Input:
3
3
5
Input meaning : m = 3, n = 3, k = 5
Output: 3
Explanation:
 1  2  3
 2  4  6
 3  6  9

 1  2  2  3  3  4  6  6  9
The 5th smallest number is 3.

Input:
2
3
6
Output: 6
Explanation: The 6th smallest number is 6.

Input:
9895
28405
100787757
Output: 31666344

Input:
3
1
3
Output: 3

Input:
9
9
81
Output: 81

Input:
45
12
471
Output: 312
 */

class Pair_Kth_Smallest_Number_In_Multiplication_Table{
    int num;
    int count;
    int start;
    int end;

    Pair_Kth_Smallest_Number_In_Multiplication_Table(int num, int count,
         int start, int end){
        this.num = num;
        this.count = count;
        this.start = start;
        this.end = end;
    }
}

public class Kth_Smallest_Number_In_Multiplication_Table {

    public static void main(String []args) {
        Kth_Smallest_Number_In_Multiplication_Table kth_smallest_number =
                new Kth_Smallest_Number_In_Multiplication_Table();
        Scanner input = new Scanner(System.in);
        int t = 123;
//        System.out.println("Enter Number Of Test Cases = ");
//        t = input.nextInt();
        while (t > 0) {
            int choice;
            System.out.println("Enter Your Choice, You Want to Test Your Own Input Or Want To Test With " +
                    "Example Input, Press 1 For Your Input Else Press Any Num Key = ");
            choice = input.nextInt();
            int m, n, k;
            if (choice == 1) {
                System.out.println("Enter How Many Tables You Want To Incorporate = ");
                m = input.nextInt();
                System.out.println("Enter Up To Which Index You Want To Continue Table= ");
                n = input.nextInt();
                System.out.println("Enter Which Kth Smallest Number You Want To Search = ");
                k = input.nextInt();
                System.out.println("The "+k+"th smallest number is: " +
                        kth_smallest_number.findKthNumber(m, n, k));
            } else {
                m = 3;
                n = 3;
                k = 5;
                System.out.println("The "+k+"th smallest number is: " +
                        kth_smallest_number.findKthNumber(m, n, k));
            }
            t--;
        }
    }

    public int findKthNumber(int m, int n, int k) {

        int smallest = 1, largest = m*n;

        int mid = 1;
        int l =smallest, r = largest;
        while(l<=r){
//            System.out.println("Kind Of Binary Search: l = "+l+" r = "+r);
            mid = l + (r - l)/2;
//            System.out.println("Kind Of Binary Search: ans = "+mid);
            Pair_Kth_Smallest_Number_In_Multiplication_Table pair =
              computeHowManyNumExistInInclusiveRangeAToB(smallest, mid, m, n);
            if(pair.start<=k && pair.end>=k){
                mid = pair.num;
                break;
            }
            else if (pair.end<k){
                l = mid + 1;
            }else if(pair.start>k){
                r = mid - 1;
            }
        }
        return mid;
    }


    private Pair_Kth_Smallest_Number_In_Multiplication_Table
    computeHowManyNumExistInInclusiveRangeAToB(int a, int b,
        int tableNumber, int tableIndex) {
        if(b<a)
            return new Pair_Kth_Smallest_Number_In_Multiplication_Table(0,0,0,0);
        int countOfNumInRange = 0;
        int maxNum = 0;
        for (int i = 1; i<=Math.min(b/2, tableNumber); i++){
            /*
             int smallerNumber = b/i;
                Let's suppose b/i = y, then this y is not just a Dividend result, but it signifies
                something in itself:
                 1 y can always be a whole Number (as we are only considering integral part)
                 2 Which number we need to multiply with i to get lesser or equal number as b.
                 Let's suppose:
                 y*i<=b (Equal condition for exact division)
                 (y-1)*i<b;
                 Eg1:
                    b = 8, i = 2
                    y = 8/2 = 4
                    4 signifies if we multiply:4*2<=8 3*2<8, 2*2<8, 1*2<8
                    Eg2:
                    b = 8, i = 3
                    y = 8/3 = 2.666 = 2
                    2 signifies if we multiply:2*3<=8 1*3<8
             */
            int smallerNumber = b/i;
            if(smallerNumber>=tableIndex){
                countOfNumInRange+=tableIndex;
                if(maxNum<i*tableIndex)
                    maxNum = i*tableIndex;
            }else{
                countOfNumInRange+=smallerNumber;
                if(maxNum<i*smallerNumber)
                    maxNum = i*smallerNumber;
            }
        }
        int needToPickFor1Result =  Math.min(b, tableNumber);
        int countWhoWillGive1Result = needToPickFor1Result-(b/2);
        if(maxNum<needToPickFor1Result)
            maxNum = needToPickFor1Result;
        if(countWhoWillGive1Result>0)
            countOfNumInRange+=countWhoWillGive1Result;
        int countFactors = totalFactorsOfNum(maxNum, tableNumber, tableIndex);
        Pair_Kth_Smallest_Number_In_Multiplication_Table pair =
                new Pair_Kth_Smallest_Number_In_Multiplication_Table(maxNum, countOfNumInRange,
                        countOfNumInRange - countFactors + 1, countOfNumInRange);
        return pair;
    }

    private int totalFactorsOfNum(int num, int m, int n){
        int possibleTill = Math.min((int) Math.sqrt(num), m);
        int countFactor = 0;

        for(int i = 1; i<=possibleTill; i++){
            if(num%i == 0){
                int j = num/i;
                if(j == i)
                    countFactor++;
                else{
                    if(i<=m && j<=n)
                        countFactor+=1;
                    if(j<=m && i<=n)
                        countFactor+=1;
                }
            }
        }
        return countFactor;
    }
}
