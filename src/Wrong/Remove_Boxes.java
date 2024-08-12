package Wrong;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

/*
https://leetcode.com/problems/remove-boxes/

// Code Is Correct But Giving Some Time Complexity Problem... So We Will Try To Solve This In Part2
    Input: 1  2  2  1  1  1  2  1  1  2  1  2  1  1  2  2  1  1  2  2  1  1  1  2  2  2  2  1  2  1  1  2  2  1  2  1  2  2  2  2  2  1  2  1  2  2  1  1  1  2  2  1  2  1  2  2  1  2  1  1  1  2  2  2  2  2  1  2  2  2  2  2  1  1  1  1  1  2  2  2  2  2  1  1  1  1  2  2  1  1  1  1  1  1  1  2  1  2  2  1  -999
    Output:

Input: 1 3 2 2 2 3 4 3 1 -999
Input meaning: boxes = [1,3,2,2,2,3,4,3,1]
Output: 23
Explanation:
[1, 3, 2, 2, 2, 3, 4, 3, 1]
----> [1, 3, 3, 4, 3, 1] (3*3=9 points)
----> [1, 3, 3, 3, 1] (1*1=1 points)
----> [1, 1] (3*3=9 points)
----> [] (2*2=4 points)
Example 2:

Input: 1 1 1 -999
Output: 9

Input: 1 -999
Output: 1

Input: 5 1 3 1 2 2 3 2 1 4 2 2 1 4 1 5 1 2 -999
         0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17
		[5,1,3,1,2,2,3,2,1,4,2, 2, 1, 4, 1, 5, 1, 2 ]
			[5,1,3,1,2,2,2,1,4,2, 2, 1, 4, 1, 5, 1, 2 ] = 1 remove 3 index 6
			[5,1,3,1,2,2,2,1,2, 2, 1, 4, 1, 5, 1, 2 ] = 2 remove 4   index 9
			[5,1,1,2,2,2,1,2, 2, 1, 4, 1, 5, 1, 2 ] = 3 remove 3     index 2
			[5,1,1,2,2,2,1,2, 2, 1, 1, 5, 1, 2 ] = 4 remove 4        index 13
			[5,1,1,2,2,2,2, 2, 1, 1, 5, 1, 2 ] = 5 remove 1          index 8
			[5,1,1 1, 1, 5, 1, 2 ] = 5 + 25 = 30 remove 2,2,2,2,2    index 4,5,7,10,11
			[5,1,1 1, 1, 1, 2 ] = 31 remove 5                        index 15
			[5, 2 ] = 31 + 25 = 56 remove 1,1,1,1,1                  index 1,3,12,14,16
			[5 ] = 57 remove 2
			[] = 58 remove 5
Output: 58
 */

class PairOfSameNumber_Remove_Boxes{
    int a;
    int b;
    int additive;
    int size;
    public PairOfSameNumber_Remove_Boxes(int a, int b){
        this.a = a;
        this.b = b;
    }
    public void setAdditive(int additive) {
        this.additive = additive;
    }

    public void setSize(int size) {
        this.size = size;
    }
}

public class Remove_Boxes {
    public static void main(String[] args) {
        Remove_Boxes rb = new Remove_Boxes();
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
                List<Integer> boxesList = new ArrayList<>();
                System.out.println("Enter Positive Number On Boxes & To Stop Insertion Press -999: ");
                x = input.nextInt();
                while(x!=-999){
                    boxesList.add(x);
                    x = input.nextInt();
                }
                int[] boxes = boxesList.stream().mapToInt(i->i).toArray();
                System.out.println("Maximum Points We Can Earn: "+rb.removeBoxes(boxes));
            } else {
                int []boxes = {1,3,2,2,2,3,4,3,1};
                System.out.println("Maximum Points We Can Earn: "+rb.removeBoxes(boxes));
            }
            t--;
        }
    }

    public int removeBoxes(int[] boxes) {
        if(boxes.length>1){
            Map<Integer, List<Integer>>keyWithIndices = buildMapForNumberIndices(boxes);
            int [][]dp = buildDp(boxes, keyWithIndices);
            int maxPoints = dp[0][boxes.length-1];
            return maxPoints;
        }
        return boxes.length;
    }

    private Map<Integer, List<Integer>> buildMapForNumberIndices(int[] boxes) {
        Map<Integer, List<Integer>> keyWithIndices = new HashMap<>();

        for (int i = 0; i<boxes.length; i++){
            if(keyWithIndices.containsKey(boxes[i])){
                List<Integer> indexes = keyWithIndices.get(boxes[i]);
                indexes.add(i);
            }else{
                List<Integer> indexes = new ArrayList<>();
                indexes.add(i);
                keyWithIndices.put(boxes[i], indexes);
            }
        }
        return keyWithIndices;
    }

    private int[][] buildDp(int[] boxes,
                     Map<Integer, List<Integer>> keyWithIndices) {
        int n = boxes.length;
        int[][]dp = new int[n][n];
        System.out.println("keyWithIndices = "+keyWithIndices);
        for (int length = 1; length<=boxes.length; length++){
            int i = 0;
            Map<String,Integer> memorize = new HashMap<>();
            StringBuilder strBld = createString(0,  length-2, boxes);
            String str = "";
            for (int column = length -1; column<n; column++){
                strBld.append(boxes[column]);
                str = strBld.substring(i+(i*3));
                dp[i][column] = Integer.MIN_VALUE;
                dp[i][column] = getMaxPointsAtCurrentRange(dp, i, column, boxes, keyWithIndices,
                        str, memorize);
                System.out.println("dp["+i+"]["+column+"] = "+dp[i][column]);
                memorize.put(str, dp[i][column]);
                i++;
                strBld.append("```");
            }
        }
        return dp;
    }

    private StringBuilder createString(int start, int end, int[] boxes) {
        StringBuilder str = new StringBuilder();
        for (int i = start; i<=end; i++) {
            str.append(i);
            str.append("```");
        }
        return str;
    }


    private int getMaxPointsAtCurrentRange(int[][] dp, int startRange, int endRange,
                  int[] boxes, Map<Integer, List<Integer>> keyWithIndices,
                  String str, Map<String,Integer> memorize) {

        int maxPointAtCurrRange = 1;
        if(startRange == endRange)
            return maxPointAtCurrRange;
        else{
            if(memorize.containsKey(str)) {
                //System.out.println("Congo memorize help");
                return memorize.get(str);
            }
            List<Integer> currentNumberIndices = keyWithIndices.get(boxes[endRange]);
            maxPointAtCurrRange = dp[startRange][endRange - 1] + 1;
            if(currentNumberIndices.size() == 1)
                return maxPointAtCurrRange;
            else{  // find for all combinations
                int end  = binarySearchEqualToX(currentNumberIndices, endRange);
                int start = binarySearchStartRange(currentNumberIndices, startRange, end);
                if(endRange-startRange+1 == end-start+1){
                    int length = end-start+1; // all numbers are identical in this length
                    return length*length;
                }
                if(endRange-startRange+1 == end-start){
                    int length = end-start+1; // all numbers are identical in this length except 1
                    return (length*length)+1;
                }
                Queue<PairOfSameNumber_Remove_Boxes> ranges = new LinkedList<>();
                int groupLength = 2;
                int prevAdditive = 0;
                createAllSegment(start, end, groupLength, prevAdditive,
                        currentNumberIndices, dp, ranges);

                while (!ranges.isEmpty()){
                    PairOfSameNumber_Remove_Boxes prevPair = ranges.peek();
                    int temp = (prevPair.size*prevPair.size) + prevPair.additive +
                            getLeftAdditive(startRange, currentNumberIndices.get(prevPair.a), dp);
                    maxPointAtCurrRange = Math.max(maxPointAtCurrRange, temp);
                    ranges.poll();

                    if(start<=prevPair.a-1) {
                        groupLength = prevPair.size + 1;
                        prevAdditive = prevPair.additive;
                        createAllSegment(start, prevPair.a, groupLength, prevAdditive,
                                currentNumberIndices, dp, ranges);
                    }
                }
            }
        }
        return maxPointAtCurrRange;
    }

    private void createAllSegment(int start, int end, int groupLength, int prevAdditive,
                   List<Integer> currentNumberIndices, int[][]dp,
                   Queue<PairOfSameNumber_Remove_Boxes> ranges) {

        for(int i = start; i<end; i++){
            PairOfSameNumber_Remove_Boxes pair =
                    new PairOfSameNumber_Remove_Boxes(i, end);
            pair.setSize(groupLength);
            pair.setAdditive(prevAdditive + getMiddleAdditive(currentNumberIndices.get(i),
                    currentNumberIndices.get(end), dp));
            ranges.add(pair);
        }
    }

    private int getLeftAdditive(int startRange, int end, int[][] dp) {
        if(end == startRange)
            return 0;
        return dp[startRange][end-1];
    }

    private int getMiddleAdditive(int start, int end, int[][] dp) {
        if(end-start==0)
            return 0;
        else
            return dp[start+1][end-1];
    }

    private int binarySearchStartRange(List<Integer> currentNumberIndices,
                      int startRange, int end) {
        int left = 0, right = end;
        int index = end;
        while (left <= right && right<=end) {
            int mid = left + (right - left) / 2;
            if (currentNumberIndices.get(mid) == startRange){
                index = mid;
                return index;
            }
            if (currentNumberIndices.get(mid) > startRange){
                index = mid;
                right = mid - 1;
            }
            else{
                left = mid + 1;
            }
        }
        return index;
    }

    private int binarySearchEqualToX(List<Integer> currentNumberIndices, int endRange) {
        int left = 0, right = currentNumberIndices.size() - 1;
        int index = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (currentNumberIndices.get(mid) == endRange){
                index = mid;
                return index;
            }
            if (currentNumberIndices.get(mid) < endRange)
                left = mid + 1;
            else
                right = mid - 1;
        }
        return -1;
    }
}
