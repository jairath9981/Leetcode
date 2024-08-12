package Solved;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
https://leetcode.com/problems/preimage-size-of-factorial-zeroes-function/

Input 1:
0
Input Meaning: k = 0
Output: 5
Explanation: 0!, 1!, 2!, 3!, and 4! end with k = 0 zeroes.

Input 2:
5
Output: 0
Explanation: There is no x such that x! ends in k = 5 zeroes.

Input 3:
3
Output: 5

Input 3:
28246
Output: 0
*/

class Pair_Preimage_Size_Of_Factorial_Zeroes_Function{
    long powerOf5;
    long zeroInEnd;

    public Pair_Preimage_Size_Of_Factorial_Zeroes_Function(long powerOf5, long zeroInEnd){
        this.powerOf5 = powerOf5;
        this.zeroInEnd = zeroInEnd;
    }
}

public class Preimage_Size_Of_Factorial_Zeroes_Function {

    public static void main(String[] args) {
        Preimage_Size_Of_Factorial_Zeroes_Function factorial_zeroes_function =
                new Preimage_Size_Of_Factorial_Zeroes_Function();
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

                int k;
                System.out.println("Enter An Integer K: ");
                k = input.nextInt();

                System.out.println();
                int countOfNumEndsWithKZeros = factorial_zeroes_function.preimageSizeFZF(k);
                System.out.println("Count Of Num Ends With K Zeros In Their Factorial " +
                        "Notation: "+countOfNumEndsWithKZeros);
            }
            else
            {
                int k = 0;

                System.out.println();
                int countOfNumEndsWithKZeros = factorial_zeroes_function.preimageSizeFZF(k);
                System.out.println("Count Of Num Ends With K Zeros In Their Factorial " +
                        "Notation: "+countOfNumEndsWithKZeros);
            }
            t--;
        }
    }

    public int preimageSizeFZF(int k) {
        if(k==0)
            return 5;
        List<Pair_Preimage_Size_Of_Factorial_Zeroes_Function> powerOf5 =
                powerOf5LessThenOrEqualToXZeros(k);
        if(binarySearch(k, powerOf5))
            return 5;
        return 0;
    }

    private boolean binarySearch(int k,
       List<Pair_Preimage_Size_Of_Factorial_Zeroes_Function> powerOf5) {

        Pair_Preimage_Size_Of_Factorial_Zeroes_Function lessThenOrEqualToKZeros =
                powerOf5.get(powerOf5.size()-1);
        long low = lessThenOrEqualToKZeros.powerOf5, high = low*5;

        while(low<=high){
            long mid = low + (high-low)/2;
            long numberOfZeroInFactorialOfMid = detectZeroInNumFactorial(mid, powerOf5);
            if(numberOfZeroInFactorialOfMid == k){
                return true;
            }else if(numberOfZeroInFactorialOfMid>k){
                high = mid - 1;
            }else{
                low = mid + 1;
            }
        }
        return false;
    }

    private long detectZeroInNumFactorial(long num,
            List<Pair_Preimage_Size_Of_Factorial_Zeroes_Function> powerOf5) {

        int numOfZeros = 0;
        for(int i = powerOf5.size()-1; i>=1; i--){
            int quotient = (int) (num/powerOf5.get(i).powerOf5);
            numOfZeros+=(quotient*powerOf5.get(i).zeroInEnd);

            num = num%powerOf5.get(i).powerOf5;
        }
        while(num>=5){
            int quotient = (int) (num/powerOf5.get(0).powerOf5);
            numOfZeros+=(quotient*powerOf5.get(0).zeroInEnd);

            num = num/powerOf5.get(0).powerOf5;
        }
        return numOfZeros;
    }


    private List<Pair_Preimage_Size_Of_Factorial_Zeroes_Function>
    powerOf5LessThenOrEqualToXZeros(int x){
        List<Pair_Preimage_Size_Of_Factorial_Zeroes_Function> powerOf5 =
                new ArrayList<>();

        long mul = 5;
        long zeros = 1;

        while(zeros<=x){
            powerOf5.add(new Pair_Preimage_Size_Of_Factorial_Zeroes_Function(
                    mul, zeros));
            mul = mul*5;
            zeros+=(mul/5);
        }
        return powerOf5;
    }
}
