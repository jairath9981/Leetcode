package Solved;

import java.util.Scanner;

/*
https://leetcode.com/problems/longest-happy-prefix/

Input 1:
level
Output: "l"
Explanation: s contains 4 prefix excluding itself ("l", "le", "lev", "leve"),
and suffix ("l", "el", "vel", "evel"). The largest prefix which is also suffix is given by "l".

Input 2:
ababab
Output: "abab"
Explanation: "abab" is the largest prefix which is also suffix. They can overlap in the original
string.

Input 3:
bba
Output: ""
*/

public class Longest_Happy_Prefix {
    public static void main(String[] args) {
        Longest_Happy_Prefix longest_happy_prefix = new Longest_Happy_Prefix();
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
                System.out.println("Enter String In Which You Want To Find Longest Happy Prefix: ");
                input.nextLine();
                s = input.nextLine();
                String longestPrefix = longest_happy_prefix.longestPrefix(s);
                System.out.println("Longest Happy Prefix: "+longestPrefix);
            } else {
                s = "ababab";
                String longestPrefix = longest_happy_prefix.longestPrefix(s);
                System.out.println("Longest Happy Prefix: "+longestPrefix);
            }
            t--;
        }
    }

    //Modification Of KMP
    public String longestPrefix(String s) {
        String longestPrefix = "";
        if(s.length()>1){
            int[] lpu = buildLPU_Table(s);
            int longestPrefixLength = lpu[lpu.length - 1];
            longestPrefix = s.substring(0, longestPrefixLength);
        }
        return longestPrefix;
    }

    private int[] buildLPU_Table(String str) {
        int []lpu = new int[str.length()];
        lpu[0] = 0;

        int suffixIndex = 1;
        int prefixIndex = 0;
        int n = str.length();

        while(suffixIndex<n){
            if(str.charAt(suffixIndex) == str.charAt(prefixIndex)){
                lpu[suffixIndex] = prefixIndex + 1;
                prefixIndex++;
                suffixIndex++;
            }else{
                if(prefixIndex!=0){
                    prefixIndex = lpu[prefixIndex - 1];
                }else {
                    lpu[suffixIndex] = 0; //prefixIndex
                    suffixIndex++;
                }
            }
        }
        return lpu;
    }
}
