package Solved;


import java.util.Scanner;

/*
https://leetcode.com/problems/decode-ways-ii/

Input: *
Input meaning: s = "*"
Output: 9
Explanation: The encoded message can represent any of the encoded messages "1", "2", "3", "4", "5",
"6", "7", "8", or "9". Each of these can be decoded to the strings "A", "B", "C", "D", "E", "F",
"G", "H", and "I" respectively.
Hence, there are a total of 9 ways to decode "*".

Input: 1*
Output: 18
Explanation: The encoded message can represent any of the encoded messages "11", "12", "13", "14",
"15", "16", "17", "18", or "19". Each of these encoded messages have 2 ways to be decoded (e.g. "11"
can be decoded to "AA" or "K").
Hence, there are a total of 9 * 2 = 18 ways to decode "1*".

Input: 2*
Output: 15
Explanation: The encoded message can represent any of the encoded messages "21", "22", "23", "24",
"25", "26", "27", "28", or "29". "21", "22", "23", "24", "25", and "26" have 2 ways of being decoded,
but "27", "28", and "29" only have 1 way.
Hence, there are a total of (6 * 2) + (3 * 1) = 12 + 3 = 15 ways to decode "2*".

Input: *1
Output: 11
*/


public class Decode_Way_ii {
    public static void main(String []args) {
        Decode_Way_ii decode_way_ii = new Decode_Way_ii();
        Scanner input = new Scanner(System.in);
        int t = 123;
//        System.out.println("Enter Number Of Test Cases = ");
//        t = input.nextInt();
        while (t > 0) {
            int choice;
            System.out.println("Enter Your Choice, You Want to Test Your Own Input Or Want To Test With " +
                    "Example Input, Press 1 For Your Input Else Press Any Num Key = ");
            choice = input.nextInt();
            String s;
            if (choice == 1) {
                input.nextLine();
                System.out.println("Enter Encoded String = ");
                s = input.nextLine();
                System.out.println("Number Of Ways to Decode it: " + decode_way_ii.numDecodings(s));
            } else {
                s = "*";
                System.out.println("Number Of Ways to Decode it: " + decode_way_ii.numDecodings(s));
            }
            t--;
        }
    }

    public final int mod = 1000000007;
    public int numDecodings(String s) {
        long []dp = new long [s.length()];
        for(int i = 0; i<s.length(); i++){
            long curr = generateWays(i, s, dp);
            dp[i] = curr%mod;
            if(dp[i] == 0)
                return 0;
        }
//        printDp(dp);
        return (int) (dp[dp.length-1]%mod);
    }

    private long generateWays(int currIndex, String str, long[] dp) {
        if(str.charAt(currIndex) == '0'){
            return detectForZero(currIndex, str, dp)%mod;
        }else if(str.charAt(currIndex) == '*'){
            return detectForAsterisk(currIndex, str, dp)%mod;
        }else{
            return detectForNumber(currIndex, str, dp)%mod;
        }
    }

    private long detectForNumber(int currIndex, String str, long[] dp) {
        long ways = 1;
        int currCharacter = Character.getNumericValue(str.charAt(currIndex));
        if(currIndex - 1>=0){
            ways = dp[currIndex-1];
            char prevCharacter = str.charAt(currIndex - 1);
            long prevToPrevValue =  getPrevToPrevValueForValidOnes(currIndex, dp,
                    str.charAt(currIndex));
            if(prevCharacter == '1' || (prevCharacter == '2' &&
                    (currCharacter>=1 && currCharacter<=6))){
                ways = (ways + prevToPrevValue)%mod;
            }else if(prevCharacter == '*'){
                if((currCharacter>=1 && currCharacter<=6))
                    ways = (ways + (prevToPrevValue*2))%mod;
                else
                    ways = (ways + (prevToPrevValue*1))%mod;
            }
        }
        return ways;
    }

    private long detectForAsterisk(int currIndex, String str, long[] dp) {
        long ways = 9;
        if(currIndex-1>=0){
            long prevValue = dp[currIndex - 1];
            ways = (prevValue*ways)%mod;

            char prevChar = str.charAt(currIndex - 1);
            long prevToPrevValue = getPrevToPrevValueForValidOnes(currIndex, dp,
                    str.charAt(currIndex));
            if(prevChar == '1'){
                ways = (ways + (9*prevToPrevValue))%mod;
            }
            if(prevChar == '2'){
                ways = (ways + (6*prevToPrevValue))%mod;
            }
            if(prevChar == '*'){
                ways = (ways + (15*prevToPrevValue))%mod;
            }
        }
        return ways;
    }

    private long detectForZero(int currIndex, String str, long[] dp) {
        long ways = 0;
        if(currIndex - 1>=0){
            char prevChar = str.charAt(currIndex - 1);
            long prevToPrevValue = getPrevToPrevValueForValidOnes(currIndex, dp,
                    str.charAt(currIndex));
            if(prevChar == '1' || prevChar == '2'){
                ways = prevToPrevValue%mod;
            }else if(prevChar == '*'){
                ways = 2*prevToPrevValue%mod;
            }
        }
        return ways;
    }

    private long getPrevToPrevValueForValidOnes(int currIndex, long[] dp,
                    char currCharacter) {
        if(currIndex - 2>=0)
            return dp[currIndex - 2]%mod;
        else{
            return 1;
        }
    }

    private void printDp(long[] dp) {
        for (int i = 0; i<dp.length; i++)
            System.out.print(dp[i]+"  ");
        System.out.println();
    }
}
