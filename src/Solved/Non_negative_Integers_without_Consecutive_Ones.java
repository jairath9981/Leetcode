package Solved;

import java.util.ArrayList;
import java.util.Scanner;

/*
https://leetcode.com/problems/non-negative-integers-without-consecutive-ones/

Input: 5
Output: 5
Explanation:
Here are the non-negative integers <= 5 with their corresponding binary representations:
0 : 0
1 : 1
2 : 10
3 : 11
4 : 100
5 : 101
Among them, only integer 3 disobeys the rule (two consecutive ones) and the other 5 satisfy the rule.

Input: 1
Output: 2

Input: 2
Output: 3
 */

class MemorisationOfPerBit_Non_negative_Integers_without_Consecutive_Ones{
    int numberOfBits;
    int maxNumberAchieved;
    int largestNumberAfterThatEveryNumberHasConsecutiveOnes;
    int consecutiveOneCountOnThisBit;
    int sumOfConsecutiveOneCountTillThisBit;
    int nonConsecutiveOneCount;

    public MemorisationOfPerBit_Non_negative_Integers_without_Consecutive_Ones() {
    }

    public MemorisationOfPerBit_Non_negative_Integers_without_Consecutive_Ones(int numberOfBits,
               int maxNumberAchieved, int largestNumberAfterThatEveryNumberHasConsecutiveOnes,
               int consecutiveOneCountOnThisBit, int sumOfConsecutiveOneCountTillThisBit, int nonConsecutiveOneCount) {
        this.numberOfBits = numberOfBits;
        this.maxNumberAchieved = maxNumberAchieved;
        this.largestNumberAfterThatEveryNumberHasConsecutiveOnes = largestNumberAfterThatEveryNumberHasConsecutiveOnes;
        this.consecutiveOneCountOnThisBit = consecutiveOneCountOnThisBit;
        this.sumOfConsecutiveOneCountTillThisBit = sumOfConsecutiveOneCountTillThisBit;
        this.nonConsecutiveOneCount = nonConsecutiveOneCount;
    }

    public void setNumberOfBits(int numberOfBits) {
        this.numberOfBits = numberOfBits;
    }
    public void setMaxNumberAchieved(int maxNumberAchieved) {
        this.maxNumberAchieved = maxNumberAchieved;
    }
    public void setLargestNumberAfterThatEveryNumberHasConsecutiveOnes(int largestNumberAfterThatEveryNumberHasConsecutiveOnes) {
        this.largestNumberAfterThatEveryNumberHasConsecutiveOnes = largestNumberAfterThatEveryNumberHasConsecutiveOnes;
    }
    public void setConsecutiveOneCountOnThisBit(int consecutiveOneCountOnThisBit) {
        this.consecutiveOneCountOnThisBit = consecutiveOneCountOnThisBit;
    }
    public void setSumOfConsecutiveOneCountTillThisBit(int sumOfConsecutiveOneCountTillThisBit) {
        this.sumOfConsecutiveOneCountTillThisBit = sumOfConsecutiveOneCountTillThisBit;
    }
    public void setNonConsecutiveOneCount(int nonConsecutiveOneCount) {
        this.nonConsecutiveOneCount = nonConsecutiveOneCount;
    }
}

public class Non_negative_Integers_without_Consecutive_Ones {
    public static void main(String[] args) {
        Non_negative_Integers_without_Consecutive_Ones n_n_i_w_c_o =
                new Non_negative_Integers_without_Consecutive_Ones();
        Scanner input = new Scanner(System.in);
        int t = 123;
//        System.out.println("Enter Number Of Test Cases = ");
//        t = input.nextInt();
        while (t > 0) {
            int choice;
            System.out.println("Enter Your Choice, You Want to Test Your Own Input Or Want To Test With " +
                    "Example Input, Press 1 For Your Input Else Press Any Num Key = ");
            choice = input.nextInt();
            if (choice == 1) {
                int n;
                System.out.println("Enter Positive Mx Range: ");
                n = input.nextInt();
                int count = n_n_i_w_c_o.findIntegers(n);
                System.out.println("The Number Of The Integers In The Range [0, "+ n +"] Whose Binary " +
                        "Representations Do Not Contain Consecutive Ones: "
                        + count);
            } else {
                int n  = 1;
                int count = n_n_i_w_c_o.findIntegers(n);
                System.out.println("The Number Of The Integers In The Range [0, "+ n +"] Whose Binary " +
                        "Representations Do Not Contain Consecutive Ones: "
                        + count);
            }
            t--;
        }
    }

    public int findIntegers(int n) {
        if(n>=1){
            ArrayList<MemorisationOfPerBit_Non_negative_Integers_without_Consecutive_Ones> dp =
                    buildDpUpToMaxPossibleBit(n);
            //printDp(dp);
            if(n>dp.get(dp.size() - 1).maxNumberAchieved){ // last bit pending
                return drillLastBit(n, dp);
            }else{
                return dp.get(dp.size() - 1).nonConsecutiveOneCount;
            }
        }
        else if(n==0)
            return 1;
        return -1; // Enter Value is not correct
    }

    private int drillLastBit(int n,
               ArrayList<MemorisationOfPerBit_Non_negative_Integers_without_Consecutive_Ones> dp) {
        int []countOfNumbersOfConsecutiveOnesInLastBit = new int[1];
        countOfNumbersOfConsecutiveOnesInLastBit[0] = 0;
        MemorisationOfPerBit_Non_negative_Integers_without_Consecutive_Ones mpb = dp.get(dp.size() - 1);

        int largestNumberAfterThatEveryNumberHasConsecutiveOnes =
                getLargestNumberAfterThatEveryNumberHasConsecutiveOnes(mpb.numberOfBits + 1);
        if(n>=largestNumberAfterThatEveryNumberHasConsecutiveOnes)
            n = largestNumberAfterThatEveryNumberHasConsecutiveOnes - 1;

        int lastBitNumberCount = n - mpb.maxNumberAchieved;
        int remaining = (n - mpb.maxNumberAchieved - 1);
        drillLastBitHelper(remaining, dp, countOfNumbersOfConsecutiveOnesInLastBit);
        return mpb.nonConsecutiveOneCount + (lastBitNumberCount - countOfNumbersOfConsecutiveOnesInLastBit[0]);
    }

    private void drillLastBitHelper(int remaining,
                  ArrayList<MemorisationOfPerBit_Non_negative_Integers_without_Consecutive_Ones> dp,
                  int[] countOfNumbersOfConsecutiveOnesInLastBit) {
        if(remaining == 0 || remaining == 1 || remaining == 2) {
            countOfNumbersOfConsecutiveOnesInLastBit[0] = countOfNumbersOfConsecutiveOnesInLastBit[0] + 0;
            return;
        }
        else{
            int index = binarySearchLessThenOrEqualToX(remaining, dp);
            MemorisationOfPerBit_Non_negative_Integers_without_Consecutive_Ones mpb = dp.get(index);

            if(remaining == mpb.maxNumberAchieved){
                countOfNumbersOfConsecutiveOnesInLastBit[0] = countOfNumbersOfConsecutiveOnesInLastBit[0] +
                        mpb.sumOfConsecutiveOneCountTillThisBit;;
                return;
            }
            else {
                int nextBitStart = mpb.maxNumberAchieved + 1;
                int nextPartiallyCovered = getPartiallyCoverCount(remaining,
                        dp.get(index + 1));
                countOfNumbersOfConsecutiveOnesInLastBit[0] = countOfNumbersOfConsecutiveOnesInLastBit[0] +
                        mpb.sumOfConsecutiveOneCountTillThisBit + nextPartiallyCovered;
                drillLastBitHelper(getRemaining(remaining, nextBitStart, dp.get(index + 1)), dp,
                        countOfNumbersOfConsecutiveOnesInLastBit);
            }
        }
    }

    private int getRemaining(int remaining, int nextBitStart,
                  MemorisationOfPerBit_Non_negative_Integers_without_Consecutive_Ones nextBitMemorisation) {
        if(remaining>=nextBitMemorisation.largestNumberAfterThatEveryNumberHasConsecutiveOnes){
            return (nextBitMemorisation.largestNumberAfterThatEveryNumberHasConsecutiveOnes - 1) - nextBitStart;
        }
        return remaining - nextBitStart;
    }

    private int getPartiallyCoverCount(int remaining,
      MemorisationOfPerBit_Non_negative_Integers_without_Consecutive_Ones nextBitMemorisation) {
        if(remaining>=nextBitMemorisation.largestNumberAfterThatEveryNumberHasConsecutiveOnes){
            return (remaining - nextBitMemorisation.largestNumberAfterThatEveryNumberHasConsecutiveOnes) + 1;
        }
        return 0;
    }

    private int binarySearchLessThenOrEqualToX(int x,
                    ArrayList<MemorisationOfPerBit_Non_negative_Integers_without_Consecutive_Ones> dp) {
        int left = 0, right = dp.size() - 1;
        int ansIndex = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (dp.get(mid).maxNumberAchieved <= x) {
                ansIndex = mid;
                left = mid + 1;
            }
            else {
                right = mid - 1;
            }
        }
        return ansIndex;
    }

    /*
        int numberOfBits;
        int maxNumberAchieved;
        int largestNumberAfterThatEveryNumberHasConsecutiveOnes;
        int consecutiveOneCountOnThisBit;
        int sumOfConsecutiveOneCountTillThisBit;
        int nonConsecutiveOneCount;
     */
    private ArrayList<MemorisationOfPerBit_Non_negative_Integers_without_Consecutive_Ones>
    buildDpUpToMaxPossibleBit(int n) {
        ArrayList<MemorisationOfPerBit_Non_negative_Integers_without_Consecutive_Ones> dp =
                new ArrayList<>();

        int bitCount = 1;
        int maxNumberAchievedByBitCount = getMaxNumberCreatedBy_N_BitCount(bitCount);
        int sum = 0; // sumOfConsecutiveOneCountTillThisBit
        while(maxNumberAchievedByBitCount<=n){
            int largestNumberAfterThatEveryNumberHasConsecutiveOnes =
                    getLargestNumberAfterThatEveryNumberHasConsecutiveOnes(bitCount);
            int consecutiveOneCountOnThisBit = getConsecutiveOneCountForCurrentBit(maxNumberAchievedByBitCount,
                    largestNumberAfterThatEveryNumberHasConsecutiveOnes,
                    getPrevToPrevBitConsecutiveOneSum(dp));
            sum = sum + consecutiveOneCountOnThisBit;
            int nonConsecutiveOneCount = (maxNumberAchievedByBitCount + 1) - sum;

            dp.add(new MemorisationOfPerBit_Non_negative_Integers_without_Consecutive_Ones(bitCount,
                    maxNumberAchievedByBitCount, largestNumberAfterThatEveryNumberHasConsecutiveOnes,
                    consecutiveOneCountOnThisBit, sum, nonConsecutiveOneCount));

            bitCount++;
            maxNumberAchievedByBitCount = getMaxNumberCreatedBy_N_BitCount(bitCount);
        }
        return dp;
    }

    private int getMaxNumberCreatedBy_N_BitCount(int bitCount) {
        return  ((int)(Math.pow(2, bitCount)) - 1);
    }

    private int getLargestNumberAfterThatEveryNumberHasConsecutiveOnes(int bitCount) {
        if(bitCount == 1)
            return -1; // non-reachable thing for bitCount = 1
        else{
            return  ((int)(Math.pow(2, (bitCount - 1))) + ((int)Math.pow(2, (bitCount - 2))));
        }
    }

    private int getPrevToPrevBitConsecutiveOneSum(
            ArrayList<MemorisationOfPerBit_Non_negative_Integers_without_Consecutive_Ones> dp) {
        if(dp.isEmpty())
            return -2; // to maintain dp when bitCount = 1
        else if(dp.size() == 1)
            return 0; // to maintain dp when bitCount = 2
        return dp.get(dp.size() - 2).sumOfConsecutiveOneCountTillThisBit;
    }

    private int getConsecutiveOneCountForCurrentBit(int maxNumberAchievedByBitCount,
                    int largestNumberAfterThatEveryNumberHasConsecutiveOnes, int prevToPrevSum) {
        if(largestNumberAfterThatEveryNumberHasConsecutiveOnes<=0)
            largestNumberAfterThatEveryNumberHasConsecutiveOnes = 0;
        return ((maxNumberAchievedByBitCount - largestNumberAfterThatEveryNumberHasConsecutiveOnes) + 1) +
                prevToPrevSum;
    }

    private void printDp(
            ArrayList<MemorisationOfPerBit_Non_negative_Integers_without_Consecutive_Ones> dp){
        System.out.println("NumberOfBits    MaxNumberAchieved    LargestNumberAfterThatEveryNumberHasConsecutiveOnes    " +
                "ConsecutiveOneCountOnThisBit    SumOfConsecutiveOneCountTillThisBit    NonConsecutiveOneCount");
        for(int i = 0; i<dp.size(); i++){
            MemorisationOfPerBit_Non_negative_Integers_without_Consecutive_Ones mpb = dp.get(i);
            System.out.println("    "
                    +mpb.numberOfBits+"                   "
                    +mpb.maxNumberAchieved+"                                "
                    +mpb.largestNumberAfterThatEveryNumberHasConsecutiveOnes+"                                              "
                    +mpb.consecutiveOneCountOnThisBit +"                                   "
                    +mpb.sumOfConsecutiveOneCountTillThisBit+"                             "
                    +mpb.nonConsecutiveOneCount);
        }
    }
}
