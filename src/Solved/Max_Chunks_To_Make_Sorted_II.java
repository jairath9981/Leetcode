package Solved;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


/*
https://leetcode.com/problems/max-chunks-to-make-sorted-ii/

Input:
5 4 3 2 1 -999
Input 1 meaning: arr = [5,4,3,2,1]
Output: 1
Explanation:
Splitting into two or more chunks will not return the required result.
For example, splitting into [5, 4], [3, 2, 1] will result in [4, 5, 1, 2, 3], which isn't sorted.

Input 2:
2 1 3 4 4 -999
Output: 4
Explanation:
We can split into two chunks, such as [2, 1], [3, 4, 4].
However, splitting into [2, 1], [3], [4], [4] is the highest number of chunks possible.

Input 3:
2 1 3 4 5 2 4 -999
Output: 2
*/


public class Max_Chunks_To_Make_Sorted_II {

    public static void main(String[] args) {
        Max_Chunks_To_Make_Sorted_II max_chunks_to_make_sorted_ii = new Max_Chunks_To_Make_Sorted_II();
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
                System.out.println("Enter Your Integer Array, For Stop Insertion Press -999");
                x = input.nextInt();
                while (x != -999) {
                    listOfNumbers.add(x);
                    x = input.nextInt();
                }
                int[] arr = listOfNumbers.stream().mapToInt(i -> i).toArray();
                int chunks = max_chunks_to_make_sorted_ii.maxChunksToSorted(arr);
                System.out.println("The Largest Number Of Chunks We Can Make To Sort " +
                        "The Array: " + chunks);
            } else {
                int[] arr = {5,4,3,2,1};
                int chunks = max_chunks_to_make_sorted_ii.maxChunksToSorted(arr);
                System.out.println("The Largest Number Of Chunks We Can Make To Sort " +
                        "The Array: " + chunks);
            }
            t--;
        }
    }

    public int maxChunksToSorted(int[] arr) {
        int[] sortedArr = sortArr(arr);
        Map<Integer, List<Integer>> numberToListOfIndexesMap =
                generateNumberToIndexMap(sortedArr);
        int chunks = maxChunksRequiredToSortArr(arr, sortedArr,
                numberToListOfIndexesMap);
        return chunks;
    }

    private int maxChunksRequiredToSortArr(int[] arr, int[] sortedArr,
            Map<Integer, List<Integer>> numberToListOfIndexesMap) {
        int chunks = 0;
        Map<Integer,Integer> numberToUsedIndexes = new HashMap<>();
        for(int i = 0; i<arr.length; i++){
            int appropriateIndex = findIndexInSortedArr(arr[i],
                    numberToListOfIndexesMap, numberToUsedIndexes);
            if(appropriateIndex!=i){
                int traceUpToIndex = traceInBetweenIndex(i, appropriateIndex, arr,
                        numberToListOfIndexesMap, numberToUsedIndexes);
                i = traceUpToIndex;
            }else{
                addInUsedMap(numberToUsedIndexes, arr[i]);
            }
            chunks++;
        }
        return chunks;
    }

    private void addInUsedMap(Map<Integer, Integer> numberToUsedIndexes, int num) {
        if(numberToUsedIndexes.containsKey(num)){
            int prev = numberToUsedIndexes.get(num);
            numberToUsedIndexes.put(num, prev+1);
        }else{
            numberToUsedIndexes.put(num, 0);
        }
    }

    private int traceInBetweenIndex(int start, int end, int[] arr,
             Map<Integer, List<Integer>> numberToListOfIndexesMap,
              Map<Integer, Integer> numberToUsedIndexes) {

        while(start<=end){
            int num = arr[start];
            int appropriateIndex = findIndexInSortedArr(num, numberToListOfIndexesMap,
                    numberToUsedIndexes);
            addInUsedMap(numberToUsedIndexes, num);

            if(appropriateIndex>end){
                end = appropriateIndex;
            }
            start++;
        }
        return end;
    }

    private int findIndexInSortedArr(int num,
            Map<Integer, List<Integer>> numberToListOfIndexesMap,
            Map<Integer, Integer> numberToUsedIndexes) {

        int start = numberToUsedIndexes.getOrDefault(num, -1);
        start++;
        List<Integer> listOfIndexes = numberToListOfIndexesMap.get(num);
        int index = listOfIndexes.get(start);
        return index;
    }


    private int[] sortArr(int[] arr) {
        int[] copyArr = new int[arr.length];

        for (int i = 0; i<arr.length; i++)
            copyArr[i] = arr[i];

        Arrays.sort(copyArr);

        return copyArr;
    }

    private Map<Integer, List<Integer>> generateNumberToIndexMap(int[] arr) {
        Map<Integer, List<Integer>> numberToListOfIndexesMap = new HashMap<>();

        for(int i = 0; i<arr.length; i++) {
            if(!numberToListOfIndexesMap.containsKey(arr[i]))
                numberToListOfIndexesMap.put(arr[i], new ArrayList<>());
            numberToListOfIndexesMap.get(arr[i]).add(i);
        }
        return numberToListOfIndexesMap;
    }
}
