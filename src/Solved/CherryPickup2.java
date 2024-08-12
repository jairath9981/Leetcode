package Solved;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
https://leetcode.com/problems/cherry-pickup/

Input 1:
0 1 -1 -999
1 0 -1 -999
1 1 1 -999
-999
Input meaning: grid = [[0,1,-1],[1,0,-1],[1,1,1]]

Output: 5
Explanation: The player started at (0, 0) and went down, down, right right to reach (2, 2).
4 cherries were picked up during this single trip, and the matrix becomes [[0,1,-1],[0,0,-1],[0,0,0]].
Then, the player went left, up, up, left to return home, picking up one more cherry.
The total number of cherries picked up is 5, and this is the maximum possible.

Input 2:
1 1 -1 -999
1 -1 1 -999
-1 1 1 -999
-999
Output: 0

Input 3:
1   1   1   1   0   0   0  -999
0   0   0   1   0   0   0  -999
0   0   0   1   0   0   1  -999
1   0   0   1   0   0   0  -999
0   0   0   1   0   0   0  -999
0   0   0   1   0   0   0  -999
0   0   0   1   1   1   1  -999
-999
Output: 15
*/


public class CherryPickup2 {
    public static void main(String[] args) {
        CherryPickup2 cp2 = new CherryPickup2();
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
                List<List<Integer>> listOfGridValues = new ArrayList<>();
                int x;
                System.out.println("Enter Grid Values[-1, 1]. For Stop Insertion At Any Row Press -999");
                x = input.nextInt();
                while(x!=-999) {
                    List<Integer> rowOfGrid = new ArrayList<>();
                    while(x!=-999) {
                        rowOfGrid.add(x);
                        x = input.nextInt();
                    }
                    if(!rowOfGrid.isEmpty())
                        listOfGridValues.add(rowOfGrid);
                    x = input.nextInt();
                }
                int[][] grid = listOfGridValues.stream().map( u -> u.stream().mapToInt(i->i).toArray() ).toArray(int[][]::new);

                System.out.println();
                int maxCherryPicked = cp2.cherryPickup(grid);
                System.out.println("Maximum Cherry That Can Be Picked: "+maxCherryPicked);
            }
            else
            {
                int [][]grid = {{0,1,-1},{1,0,-1},{1,1,1}};
                System.out.println();
                int maxCherryPicked = cp2.cherryPickup(grid);
                System.out.println("Maximum Cherry That Can Be Picked: "+maxCherryPicked);
            }
            t--;
        }
    }
    public int cherryPickup(int[][] grid) {
        int[][][] dp = initializeDp(grid.length);

        int r1 = 0, c1 = 0;
        int r2 = 0, c2 = 0;
        int maxCherry = cherryPickupDPHelper(r1, c1, r2, grid, dp);
        if(maxCherry <=0 )
            return 0;
        return maxCherry;
    }

    private int[][][] initializeDp(int n) {
        int[][][] dp = new int[n][n][n];

        for (int i = 0; i<n; i++){
            for (int j = 0; j<n; j++){
                for (int k = 0; k<n; k++){
                    dp[i][j][k] = -1;
                }
            }
        }
        return dp;
    }

    private int cherryPickupDPHelper(int r1, int c1, int r2, int[][] grid,
                                     int[][][] dp){
        int c2 = r1 + c1 - r2;
        //System.out.println("Print Coordinates: ["+r1+" - "+c1+ "], ["+r2+" - "+c2+"]");
        if(r1 >= grid.length ||  c1 >= grid[0].length ||
                r2 >= grid.length || c2 >= grid[0].length ||
                grid[r1][c1] == -1 || grid[r2][c2] == -1)
            return Integer.MIN_VALUE;

        if((r1 == grid.length - 1 && c1 == grid[0].length - 1) &&
                (r2 == grid.length - 1 && c2 == grid[0].length - 1))
            return grid[r1][c1];

        if(dp[r1][c1][r2]!=-1)
            return dp[r1][c1][r2];

        int cherry = 0;
        if(r1 == r2 && c1 == c2){
            cherry+=grid[r1][c1];
        }else {
            cherry+= grid[r1][c1];
            cherry+= grid[r2][c2];
        }

        // both p1 and p2 move horizontal
        int p1 = cherryPickupDPHelper(r1, c1+1, r2, grid, dp);
        // both p1 and p2 move vertical
        int p2 = cherryPickupDPHelper(r1+1, c1, r2+1, grid, dp);
        // p1 move horizontal and p2 move vertical
        int p3 = cherryPickupDPHelper(r1, c1+1, r2+1, grid, dp);
        // p1 move vertical and p2 move horizontal
        int p4 = cherryPickupDPHelper(r1+1, c1, r2, grid, dp);

        cherry+=Math.max(Math.max(p1, p2), Math.max(p3, p4));
        dp[r1][c1][r2] = cherry;
        return cherry;
    }
}
