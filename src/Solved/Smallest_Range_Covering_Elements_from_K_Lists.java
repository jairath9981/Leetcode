package Solved;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.stream.Collectors;

/*
https://leetcode.com/problems/smallest-range-covering-elements-from-k-lists/

Example 1:

Input:
4 10 15 24 26 -999
0 9 12 20 -999
5 18 22 30 -999
-9999
Input meaning: nums = [[4,10,15,24,26],[0,9,12,20],[5,18,22,30]]
Output: [20,24]
Explanation:
List 1: [4, 10, 15, 24,26], 24 is in range [20,24].
List 2: [0, 9, 12, 20], 20 is in range [20,24].
List 3: [5, 18, 22, 30], 22 is in range [20,24].

Input:
1 2 3 -999
1 2 3 -999
1 2 3 -999
-9999
Output: [1,1]
 */

class Pair_Smallest_Range_Covering_Elements_from_K_Lists{
    int a;
    int b;

    public Pair_Smallest_Range_Covering_Elements_from_K_Lists(int a,
              int b) {
        this.a = a;
        this.b = b;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }
}

public class Smallest_Range_Covering_Elements_from_K_Lists {
    public static void main(String[] args) {
        Smallest_Range_Covering_Elements_from_K_Lists srcae = new Smallest_Range_Covering_Elements_from_K_Lists();
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
                List<List<Integer>> nums = new ArrayList<>();
                int x, y;
                System.out.println("Enter K Sorted Lists. For Stop Insertion In List Press -999 And For Stop " +
                        "Inserting I/P Press -9999");
                x = input.nextInt();
                while(x!=-9999) {
                    List<Integer> sortedList = new ArrayList<>();
                    while(x!=-999 && x!=-9999){
                        sortedList.add(x);
                        x = input.nextInt();
                    }
                    nums.add(sortedList);
                    if(x==-9999)
                        break;
                    x = input.nextInt();
                }
                System.out.println();
                int[] smallestRangeArr = srcae.smallestRange(nums);
                System.out.println("Smallest Range: ["+smallestRangeArr[0]+", "+smallestRangeArr[1]+"]");
            }
            else
            {
                Integer [][]sortedKArrays = {{4,10,15,24,26},{0,9,12,20},{5,18,22,30}};
                List<List<Integer>> nums = Arrays.stream(sortedKArrays).map(Arrays::asList).collect(
                        Collectors.toList());
                System.out.println();
                int[] smallestRangeArr = srcae.smallestRange(nums);
                System.out.println("Smallest Range: ["+smallestRangeArr[0]+", "+smallestRangeArr[1]+"]");
            }
            t--;
        }
    }

    public int[] smallestRange(List<List<Integer>> nums) {

        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        Map<Integer, List<Pair_Smallest_Range_Covering_Elements_from_K_Lists>>
                numberToArrayAndIndex = new HashMap<>();

        int []ans = new int[2];
        int b = initializeHeapAndMap(nums, minHeap, numberToArrayAndIndex);
        int a = minHeap.peek();
        ans[0] = a;
        ans[1] = b;

        while(minHeap.size() == nums.size()){
            pickBestAns(ans, a, b);

            // find next ans
            int tryToRemove = minHeap.peek();
            removeFromHeap(minHeap, tryToRemove);
            int max = insertNextValueInHeapAndMap(nums, minHeap,
                    numberToArrayAndIndex, tryToRemove);
            if(!minHeap.isEmpty()) {
                a = minHeap.peek();
                b = chooseLarger(tryToRemove, b, max);
            }
        }
        return ans;
    }

    private int chooseLarger(int tryToRemove, int b, int max) {
        if(b == tryToRemove)
            return max;
        else{
            if(max>b)
                return max;
            return b;
        }
    }

    private int insertNextValueInHeapAndMap(List<List<Integer>> nums,
      PriorityQueue<Integer> minHeap,
      Map<Integer, List<Pair_Smallest_Range_Covering_Elements_from_K_Lists>> map,
      int numberToRemove) {

        int max = Integer.MIN_VALUE;
        List<Pair_Smallest_Range_Covering_Elements_from_K_Lists> arrayNumberAndIndex =
                map.get(numberToRemove);
        map.remove(numberToRemove);

        for (int i = 0; i<arrayNumberAndIndex.size(); i++){
            Pair_Smallest_Range_Covering_Elements_from_K_Lists pair =
                    arrayNumberAndIndex.get(i);

            if(nums.get(pair.getA()).size()>pair.getB()+1) {
                int x = nums.get(pair.getA()).get(pair.getB() + 1);
                addInHeapAndMapHelper(minHeap, map, x, pair.getA(),
                        pair.getB()+1);
                if(max<x)
                    max = x;
            }
        }
        return max;
    }

    private int initializeHeapAndMap(List<List<Integer>> nums,
     PriorityQueue<Integer> minHeap,
     Map<Integer, List<Pair_Smallest_Range_Covering_Elements_from_K_Lists>> map) {

        int max = Integer.MIN_VALUE;
        for(int i = 0; i<nums.size(); i++){
            // Add smallest of All lists
            int x = nums.get(i).get(0);
            addInHeapAndMapHelper(minHeap, map, x, i, 0);
            if(max<x)
                max = x;
        }
        return max;
    }

    private void addInHeapAndMapHelper(PriorityQueue<Integer> minHeap,
      Map<Integer, List<Pair_Smallest_Range_Covering_Elements_from_K_Lists>> map,
      int num, int arrNumber, int indexNumber){

        minHeap.add(num);

        Pair_Smallest_Range_Covering_Elements_from_K_Lists pair =
          new Pair_Smallest_Range_Covering_Elements_from_K_Lists(arrNumber, indexNumber);
        addInMap(map, num, pair);
    }

    private void addInMap(
     Map<Integer, List<Pair_Smallest_Range_Covering_Elements_from_K_Lists>> map,
     int key, Pair_Smallest_Range_Covering_Elements_from_K_Lists value) {

        if(!map.containsKey(key)){
            map.put(key, new ArrayList<>());
        }
        map.get(key).add(value);
    }

    private void pickBestAns(int[] ans, int a, int b) {
        if(ans[1] - ans[0] > b - a ||
                (ans[1] - ans[0] == b - a && ans[0] > a)){
            ans[0] = a;
            ans[1] = b;
        }
    }

    private void removeFromHeap(PriorityQueue<Integer> minHeap, int tryToRemove) {
        while(!minHeap.isEmpty() && minHeap.peek() == tryToRemove)
            minHeap.poll();
    }

}
