package Solved;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/*
https://leetcode.com/problems/couples-holding-hands/

Input 1:
0 2 1 3 -999
Input 1 meaning: row = [0,2,1,3]
Output: 1
Explanation: We only need to swap the second (row[1]) and third (row[2]) person.

Input 2:
3 2 0 1 -999
Output: 0
Explanation: All couples are already seated side by side.
*/


public class Couples_Holding_Hands {
    public static void main(String[] args) {
        Couples_Holding_Hands couples_holding_hands = new Couples_Holding_Hands();
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
                int x;
                List<Integer> listOfRow = new ArrayList<>();
                System.out.println("Enter Your Row Seat Array, For Stop Insertion Press -999");
                x = input.nextInt();
                while (x != -999) {
                    listOfRow.add(x);
                    x = input.nextInt();
                }
                int[] row = listOfRow.stream().mapToInt(i -> i).toArray();
                int minSwap = couples_holding_hands.minSwapsCouples(row);
                System.out.println("Min. Swap Required So Tha Every Couple Can Hold Hand: " + minSwap);
            } else {
                int[] row = {0, 2, 1, 3};
                int minSwap = couples_holding_hands.minSwapsCouples(row);
                System.out.println("Min. Swap Required So Tha Every Couple Can Hold Hand: " + minSwap);
            }
            t--;
        }
    }

    public int minSwapsCouples(int[] row) {
        Map<Integer, Integer> numberToIndex = buildNumberToIndexMap(row);
        int swap = couplesSwap(row, numberToIndex);
        return swap;
    }

    private int couplesSwap(int[] row, Map<Integer, Integer> numberToIndex) {
        int swapCount = 0;
        for (int i = 0; i<row.length ; i=i+2){

            if(isSwapRequired(i, row)){
                int coupleId1 = row[i];
                int coupleId2 = getPartnerId(coupleId1);
                int indexToSwapWith = numberToIndex.get(coupleId2);
                swap(i+1, indexToSwapWith, row, numberToIndex);

                swapCount++;
            }
        }
        return swapCount;
    }

    private void swap(int currIndex, int indexToSwapWith, int[] row,
               Map<Integer, Integer> numberToIndex) {
        int temp = row[currIndex];
        row[currIndex] = row[indexToSwapWith];
        row[indexToSwapWith] = temp;

        numberToIndex.put(row[indexToSwapWith], indexToSwapWith);
        numberToIndex.put(row[currIndex], currIndex);
    }

    private boolean isSwapRequired(int currIndex, int[] row) {
        int coupleId1 = row[currIndex];
        int coupleId2 = getPartnerId(coupleId1);

        if(currIndex == 0 && row[currIndex+1] == coupleId2)
            return false;
        if(currIndex == row.length-1 && row[currIndex-1] == coupleId2)
            return false;
        if(0 < currIndex &&  currIndex < row.length-1
             && (row[currIndex-1] == coupleId2 || row[currIndex+1] == coupleId2))
            return false;
        return true;
    }

    private int getPartnerId(int coupleId1) {
        if(coupleId1%2 == 0)
            return coupleId1 + 1;
        else
           return coupleId1 - 1;
    }

    private Map<Integer, Integer> buildNumberToIndexMap(int[] row) {
        Map<Integer, Integer> numberToIndex = new HashMap<>();
        for(int i = 0; i<row.length; i++){
            numberToIndex.put(row[i], i);
        }
        return numberToIndex;
    }
}
