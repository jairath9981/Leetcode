package Solved;

/*
https://leetcode.com/problems/super-washing-machines/

Input: 1 0 5 -999
Input Meaning: machines = [1,0,5]
Output: 3
Explanation:
1st move:    1     0 <-- 5    =>    1     1     4
2nd move:    1 <-- 1 <-- 4    =>    2     1     3
3rd move:    2     1 <-- 3    =>    2     2     2

Input: 0 3 0 -999
Output: 2
Explanation:
1st move:    0 <-- 3     0    =>    1     2     0
2nd move:    1     2 --> 0    =>    1     1     1
Example 3:

Input: 0 2 0 -999
Output: -1
Explanation:
It's impossible to make all three washing machines have the same number of dresses.

Input: 4 0 0 4 -999
Output: 2

 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Super_Washing_Machines {
    public static void main(String[] args) {
        Super_Washing_Machines swm = new Super_Washing_Machines();
        Scanner input = new Scanner(System.in);
        int t = 123;
//        System.out.println("Enter Number Of Test Cases = ");
//        t = input.nextInt();
        while (t > 0) {
            int choice;
            System.out.println("Enter Your Choice, You Want to Test Your Own Input Or Want To Test With " +
                    "Example Input, Press 1 For Your Input Else Press Any Num Key = ");
            choice = input.nextInt();
            int x;
            if (choice == 1) {
                List<Integer> machinesList = new ArrayList<>();
                System.out.println("Enter Dresses In Each Washing Machines To Stop Insertion Press -999: ");
                x = input.nextInt();
                while(x!=-999){
                    machinesList.add(x);
                    x = input.nextInt();
                }
                int[] machines = machinesList.stream().mapToInt(i->i).toArray();
                System.out.println("Minimum Number Of Moves To Make All The Washing Machines Have The Same " +
                        "Number Of Dresses: "+swm.findMinMoves(machines));
            } else {
                int []machines = {1,0,5};
                System.out.println("Minimum Number Of Moves To Make All The Washing Machines Have The Same " +
                        "Number Of Dresses: "+swm.findMinMoves(machines));
            }
            t--;
        }
    }

    public int findMinMoves(int[] machines) {
        int sum = getSum(machines);
        if(sum%machines.length == 0){

            int clothsShouldBe = sum/machines.length;
            int leftSideMachines = 0, leftSideClothsShouldBe = 0;
            int leftSideHave = 0;
            int rightSideMachines = machines.length, rightSideClothCountShouldBe = sum;
            int rightSideHave = sum;

            int minMoves = 0, currentMoves = 0;
            for(int i = 0; i<machines.length; i++){

                leftSideMachines++;
                leftSideClothsShouldBe = leftSideMachines * clothsShouldBe;
                leftSideHave = leftSideHave + machines[i];
                if(leftSideHave>leftSideClothsShouldBe){ // Clothes can be sent to Right Side
                    currentMoves = currentMoves + leftSideHave - leftSideClothsShouldBe;
                }

                rightSideMachines = machines.length - i;
                rightSideClothCountShouldBe = rightSideMachines * clothsShouldBe;
                if(rightSideHave>rightSideClothCountShouldBe){ // Clothes can be sent to left Side
                    currentMoves = currentMoves + rightSideHave - rightSideClothCountShouldBe;
                }
                rightSideHave = rightSideHave - machines[i];

                minMoves = Math.max(minMoves, currentMoves);
                currentMoves = 0; // clothes can be sent independently by selecting m machines 1<=m<=machines.length
            }
            return minMoves;
        }
        return -1;
    }

    private int getSum(int[] machines) {
        int sum = 0;
        for(int i = 0; i<machines.length; i++)
            sum = sum + machines[i];
        return sum;
    }
}
