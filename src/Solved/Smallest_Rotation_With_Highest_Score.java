package Solved;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

/*
https://leetcode.com/problems/smallest-rotation-with-highest-score/

Input 1
2 3 1 4 0 -999
Input 1 Meaning: nums = [2,3,1,4,0]
Output: 3
Explanation: Scores for each k are listed below:
k = 0,  nums = [2,3,1,4,0],    score 2
k = 1,  nums = [3,1,4,0,2],    score 3
k = 2,  nums = [1,4,0,2,3],    score 3
k = 3,  nums = [4,0,2,3,1],    score 4
k = 4,  nums = [0,2,3,1,4],    score 3
So we should choose k = 3, which has the highest score.

Input 2
1 3 0 2 4 -999
Output: 0
Explanation: nums will always have 3 points no matter how it shifts.
So we will choose the smallest k, which is 0.
*/

public class Smallest_Rotation_With_Highest_Score {

    public static void main(String[] args) {
        Smallest_Rotation_With_Highest_Score smallest_rotation_with_highest_score =
                new Smallest_Rotation_With_Highest_Score();
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
                List<Integer> listOfNumbers = new ArrayList<>();
                System.out.println("Enter Your Integer Array For Which You Want To Get The " +
                        "Max Score My Min Rotations. For Stop Insertion Press -999");
                x = input.nextInt();
                while (x != -999) {
                    listOfNumbers.add(x);
                    x = input.nextInt();
                }
                int[] nums = listOfNumbers.stream().mapToInt(i -> i).toArray();
                int ans = smallest_rotation_with_highest_score.bestRotation(nums);
                System.out.println("Min Rotation To Achieve The Max Score " + ans);
            } else {
                int[] nums = {1, 3, 0, 2, 4};
                int ans = smallest_rotation_with_highest_score.bestRotation(nums);
                System.out.println("Min Rotation To Achieve The Max Score " + ans);
            }
            t--;
        }
    }

    public int bestRotation(int[] nums) {

        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        Map<Integer, Integer> numberOfValuesNeedToRemoveAfterRotation =
                new HashMap<>();
        int k = 0, maxScore = 0, prevScore = 0;
        int currScore = scoreWithoutRotation(nums, minHeap,
                numberOfValuesNeedToRemoveAfterRotation);
        maxScore = currScore; prevScore = currScore;

        for(int rot = 1; rot<nums.length; rot++){
            currScore = prevScore;
            if(minHeap.peek()<rot){
                currScore-=numberOfValuesNeedToRemoveAfterRotation.get(minHeap.peek());
                numberOfValuesNeedToRemoveAfterRotation.remove(minHeap.peek());
                minHeap.poll();
            }
            currScore+=1;
            addInMapAndHeap(numberOfValuesNeedToRemoveAfterRotation, minHeap,
                    rot+(nums.length-1-nums[rot-1]));
            if(maxScore<currScore){
                maxScore = currScore;
                k = rot;
            }
            prevScore = currScore;
        }
        return k;
    }

    private int scoreWithoutRotation(int[] nums, PriorityQueue<Integer> minHeap,
                 Map<Integer,Integer> numberOfValuesNeedToRemoveAfterRotation) {
        int score = 0;
        for (int i = 0; i<nums.length; i++){
            if(nums[i] <= i) {
                score += 1;
                addInMapAndHeap(numberOfValuesNeedToRemoveAfterRotation, minHeap,
                        i-nums[i]);
            }
        }
        return score;
    }

    private void addInMapAndHeap(Map<Integer, Integer> map,
              PriorityQueue<Integer> minHeap, int key) {
        if(map.containsKey(key)){
            int count = map.get(key);
            map.put(key, count+1);
        }else{
            minHeap.add(key);
            map.put(key, 1);
        }
    }
}
