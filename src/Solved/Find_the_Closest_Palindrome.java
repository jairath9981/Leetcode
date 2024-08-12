package Solved;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/*
https://leetcode.com/problems/find-the-closest-palindrome/

Input: 123
Input meaning: n =  "123"
Output: "121"

Input: 1
Output: "0"
Explanation: 0 and 2 are the closest palindromes but we return the smallest which is 0.
 */

public class Find_the_Closest_Palindrome {
    public static void main(String[] args) {
        Find_the_Closest_Palindrome find_the_closest_palindrome = new Find_the_Closest_Palindrome();
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
                System.out.println("Enter Any Number For Which You Want To Find Closest Smallest Palindrome: ");
                input.nextLine();
                n = input.nextLine();
                String result = find_the_closest_palindrome.nearestPalindromic(n);
                System.out.println("W.R.T. " + n + " Closest Smallest Palindrome: "+result);
            } else {
                n = "123";
                String result = find_the_closest_palindrome.nearestPalindromic(n);
                System.out.println("W.R.T. " + n + " Closest Smallest Palindrome: "+result);
            }
            t--;
        }
    }

    public String nearestPalindromic(String n) {
        if(n.length() == 1){
            return String.valueOf(Integer.parseInt(n) - 1);
        }
        else{
            String[] threeHalves = cutStringInThreeHalves(n);

            String[] byIncreasingThreeHalves = ByIncreasingMiddleBit(threeHalves);
            String[] byDecreasingThreeHalves = ByDecreasingMiddleBit(threeHalves);

            String palindrome = byConcatenation(threeHalves);
            String palindromeByIncreasing = byConcatenation(byIncreasingThreeHalves);
            String palindromeByDecreasing = byConcatenation(byDecreasingThreeHalves);

            //System.out.println("palindromes: " + palindrome + "    " + palindromeByIncreasing + "    " + palindromeByDecreasing);
            return findClosestSmallest(n, palindrome, palindromeByIncreasing, palindromeByDecreasing);
        }
    }

    private String findClosestSmallest(String n, String palindrome, String palindromeByIncreasing,
                                       String palindromeByDecreasing) {
        long num = Long.parseLong(n);

        long a = Long.parseLong(palindrome);
        long b = Long.parseLong(palindromeByIncreasing);
        long c = Long.parseLong(palindromeByDecreasing);

        long diffA = Math.abs(a - num);
        long diffB = Math.abs(b - num);
        long diffC = Math.abs(c- num);
        long ansDiff = diffB;
        Map<Long, Long> diffWithPalindrome = new HashMap<>();

        if(diffA!=0) {
            Long x = getLowerValue(diffWithPalindrome, diffA, a);
            diffWithPalindrome.put(diffA, x);
            if(ansDiff>diffA)
                ansDiff = diffA;
        }
        if(b!=0){
            Long x = getLowerValue(diffWithPalindrome, diffB, b);
            diffWithPalindrome.put(diffB, x);
            if(ansDiff>diffB)
                ansDiff = diffB;
        }

        if(c!=0){
            Long x = getLowerValue(diffWithPalindrome, diffC, c);
            diffWithPalindrome.put(diffC, x);
            if(ansDiff>diffC)
                ansDiff = diffC;
        }
        return String.valueOf(diffWithPalindrome.get(ansDiff));
    }

    private Long getLowerValue(Map<Long, Long> diffWithPalindrome, long key,
                        long comparingFactor) {
        if(diffWithPalindrome.containsKey(key)) {
            Long a = diffWithPalindrome.get(key);
            if(a<=comparingFactor)
                return a;
            return comparingFactor;
        }
        return comparingFactor;
    }

    private String byConcatenation(String[] threeHalves) {
        StringBuilder str = new StringBuilder(threeHalves[0]);

        if(threeHalves[1] == "-"){
            return threeHalves[0] + str.reverse();
        }
        return threeHalves[0] + threeHalves[1] + str.reverse();
    }

    private String[] ByDecreasingMiddleBit(String[] threeHalves) {
        if(threeHalves[1].equals("-")){  // initially even length
            String leftPart = threeHalves[0];
            StringBuilder reversedNewLeftPart = new StringBuilder();
            reversedNewLeftPart = minusOne(leftPart, reversedNewLeftPart);
            //System.out.println("ByDecreasingMiddleBit Initially Even Length reversedNewLeftPart = "+reversedNewLeftPart);
            String []threeHalvesByAddOne = appendSmartlyAfterDecreaseOne(1, 0, threeHalves, reversedNewLeftPart);
//            System.out.println("threeHalvesByAddOne: "+threeHalvesByAddOne[0]+"    "+
//                    threeHalvesByAddOne[1]+"    "+threeHalvesByAddOne[2]);
            return threeHalvesByAddOne;
        }
        else{
            String middleDigit = threeHalves[1];
            StringBuilder reversedNewLeftPart = new StringBuilder();
            int concatFlag = 0;
            if(middleDigit.charAt(0) != '0'){
                int digit = Integer.parseInt(String.valueOf(middleDigit.charAt(0))) - 1;
                reversedNewLeftPart.append(digit);
//                System.out.println("ByDecreasingMiddleBit Initially Odd Length reversedNewLeftPart = "+reversedNewLeftPart);
            }
            else{
                reversedNewLeftPart = minusOne(threeHalves[0]+threeHalves[1], reversedNewLeftPart);
                concatFlag = 1;
                //System.out.println("ByDecreasingMiddleBit Initially Odd Length reversedNewLeftPart = "+reversedNewLeftPart);
            }
            String []threeHalvesByAddOne = appendSmartlyAfterDecreaseOne(0, concatFlag, threeHalves, reversedNewLeftPart);
//            System.out.println("threeHalvesByAddOne: "+threeHalvesByAddOne[0]+"    "+
//                    threeHalvesByAddOne[1]+"    "+threeHalvesByAddOne[2]);
            return threeHalvesByAddOne;
        }
    }

    private String[] ByIncreasingMiddleBit(String[] threeHalves) {
        if(threeHalves[1].equals("-")){  // initially even length
            String leftPart = threeHalves[0];
            StringBuilder reversedNewLeftPart = new StringBuilder();
            reversedNewLeftPart = addOne(leftPart, reversedNewLeftPart);
//            System.out.println("ByIncreasingMiddleBit Initially Even Length reversedNewLeftPart = "+reversedNewLeftPart);
            String []threeHalvesByAddOne = appendSmartlyAfterAddOne(1, threeHalves, reversedNewLeftPart);
//            System.out.println("ByIncreasingMiddleBit threeHalvesByAddOne: "+threeHalvesByAddOne[0]+"    "+
//                    threeHalvesByAddOne[1]+"    "+threeHalvesByAddOne[2]);
            return threeHalvesByAddOne;
        }
        else{
            String middleDigit = threeHalves[1];
            StringBuilder reversedNewLeftPart = new StringBuilder();
            if(middleDigit.charAt(0) != '9'){
                int digit = Integer.parseInt(String.valueOf(middleDigit.charAt(0))) + 1;
                reversedNewLeftPart.append(digit);
//                System.out.println(" ByIncreasingMiddleBit Initially Odd Length reversedNewLeftPart = "+reversedNewLeftPart);
            }
            else{
                reversedNewLeftPart.append("0");
                reversedNewLeftPart = addOne(threeHalves[0], reversedNewLeftPart);
//                System.out.println("ByIncreasingMiddleBit Initially Odd Length reversedNewLeftPart = "+reversedNewLeftPart);
            }
            String []threeHalvesByAddOne = appendSmartlyAfterAddOne(0, threeHalves, reversedNewLeftPart);
//            System.out.println("ByIncreasingMiddleBit threeHalvesByAddOne: "+threeHalvesByAddOne[0]+"    "+
//                    threeHalvesByAddOne[1]+"    "+threeHalvesByAddOne[2]);
            return threeHalvesByAddOne;
        }
    }

    private String[] appendSmartlyAfterAddOne(int evenFlag, String[] initialThreeHalves,
                                              StringBuilder reversedNewLeftPart) {
        String []resultThreeHalves = new String[3];
        resultThreeHalves[0] = "-"; resultThreeHalves[1] = "-"; resultThreeHalves[2] = "-";
        if(evenFlag == 1){
            if(initialThreeHalves[0].length() == reversedNewLeftPart.length()){
                resultThreeHalves[0] = new String(reversedNewLeftPart.reverse());
                resultThreeHalves[2] = initialThreeHalves[2];
            }
            else{
                String extraDigitCarryForward = String.valueOf(reversedNewLeftPart.charAt(0));
                reversedNewLeftPart.deleteCharAt(0);
                resultThreeHalves[0] = new String(reversedNewLeftPart.reverse());
                resultThreeHalves[1] = extraDigitCarryForward;
                resultThreeHalves[2] = initialThreeHalves[2];
            }
        }else{
            if(reversedNewLeftPart.length() == 1){
                resultThreeHalves[0] = initialThreeHalves[0];
                resultThreeHalves[1] = new String(reversedNewLeftPart);
                resultThreeHalves[2] = initialThreeHalves[2];

            }else if(initialThreeHalves[0].length() + initialThreeHalves[1].length() ==
                    reversedNewLeftPart.length()){
                String extraDigitCarryForward = String.valueOf(reversedNewLeftPart.charAt(0));
                reversedNewLeftPart.deleteCharAt(0);
                resultThreeHalves[0] = new String(reversedNewLeftPart.reverse());
                resultThreeHalves[1] = extraDigitCarryForward;
                resultThreeHalves[2] = initialThreeHalves[2];

            }/* initialThreeHalves[0].length() + initialThreeHalves[1].length() ==
                    reversedNewLeftPart.length() + 1
                 */else{
                String extraDigitCarryForward = String.valueOf(reversedNewLeftPart.charAt(0));
                reversedNewLeftPart.deleteCharAt(0);
                resultThreeHalves[0] = new String(reversedNewLeftPart.reverse());
                resultThreeHalves[2] = extraDigitCarryForward + initialThreeHalves[2];
            }
        }
        return resultThreeHalves;
    }

    private String[] appendSmartlyAfterDecreaseOne(int evenFlag, int concatFlag,
                       String[] initialThreeHalves, StringBuilder reversedNewLeftPart) {
        String []resultThreeHalves = new String[3];
        resultThreeHalves[0] = "-"; resultThreeHalves[1] = "-"; resultThreeHalves[2] = "-";
        if(evenFlag == 1){
            if(initialThreeHalves[0].length() == reversedNewLeftPart.length()){
                resultThreeHalves[0] = new String(reversedNewLeftPart.reverse());
                resultThreeHalves[2] = initialThreeHalves[2];
            }
            else{
                resultThreeHalves[0] = new String(reversedNewLeftPart.reverse());
                resultThreeHalves[1] = "9";
                resultThreeHalves[2] = initialThreeHalves[2].substring(1);
            }
        }else{
            if(concatFlag == 1){
                if(initialThreeHalves[0].length() + initialThreeHalves[1].length() == reversedNewLeftPart.length() + 1){
                    resultThreeHalves[0] = new String(reversedNewLeftPart.reverse());
                    resultThreeHalves[2] = initialThreeHalves[2];
                }else{
                    String extraDigitCarryForward = String.valueOf(reversedNewLeftPart.charAt(0));
                    reversedNewLeftPart.deleteCharAt(0);
                    resultThreeHalves[0] = new String(reversedNewLeftPart.reverse());
                    resultThreeHalves[1] = extraDigitCarryForward;
                    resultThreeHalves[2] = initialThreeHalves[2];
                }
            }else{
                resultThreeHalves[0] = initialThreeHalves[0];;
                resultThreeHalves[1] = String.valueOf(reversedNewLeftPart);
                resultThreeHalves[2] = initialThreeHalves[2];
            }
        }
        return resultThreeHalves;
    }

    private StringBuilder addOne(String str, StringBuilder resultStr){
        int flagCarry = 0, tempi = 0;
        for(int i = str.length()-1; i>=0; i--){
            if(str.charAt(i) == '9'){
                resultStr.append("0");
                flagCarry = 1;
            }else{
                int digit = Integer.parseInt(String.valueOf(str.charAt(i))) + 1;
                resultStr.append(digit);
                flagCarry = 0;
                tempi = i;
                break;
            }
            tempi = i;
        }
        if(flagCarry == 1)
            resultStr.append("1");
        else{
            for (int i = tempi - 1; i>=0; i--)
                resultStr.append(str.charAt(i));
        }
        return resultStr;
    }

    private StringBuilder minusOne(String str, StringBuilder resultStr) {
        int tempi = 0;
        for(int i = str.length()-1; i>=0; i--){
            if(str.charAt(i) == '0'){
                resultStr.append("9");
            }else{
                int digit = Integer.parseInt(String.valueOf(str.charAt(i))) - 1;
                if(!(digit == 0 && i == 0)){
                    resultStr.append(digit);
                }
                tempi = i;
                break;
            }
            tempi = i;
        }
        for (int i = tempi - 1; i>=0; i--)
            resultStr.append(str.charAt(i));

        return resultStr;
    }

    private String[] cutStringInThreeHalves(String str) {
        String [] str_arr = new String[3];
        str_arr[0] = "-"; str_arr[1] = "-"; str_arr[2] = "-";

        StringBuilder half = new StringBuilder();
        for (int i = 0; i<str.length(); i++){
            half.append(str.charAt(i));

            if(i == (str.length()/2)-1){
                str_arr[0] = new String(half);
                if(str.length()%2 == 1){
                    i = i + 1;
                    str_arr[1] = String.valueOf(str.charAt(i));
                }
                half = new StringBuilder();
            }
        }
        str_arr[2] = new String(half);
        return str_arr;
    }

    private boolean isPalindrome(String str){
        for(int i = 0; i<=str.length()/2 - 1; i++){
            if(str.charAt(i)!=str.charAt(str.length()-i-1)){
                return false;
            }
        }
        return true;
    }
}
