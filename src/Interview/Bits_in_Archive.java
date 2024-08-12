package Interview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/*
                           //  Do not know giving wrong answer
Input:
2 4 6 8 10 -999
4
Input meaning: [2, 4, 6, 8, 10]
k = 4
Output: 10


Input:
3 1 9 8 -999
3
Output: 5
 */

public class Bits_in_Archive {

    public static void main(String []args) {
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
            if (choice == 1) {
                int x, k;
                List<Integer> listOfNumbers = new ArrayList<>();
                System.out.println("Enter Your Integer Array, For Stop Insertion Press -999");
                x = input.nextInt();
                while (x != -999) {
                    listOfNumbers.add(x);
                    x = input.nextInt();
                }
                System.out.println("Enter ThreshHold Value: ");
                k = input.nextInt();
                long countOfBits = Bits_in_Archive.countBit(listOfNumbers, k);
                System.out.println("countOfBits: "+countOfBits);
            } else {
                int[] nums = {3,2,1,1};
                List<Integer> listOfNumbers = Arrays.stream(nums).boxed().collect(Collectors.toList());
                int k = 4;
                long countOfBits = Bits_in_Archive.countBit(listOfNumbers, k);
                System.out.println("countOfBits: "+countOfBits);
            }
            t--;
        }
    }

    public static long countBit(List<Integer> arr, int k) {
        List<Long> onBits = countOnBitsForAllElements(arr);
        Collections.sort(onBits);
        System.out.println("After Sort OnBits: "+onBits);
        long count = 0;
        for(int i = 0; i<onBits.size(); i++){
            long bitCanAccommodate = k-onBits.get(i);
            System.out.println("bitCanAccommodate = "+bitCanAccommodate);
            long pairCountForCurrNum = binarySearchLessThenOrEqualToX(onBits,
                    bitCanAccommodate, i);
            System.out.println("pairCountForCurrNum = "+pairCountForCurrNum);
            //System.out.println("x = "+x);
            if(pairCountForCurrNum>0)
                count = count + pairCountForCurrNum;
        }
        //System.out.println("count = "+count);
        return count;
    }

    public static long binarySearchLessThenOrEqualToX(List<Long> onBits,
         long x, int start){
        if(x<0)
            return 0;
        if(x == 0)
            return 1;
        int left = 0,  right = onBits.size() - 1;
        int mid = 0, ans = 0;
        while(left<=right){
            mid = left + (right - left)/2;
            if(onBits.get(mid)>x){
                right = mid - 1;
            }
            else if(onBits.get(mid)<=x){
                ans = mid + numberOfEqualBits(onBits, mid);
                // System.out.println("loop ans = "+ans+"  "+mid);
                left = mid + 1;
            }
        }
        //System.out.println("ans = "+ans);
        return ans-start;
    }

    public static int numberOfEqualBits(List<Long> onBits, int index){
        long val = onBits.get(index);
        int count = 0;
        for(int i = index+1; i<onBits.size(); i++){
            if(onBits.get(i) == val){
                count++;
            }
            else{
                break;
            }
        }
        return count;
    }

    public static List<Long> countOnBitsForAllElements(List<Integer> arr){
        List<Long> onBits = new ArrayList<>();
        for(int i = 0; i<arr.size(); i++){
            onBits.add(countOnBits(arr.get(i)));
        }
        return onBits;
    }

    public static Long countOnBits(int num){
        long count = 0;
        while(num>0){
            num = (num&num-1);
            count++;
        }
        return count;
    }
}
