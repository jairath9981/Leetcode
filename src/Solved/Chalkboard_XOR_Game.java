package Solved;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/*
Input:
1 1 2 -999
Input Meaning: nums = [1,1,2]
Output: false
Explanation:
Alice has two choices: erase 1 or erase 2.
If she erases 1, the nums array becomes [1, 2]. The bitwise XOR of all the elements of the
chalkboard is 1 XOR 2 = 3. Now Bob can remove any element he wants, because Alice will be the one
to erase the last element and she will lose.
If Alice erases 2 first, now nums become [1, 1]. The bitwise XOR of all the elements of the
chalkboard is 1 XOR 1 = 0. Alice will lose.

Input 2:
0 1 -999
Output: true

Input 3:
1 2 3 -999
Explanation: 1^2^3 = 0
Output: true
*/


public class Chalkboard_XOR_Game {

    public static void main(String[] args) {
        Chalkboard_XOR_Game chalkboard_xor_game = new Chalkboard_XOR_Game();
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

                List<Integer> listOfNums = new ArrayList<>();
                System.out.println("Enter Nums Array Values. For Stop Insertion Press -999");
                int x = input.nextInt();
                while(x!=-999) {
                    listOfNums.add(x);
                    x = input.nextInt();
                }

                int[] nums = listOfNums.stream().mapToInt(i->i).toArray();

                boolean result = chalkboard_xor_game.xorGame(nums);
                System.out.println("Alex Will Win: "+result);
            }
            else
            {
                int[] nums = {};

                boolean result = chalkboard_xor_game.xorGame(nums);
                System.out.println("Alex Will Win: "+result);
            }
            t--;
        }
    }

    public boolean xorGame(int[] nums) {
        int xorResult = findXor(nums);
        if(xorResult == 0)
            return true;
        Map<Integer, Integer> elementToCount = countOfElement(nums);

        int []sameDiffCount = new int[2];
        countSameAndDiffElement(elementToCount, sameDiffCount);
        return checkConditions(sameDiffCount[0], sameDiffCount[1]);
    }

    private boolean checkConditions(int same, int diff) {
        if(same%2 == 0 && diff%2 == 0)
            return true;
        if(same%2 == 1 && diff%2 == 1)
            return true;
        return false;
    }

    private void countSameAndDiffElement(Map<Integer, Integer> elementToCount,
                                         int[] sameDiffCount) {
        int same = 0, diff = 0;
        for(Map.Entry<Integer, Integer>entry: elementToCount.entrySet()){
            if(entry.getValue()>1){
                same = same + entry.getValue();
            }else if(entry.getValue() == 1) {
                diff = diff + entry.getValue();
            }
        }
        sameDiffCount[0] = same;
        sameDiffCount[1] = diff;
    }

    private Map<Integer, Integer> countOfElement(int[] nums) {
        Map<Integer, Integer> elementToCount = new HashMap<>();

        for(int i = 0; i<nums.length; i++){
            if(!elementToCount.containsKey(nums[i])){
                elementToCount.put(nums[i], 0);
            }
            int count = elementToCount.get(nums[i]);
            elementToCount.put(nums[i], count + 1);
        }

        return elementToCount;
    }

    private int findXor(int[] nums) {
        int xor = 0;

        for(int i = 0; i<nums.length; i++)
            xor = xor^nums[i];

        return xor;
    }
}
