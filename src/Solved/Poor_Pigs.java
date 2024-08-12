package Solved;

import java.util.Scanner;

/*
https://leetcode.com/problems/poor-pigs/

Input :
1000
15
60
Input meaning: buckets = 1000, minutesToDie = 15, minutesToTest = 60
Output: 5

Input:
4
15
15
Output: 2

Input:
4
15
30
Output: 2
 */

public class Poor_Pigs
{
    public static void main(String []args)
    {
        Poor_Pigs poor_pigs = new Poor_Pigs();
        Scanner input = new Scanner( System.in );
        int t;
        System.out.println("Enter Number Of Test Cases = ");
        t = input.nextInt();
        while(t>0)
        {
            int choice;
            System.out.println("Enter Your Choice, You Want to Test Your Own Input Or Want To Test With " +
                    "Example Input, Press 1 For Your Input Else Press Any Num Key = ");
            choice = input.nextInt();
            int buckets, minutesToDie, minutesToTest;
            if(choice == 1) {
                System.out.println("Enter How Many Buckets You Have To Test = ");
                buckets = input.nextInt();
                System.out.println("Enter After How Much Time Poison React(minutesToDie) = ");
                minutesToDie = input.nextInt();
                System.out.println("Enter How Much You Have To Test Your Whole Buckets(minutesToTest) = ");
                minutesToTest = input.nextInt();
                System.out.println("Number Of Pigs Required To Test Your All Buckets = " +
                        poor_pigs.poorPigs(buckets, minutesToDie, minutesToTest));
            }
            else {
                buckets = 1000;
                minutesToDie = 15;
                minutesToTest = 60;
                System.out.println("Number Of Pigs Required To Test Your All Buckets = " +
                        poor_pigs.poorPigs(buckets, minutesToDie, minutesToTest));
            }
            t--;
        }
    }

    public int poorPigs(int buckets, int minutesToDie, int minutesToTest)
    {
        if(buckets>1)
        {
            int pigs = 1;
            int intervals = minutesToTest/minutesToDie;
            while(by_P_PigsIn_T_IntervalsHowManyBucketsWeCanUniquelyEncode(pigs,intervals) < buckets )
            {
                pigs++;
            }
            return pigs;
        }
        /*
            when there is only buckets then it is obvious that is only poisonous buckets so zeo pig
            required in that cas
         */
        else
        {
            return 0;
        }
    }

    private int  by_P_PigsIn_T_IntervalsHowManyBucketsWeCanUniquelyEncode(int pigs, int intervals)
    {
        return (int)Math.pow( intervals + 1, pigs );
    }
}
