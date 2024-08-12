package Solved;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/*
https://leetcode.com/problems/find-k-th-smallest-pair-distance/
*/

public class Find_K_th_Smallest_Pair_Distance {

    public static void main(String[] args) {
        Find_K_th_Smallest_Pair_Distance find_k_th_smallest_pair = new Find_K_th_Smallest_Pair_Distance();
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
                int[] nums = listOfNumbers.stream().mapToInt(i -> i).toArray();
                System.out.println("Enter k Value i.e. Which Smallest Position You Want To Pick: ");
                k = input.nextInt();
                int ans = find_k_th_smallest_pair.smallestDistancePair(nums, k);
                System.out.println("K-th Smallest Value: " + ans);
            } else {
                int[] nums = {1, 3, 1};
                int k = 1;
                int ans = find_k_th_smallest_pair.smallestDistancePair(nums, k);
                System.out.println("K-th Smallest Value: " + ans);
            }
            t--;
        }
    }

    public int smallestDistancePair(int[] nums, int k) {
        Arrays.sort(nums);
        return binarySearchForDiff(k, nums);
    }

    private int binarySearchForDiff(int k, int[] nums) {

        int ans = -1;

        Set<Integer> diffSearched = new HashSet<>();
        Set<Integer> diffCannotExist = new HashSet<>();

        int left = 0, right = nums[nums.length-1] - nums[0];
        int[] currentTargetDiffExist = new int[2];
        currentTargetDiffExist[0] = 0;
        while (left <= right) {

            int targetDiff = left + (right - left) / 2;

            currentTargetDiffExist[0] = 0;
            currentTargetDiffExist[1] = Integer.MIN_VALUE;

            int count = getCountOfSmallerOrEqualToDiff(nums, targetDiff,
                    diffSearched, diffCannotExist, currentTargetDiffExist);

            if(currentTargetDiffExist[0] == 0) {

                int tempTargetDiff = currentTargetDiffExist[1];
                if(!diffSearched.contains(tempTargetDiff) &&
                        !diffCannotExist.contains(tempTargetDiff)){

                    diffCannotExist.add(targetDiff);
                    targetDiff = tempTargetDiff;
                }
                else{
                    // targetDiff Automatically adjust itself
                    // in below if, else if conditions
                    // else if (count < k)
//                    System.out.println("Not Able to get Proper targetDiff. " +
//                            "targetDiff: "+targetDiff+"  count: "+count);
                }
            }
            diffSearched.add(targetDiff);

            if (count > k) {
                ans = targetDiff;
                right = targetDiff - 1;
            } else if (count < k) {
                left = targetDiff + 1;
            } else {
                ans = targetDiff;
                break;
            }
        }
        return ans;
    }

    private int getCountOfSmallerOrEqualToDiff(int[] nums, int targetDiff,
                   Set<Integer> diffSearched, Set<Integer> diffCannotExist,
                   int[] currentTargetDiffExist) {

        int totalCountSmallerOeEqualToDiff = 0;
        for(int i = nums.length - 1; i>=1; i--){

            int numToGetDiffEqualToTargetDiff = nums[i] - targetDiff;
            int currCount = 0;

            if(numToGetDiffEqualToTargetDiff > 0) {
                int index = binarySearchGreaterThenOrEqualToX(nums,
                        i - 1, numToGetDiffEqualToTargetDiff);
                if(index!=-1) {
                    currCount = i - index;
                    diffReachedDecision(numToGetDiffEqualToTargetDiff, index, i,
                            nums, currentTargetDiffExist);
                }
            }else{
                currCount = i;
                diffReachedDecision(numToGetDiffEqualToTargetDiff, 0, i,
                        nums, currentTargetDiffExist);
            }
            totalCountSmallerOeEqualToDiff+=currCount;
        }
        return totalCountSmallerOeEqualToDiff;
    }

    private void diffReachedDecision(int numToGetDiffEqualToTargetDiff, int index,
            int currIndex, int[] nums, int[] currentTargetDiffExist) {

        if(currentTargetDiffExist[0] == 0) {
            if (nums[index] == numToGetDiffEqualToTargetDiff) {
                currentTargetDiffExist[0] = 1;
            }else{
                currentTargetDiffExist[1] = Math.max(nums[currIndex] - nums[index],
                        currentTargetDiffExist[1]);
            }
        }
    }


    private int binarySearchGreaterThenOrEqualToX(int[] nums, int right, int x) {
        int left = 0;

        int index = - 1;
        while (left<=right){
            int mid = left + (right - left)/2;
            if(nums[mid]<x){
                left = mid+1;
            }else if(nums[mid]>=x){
                index = mid;
                right = mid-1;
            }
        }
        return index;
    }

}

/*
Algo1 : Time Limit Exceed For Larger Input

        public int smallestDistancePair(int[] nums, int k) {

        Map<Integer, Integer> diffToCount = new HashMap<>();
        PriorityQueue<Integer> minHeapDiff = buildDiffHeap(nums, diffToCount);

        int countOfSmaller = 0;
        int diffValue = -1;
        while(!minHeapDiff.isEmpty() && countOfSmaller<k){
            diffValue = minHeapDiff.poll();
            int count = diffToCount.get(diffValue);
            countOfSmaller = countOfSmaller + count;
        }
        return diffValue;
    }

    private PriorityQueue<Integer> buildDiffHeap(int[] nums,
                Map<Integer, Integer> diffToCount) {

        PriorityQueue<Integer> minHeapDiff = new PriorityQueue<>();
        Set<Integer> visitedIntegers = new HashSet<>();
        for (int i = 0; i<nums.length-1; i++){

            if(!visitedIntegers.contains(nums[i])) {
                visitedIntegers.add(nums[i]);
                int currCount = 1;

                for (int j = i + 1; j < nums.length; j++) {
                    int diff = Math.abs(nums[i] - nums[j]);
                    storeDiffs(minHeapDiff, diffToCount, diff, currCount);
                    if(nums[j] == nums[i])
                        currCount++;
                }
            }
        }

        return minHeapDiff;
    }

    private void storeDiffs(PriorityQueue<Integer> minHeapDiff,
      Map<Integer, Integer> diffToCount, int diff, int currCount) {

        if(diffToCount.containsKey(diff)){
            diffToCount.put(diff, diffToCount.get(diff)+currCount);
        }else{
            minHeapDiff.add(diff);
            diffToCount.put(diff, currCount);
        }
    }
*/
