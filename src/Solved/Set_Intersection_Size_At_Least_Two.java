package Solved;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/*
Input 1:
1 3
1 4
2 5
3 5
-1
Input 1 meaning: intervals = [[1,3],[1,4],[2,5],[3,5]]
Output: 3
Explanation: Consider the set S = {2, 3, 4}.  For each interval, there are at least 2 elements
from S in the interval. Also, there isn't a smaller size set that fulfills the above condition.
Thus, we output the size of this set, which is 3.

Input 2:
1 2
2 3
2 4
4 5
-1
Output: 5
Explanation: An example of a minimum sized set is {1, 2, 3, 4, 5}.

Input 3:
1 3
3 7
5 7
7 8
-1
Output: 5
*/

public class Set_Intersection_Size_At_Least_Two {
    public static void main(String[] args) {
        Set_Intersection_Size_At_Least_Two sis2 = new Set_Intersection_Size_At_Least_Two();
        Scanner input = new Scanner(System.in);
        int t;
//        System.out.println("Enter Number Of Test Cases = ");
//        t = input.nextInt();
        t = 123;
        while (t > 0) {
            int choice;
            System.out.println("Enter Your Choice, You Want to Test Your Own Input Or Want To " +
                    "Test With Example Input, Press 1 For Your Input Else Press Any Num Key = ");
            choice = input.nextInt();
            if(choice == 1) {
                List<List<Integer>> listOfIntervals = new ArrayList<>();
                int x, y;
                System.out.println("Enter Intervals Range [a, b] Where a<b And Range Will " +
                        "Include Ever Number Arom a To B Inclusively. For Stop Insertion Enter " +
                        "Any Negative Interval");
                x = input.nextInt();
                y = input.nextInt();
                while(x>=0 && y>=0) {
                    listOfIntervals.add(List.of(x, y));
                    x = input.nextInt();
                    if(x>=0)
                        y = input.nextInt();
                    else
                        break;
                }
                int[][] intervals = listOfIntervals.stream().map( u -> u.stream().mapToInt(
                        i->i).toArray() ).toArray(int[][]::new);
                System.out.println();
                int intervalSetSize = sis2.intersectionSizeTwo(intervals);
                System.out.println("Minimum Sized Set Length Is: "+intervalSetSize);
            }
            else
            {
               int [][]intervals = {{1,3},{1,4},{2,5},{3,5}};
                System.out.println();
                int intervalSetSize = sis2.intersectionSizeTwo(intervals);
                System.out.println("Minimum Sized Set Length Is: "+intervalSetSize);
            }
            t--;
        }
    }

    private final int requiredIntersection = 2;
    public int intersectionSizeTwo(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[1]));
        //print(intervals);
        List<Integer> result = intersectionSizeTwoHelper(intervals);
        //System.out.println("Result: "+result);
        return result.size();
    }

    private List<Integer> intersectionSizeTwoHelper(int[][] intervals) {

        List<Integer> result = new ArrayList<>();
        //Input 3
        Set<Integer> consumedNumbers = new HashSet<>();
        int a = intervals[0][0];
        int b = intervals[0][1];
        addNewValues(result, requiredIntersection, consumedNumbers, a, b);

        for(int i = 1; i<intervals.length; i++){
            a = intervals[i][0];
            b = intervals[i][1];
            int count = howManyNewValuesNeedToBeAdded(result, a, b);
            addNewValues(result, count, consumedNumbers, a, b);
        }
        return result;
    }

    private void addNewValues(List<Integer> result, int count,
           Set<Integer> consumedNumbers, int a, int b) {
        int j = b+1;
        for(int i = b; i>=a && count>0; i--){
            if(!consumedNumbers.contains(i)) {
                j = i;
                count--;
            }
        }
        int flag = 0; //Input 3
        for(int i = j; i<=b; i++){
            if(!consumedNumbers.contains(i)) {
                result.add(i);
                consumedNumbers.add(i);
            }else{
                flag = 1;
            }
        }
        if(flag == 1){
            Collections.sort(result);
        }
    }

    private int howManyNewValuesNeedToBeAdded(List<Integer> result,
                       int a, int b) {
        int count = requiredIntersection;
        int index = -1;
        while(count>0){
            index =  binarySearch(result, index+1, a, b);
            if(index == -1)
                break;
            count--;
        }
        return count;
    }

    private int binarySearch(List<Integer> result, int start, int a, int b) {
        int left = start, right = result.size()-1;
        int index = -1;
        while (left<=right){
            int mid = left + ((right - left)/2);
            if(a<=result.get(mid) && result.get(mid)<=b){
                index = mid;
                right = mid - 1;
            }else if(result.get(mid)<a){
                left = mid + 1;
            }else if(result.get(mid)>b){
                right = mid - 1;
            }
        }
        return index;
    }

    private  void print(int[][] arr){
        System.out.println("Print Array: ");
        for(int i = 0; i<arr.length; i++){
            System.out.println(arr[i][0]+"   "+arr[i][1]);
        }
    }
}
