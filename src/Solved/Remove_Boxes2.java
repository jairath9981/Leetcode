package Solved;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
https://leetcode.com/problems/remove-boxes/

https://www.youtube.com/watch?v=PfdYtfVkI6Y

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

Input: 1  2  2  1  1  1  2  1  1  2  1  2  1  1  2  2  1  1  2  2  1  1  1  2  2  2  2  1  2  1  1  2  2  1  2  1  2  2  2  2  2  1  2  1  2  2  1  1  1  2  2  1  2  1  2  2  1  2  1  1  1  2  2  2  2  2  1  2  2  2  2  2  1  1  1  1  1  2  2  2  2  2  1  1  1  1  2  2  1  1  1  1  1  1  1  2  1  2  2  1  -999
Output:
 */
public class Remove_Boxes2 {
    public static void main(String[] args) {
        Remove_Boxes2 rb2 = new Remove_Boxes2();
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
                System.out.println("Maximum Points We Can Earn: "+rb2.removeBoxes(boxes));
            } else {
                int []boxes = {1,3,2,2,2,3,4,3,1};
                System.out.println("Maximum Points We Can Earn: "+rb2.removeBoxes(boxes));
            }
            t--;
        }
    }

    public int removeBoxes(int[] boxes) {
        int n = boxes.length;
        int [][][]dp = new int[n][n][n];
        for(int i = 0; i<n; i++)
            for (int j = 0;j<n; j++)
                for (int k = 0; k<n; k++)
                    dp[i][j][k] = -1;
        return calculateMaxPoints(0, n-1, 0, boxes, dp);
    }

    private int calculateMaxPoints(int left, int right, int sameBox, int[]boxes,
                                   int[][][]dp) {
        //System.out.println("left = "+left+" right = "+right+"  sameBox = "+sameBox);
        if(left>right)
            return 0;
        if(dp[left][right][sameBox]>=0)
            return dp[left][right][sameBox];

        int points = ((sameBox+1)*(sameBox+1)) + calculateMaxPoints(left+1, right, 0, boxes, dp);

        for(int i = left+1; i<=right; i++){
            if(boxes[left] == boxes[i]){
                points = Math.max(points, calculateMaxPoints(left+1, i-1, 0, boxes, dp) +
                        calculateMaxPoints(i, right, sameBox+1, boxes, dp));
            }
        }
        dp[left][right][sameBox] = points;
        return points;
    }
}
