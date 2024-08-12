package Solved;

import java.util.Scanner;
/*
https://leetcode.com/problems/largest-palindrome-product/

Input:
2
Input meaning: n = 2
Output: 987
Explanation: 99 x 91 = 9009, 9009 % 1337 = 987

Input:
1
Output: 9
 */
public class Largest_Palindrome_Product {
    public static void main(String []args) {
        Largest_Palindrome_Product largest_palindrome_product = new Largest_Palindrome_Product();
        Scanner input = new Scanner(System.in);
        int t = 123;
//        System.out.println("Enter Number Of Test Cases = ");
//        t = input.nextInt();
        while (t > 0) {
            int choice;
            System.out.println("Enter Your Choice, You Want to Test Your Own Input Or Want To Test With " +
                    "Example Input, Press 1 For Your Input Else Press Any Num Key = ");
            choice = input.nextInt();
            int n;
            if (choice == 1) {
                System.out.println("Enter Number 'n' For Which You Want To Find Largest Palindrome Product = ");
                n = input.nextInt();
                System.out.println( "Largest Palindrome Product for " + n +" is = " +
                        largest_palindrome_product.largestPalindrome(n));
            } else {
                n = 2; // ans = 9001 % 1337 = 987
                System.out.println( "Largest Palindrome Product for " + n +" digit product is = " +
                        largest_palindrome_product.largestPalindrome(n));
            }
            t--;
        }
    }

    public int largestPalindrome(int n) {
        if(n>=1) {
            long smallestNumOfNDigits = getSmallestNumOfNDigits(n);
            long largestNumOfNDigits = getLargestNumOfNDigits(n);
            long maxPalindromeAns = -1, a = -1;

            for(long i = largestNumOfNDigits; i>=smallestNumOfNDigits; i--) {

                long multiply = i*i;
                if(multiply >maxPalindromeAns) {
                    if (!isPalindrome(multiply)) {
                        /*
                            arr[0] -> multiply length
                            arr[1] -> subtractingFactor
                            arr[2] -> subtractingFactor length
                            arr[3] -> how many times we can reduce by subtractingFactor so that it remains its
                            palindrominity
                         */
                        long []arrInput ={n,smallestNumOfNDigits};
                        long []arrOutput ={0,0,0,0};
                        getSubtractingFactorForMultiply(multiply, n, smallestNumOfNDigits, arrOutput);
                        long subtractingFactor = arrOutput[1];
                        long palindrome = convertToPalindrome(multiply, arrOutput[2],
                                (long)(Math.pow(10, arrOutput[2] - 1)), arrOutput, arrInput);
                        System.out.println(i+"^2 = "+multiply+" subtractingFactor = "+subtractingFactor+
                                "  palindrome = "+palindrome);
//                        System.out.println("  arr = "+arrOutput[0] + " " + arrOutput[1] + " " +arrOutput[2] + " " + arrOutput[3]);
                        while(palindrome >= i*smallestNumOfNDigits && palindrome>maxPalindromeAns) {
                            //System.out.println("    palindrome = "+palindrome+" i = "+i);
                            if(palindrome%i == 0) {
                                if(maxPalindromeAns<palindrome) {
                                    maxPalindromeAns = palindrome;
                                    a = i;
                                }
                                break;
                            }
                            palindrome = getNextSmallerPalindrome(palindrome, subtractingFactor, arrOutput, arrInput);
                        }
                    } else {
                        if (maxPalindromeAns < multiply) {
                            maxPalindromeAns = multiply;
                            a = i;
                        }
                    }
                }
                else
                    break;
            }
            System.out.println("maxPalindromeAns = "+maxPalindromeAns+ " a = "+a);
            return (int)(maxPalindromeAns%1337);
        }
        return 0;
    }

    /*
    arr[0] -> multiply length
    arr[1] -> subtractingFactor
    arr[2] -> subtractingFactor length
    arr[3] -> how many times we can reduce by subtractingFactor so that it remains its
    palindrominity
 */
    private void getSubtractingFactorForMultiply(long multiply, int digits, long smallestNumOfNDigits,
                                                 long[] arrOutput) {
        int digitsInMultiply = countDigits(multiply);
        arrOutput[0] = digitsInMultiply;
        int numberOfZeroInBetweenTwoOnes = (digitsInMultiply - digits) - digits;
        boolean is2ndOneCome = false;
        if(numberOfZeroInBetweenTwoOnes>=0)
            is2ndOneCome = true;
        else
            numberOfZeroInBetweenTwoOnes = 0;
        if(is2ndOneCome) {
            int positionOf2ndOne = digits + numberOfZeroInBetweenTwoOnes  + 1;
            arrOutput[1] = (long)(Math.pow(10, numberOfZeroInBetweenTwoOnes+digits)) + smallestNumOfNDigits;
            arrOutput[2] = positionOf2ndOne;
        }
        else {
            arrOutput[2] = digits;
            arrOutput[1] = smallestNumOfNDigits;
        }
    }


    private int countDigits(long x) {
        int count = 0;

        while(x>0) {
            x = x/10;
            count++;
        }
        return count;
    }

    /*
    arr[0] -> multiply length
    arr[1] -> subtractingFactor
    arr[2] -> subtractingFactor length
    arr[3] -> how many times we can reduce by subtractingFactor so that it remains its
    palindrominity
 */
    private long getNextSmallerPalindrome(long currPalindrome, long subtractingFactor, long[] arrOutput, long[] arrInput) {
        //System.out.println("        *****************getNextSmallerPalindrome***************");
        if(arrOutput[3]>=1 && currPalindrome!=subtractingFactor) {
            long nextSmallerPalindrome = currPalindrome - subtractingFactor;
            arrOutput[3] = arrOutput[3] - 1;
            //System.out.println("        After calculation arrOutput[3] = "+arrOutput[3]+"   nextSmallerPalindrome = "+nextSmallerPalindrome);
            return nextSmallerPalindrome;
        }
        else{
            int subtractingFactorLength = (int) arrOutput[2];
            long currPalindromeTemp = currPalindrome;
            while(subtractingFactorLength>0)
            {
                currPalindromeTemp = currPalindromeTemp/10;
                subtractingFactorLength--;
            }
            if(currPalindromeTemp == 0 || reverseNum(currPalindromeTemp) == 1) // onr less small length multiple
            {
                long nextSmallerPalindrome = getLargestNumOfNDigits((int)arrOutput[0] -1);
                arrOutput[0] = arrOutput[0] - 1;
//                arrOutput[1] = arrOutput[1]/10;
//                arrOutput[2] = arrOutput[2] - 1;
                arrOutput[3] = 9;
                return nextSmallerPalindrome;
            }
            else
            {
                currPalindromeTemp = currPalindromeTemp -1;
                long remainingDigitsInCurrPalindromeTemp = arrOutput[0] - arrOutput[2];
                long valueOfNLargestNumOfNDigits = arrOutput[0] - 2*remainingDigitsInCurrPalindromeTemp;
                long largestNumOfNDigits = getLargestNumOfNDigits((int)valueOfNLargestNumOfNDigits);
                long additiveFactorOf9 = largestNumOfNDigits *
                        (long) Math.pow(10, remainingDigitsInCurrPalindromeTemp);
//                System.out.println("        currPalindromeTemp = "+currPalindromeTemp+
//                        " remainingDigitsInCurrPalindromeTemp = "+remainingDigitsInCurrPalindromeTemp);
//                System.out.println("        largestNumOfNDigits = "+largestNumOfNDigits+
//                        " additiveFactorOf9 = "+additiveFactorOf9);
                long nextSmallerPalindrome = ( (currPalindromeTemp)*(long)Math.pow(10, arrOutput[2]) ) +
                       + additiveFactorOf9 +  reverseNum(currPalindromeTemp);

                arrOutput[3] = 9;
//                System.out.println("        currentPalindrome = "+currPalindrome+"  "+nextSmallerPalindrome);
                return  nextSmallerPalindrome;
            }
        }
    }

    /*
    arr[0] -> multiply length
    arr[1] -> subtractingFactor
    arr[2] -> subtractingFactor length
    arr[3] -> how many times we can reduce by subtractingFactor so that it remains its
    palindrominity
 */
    private long convertToPalindrome(long num, long firstDigitToDisturb, long multiplicationFactor,
                                     long[] arrOutput, long[] arrInput) {

        //System.out.println("****************************convertToPalindrome num = ******************* "+num);
        while(firstDigitToDisturb>1) {
            num = num/10;
            firstDigitToDisturb--;
        }
        long reducedNum = num - 1;
        arrOutput[3] = (int) reducedNum % 10;

        int digitsInReducedNum = countDigits(reducedNum);
        int digitsInNum = countDigits(num);
        if(digitsInReducedNum < digitsInNum)
        {
            long palindrome = getLargestNumOfNDigits((int)arrOutput[0] - 1);
            getSubtractingFactorForMultiply(palindrome, (int)arrInput[0], arrInput[1], arrOutput);
            //System.out.println("  num = " + palindrome + " multiplicationFactor = " + multiplicationFactor);
            return palindrome;
        }
        else {
            //System.out.println("  num = " + reducedNum + " multiplicationFactor = " + multiplicationFactor);
            long reverseNumOfReducedNumber = getAdditiveNumber(reducedNum, multiplicationFactor, digitsInReducedNum);
            return ((reducedNum * multiplicationFactor) + reverseNumOfReducedNumber);
        }
    }

    private long getAdditiveNumber(long num, long multiplicationFactor, int digitsInNum)
    {
        int countDigitsInNum = digitsInNum;
        int countZerosDigitsInMultiplicationFactor = countDigits(multiplicationFactor) - 1;
        if(countDigitsInNum == countZerosDigitsInMultiplicationFactor)
            return reverseNum(num);
        else {
            int excessiveDigits = countDigitsInNum - countZerosDigitsInMultiplicationFactor;
            while(excessiveDigits>0) {
                num = num/10;
                excessiveDigits--;
            }
            return reverseNum(num);
        }

    }

    private long reverseNum(long num) {
        long rev = 0;
        while(num>0) {

            rev = rev*10 + num%10;
            num = num/10;
        }
        return rev;
    }


    private boolean isPalindrome(long num) {
        long revNum=0, curr = num;
        while(curr>0) {

            revNum = (revNum*10)  + (curr%10);
            curr = curr/10;
        }
        if(num==revNum)
            return true;
        return false;
    }

    private long getLargestNumOfNDigits(int n) {

        long num = 9;
        while(n>1) {

            num = (num*10) + 9 ;
            n--;
        }
        return num;
    }

    private long getSmallestNumOfNDigits(int n) {
        long num = 1;
        while(n>1) {

            num = num * 10;
            n--;
        }
        return num;
    }

    private long[] getSquareOfNumbersFromNToM(long n, long m) {
        long []squareOfNum = new long[(int) (m-n+1)];
        int j = 0;
        for(long i = n; i<=m; i++) {

            squareOfNum[j] = i*i;
            j++;
        }
        return squareOfNum;
    }
}
