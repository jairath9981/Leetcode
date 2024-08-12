package Solved;

import java.util.Scanner;

/*
https://leetcode.com/problems/smallest-good-base/

Input: 13
Output: "3"
Explanation: 13 base 3 is 111.

Input: 4681
Output: "8"
Explanation: 4681 base 8 is 11111.

Input: 1000000000000000000
Output: "999999999999999999"
Explanation: 1000000000000000000 base 999999999999999999 is 11.

Input: 3
Output: "2"

Input: 15
Output: "2"

Input: 2251799813685247
Output: "2"

Input: 470988884881403701
Output: "686286299"

Input: 663208457429249320
Output: "872067"
*/
public class Smallest_Good_Base
{
    public static void main(String []args) {
        Smallest_Good_Base smallest_good_base = new Smallest_Good_Base();
        Scanner input = new Scanner(System.in);
        int t = 123;
//        System.out.println("Enter Number Of Test Cases = ");
//        t = input.nextInt();
        while (t > 0) {
            int choice;
            System.out.println("Enter Your Choice, You Want to Test Your Own Input Or Want To Test With " +
                    "Example Input, Press 1 For Your Input Else Press Any Num Key = ");
            choice = input.nextInt();
            String n = "";
            if (choice == 1) {
                input.nextLine();
                System.out.println("Enter Number 'n' For Which You Want To Find Out Good Base = ");
                n = input.nextLine();
                System.out.println( "Good base of " + n +" is = " + smallest_good_base.smallestGoodBase(  n  ) );
            } else {
                //n = "4681"; // ans = 8
                n = "1000000000000000000"; // ans = 999999999999999999
                System.out.println( "Good base of " + n +" is = " + smallest_good_base.smallestGoodBase(  n  ) );
            }
            t--;
        }
    }

    public String smallestGoodBase(String n)
    {
        long num = Long.parseLong(n);
        long ans = num - 1; // maximum answer value

        int count = 0;
        long lowerRange = 2;
        long maxRange = (long) Math.sqrt( num );
        int bits = 2;
        while( getNumFor_X_ForAll_N_BitsOn(maxRange, bits) != num )
        {
            bits++;
            long nextMaxRange = binarySearch(lowerRange, maxRange, bits, num);
            //System.out.println("max Range inside while loop = "+ nextMaxRange + " bits = " + bits );
            if( nextMaxRange == maxRange )
                count++;
            else
                count = 0;
            if( (nextMaxRange == maxRange  && count>=20) || nextMaxRange < lowerRange) {
//                System.out.println(num + " base " + ans + " is " + " 11 ");
                return String.valueOf(ans);
            }

            maxRange = nextMaxRange;
        }
//        System.out.print(num + " base " + maxRange + " is ");
//        for(int i = 0; i<bits; i++)
//            System.out.print(1);
//        System.out.println();
        return String.valueOf(maxRange);
    }

    private long binarySearch(long low, long high, int bits, long num)
    {
        long ans = high;
        long exceedingLongMax = 0;
        while(low<=high)
        {
            long mid = low + ( (  high - low  ) / 2 );
            //System.out.println("low = "+low + " high = "+high);
            // answer For Mid by Making "bits" bits on
            long ansForMid = getNumFor_X_ForAll_N_BitsOn(mid, bits);
            //System.out.println("Mid = "+mid + " bits = "+bits+" answerForMid = "+ansForMid+" num = "+num);
            if( ansForMid > num || ansForMid < exceedingLongMax)
            {
                high = mid - 1;
            }
            else if( ansForMid < num )
            {
                ans = mid;
                low = mid + 1;
            }
            else //  if( ansForMid == num )
            {
                ans = mid;
                break;
            }
        }
        return ans;
    }

    private long getNumFor_X_ForAll_N_BitsOn(long x, int n)
    {
        long numberGenerated = 1;
        for(int i = 1; i<n; i++)
        {
            long powerResult = power(x, i );
            //System.out.println(x+"^"+i+" = "+powerResult);
            numberGenerated =  numberGenerated + powerResult;
            int j = i + 1;
            //System.out.println("till i = "+j+" = "+numberGenerated);
        }
        return numberGenerated;
    }

    private long power(long num, long e)
    {
        long result = 1;
        while (e > 0)
        {
            if (e % 2 == 0)
            {
                num = num * num;
                e = e / 2;
            }
            else
            {
                result = result * num;
                e = e - 1;
            }
        }
        return result;
    }
}
