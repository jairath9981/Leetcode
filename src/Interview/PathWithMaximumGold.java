package Interview;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
Input 1:
0 6 0 -999
5 8 7 -999
0 9 0 -999
-999
Input 1 Meaning: grid = [[0,6,0],[5,8,7],[0,9,0]]
Output: 24
Explanation:
Path to get the maximum gold, 9 -> 8 -> 7.

Input 2:
1 0 7 -999
2 0 6 -999
3 4 5 -999
0 3 0 -999
9 0 20 -999
-999
Output: 28
Explanation:
Path to get the maximum gold, 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7.
*/


class Pair_PathWithMaximumGold {
    int a;
    int b;

    Pair_PathWithMaximumGold(int a, int b){
        this.a = a;
        this.b = b;
    }
}

public class PathWithMaximumGold {
    public static void main(String[] args) {
        PathWithMaximumGold pathWithMaximumGold = new PathWithMaximumGold();
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
                System.out.println("Enter Grid Values. For Stop Insertion At Any Row Press -999");
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
                int maxGold = pathWithMaximumGold.getMaximumGold(grid);
                System.out.println("Maximum Gold Collected: "+maxGold);
            }
            else
            {
                int [][]grid = {{1,2,3},{4,0,5}};
                System.out.println();
                int maxGold = pathWithMaximumGold.getMaximumGold(grid);
                System.out.println("Maximum Gold Collected: "+maxGold);
            }
            t--;
        }
    }

    public int getMaximumGold(int[][] grid) {
        int maxGoldPicked = 0;
        Pair_PathWithMaximumGold[] moveDirectionArr = addValidDirection();

        for(int i = 0; i<grid.length; i++){
            for(int j = 0; j<grid[i].length; j++){
                int maxGoldFromThisCell = DFSMaxGoldSum(grid, i, j, moveDirectionArr);
                maxGoldPicked = Math.max(maxGoldPicked, maxGoldFromThisCell);
            }
        }
        return maxGoldPicked;
    }

    private int DFSMaxGoldSum(int[][] grid,
                       int currI, int currJ,
                        Pair_PathWithMaximumGold[] moveValidDirectionArr) {
        if(!validCell(grid, currI, currJ ) || grid[currI][currJ] == 0)
            return 0;
        int curr = grid[currI][currJ];
        grid[currI][currJ] = 0; // visited
        int maxGold = 0;
        for(int i = 0; i<moveValidDirectionArr.length; i++){
            maxGold = Math.max(maxGold, DFSMaxGoldSum(grid,
                    currI+moveValidDirectionArr[i].a, currJ+moveValidDirectionArr[i].b,
                    moveValidDirectionArr));
        }
        /*
            // all path covered from Parent current Direction,
            // open for parent remaining Direction
         */
        grid[currI][currJ] = curr;
        return grid[currI][currJ] + maxGold;
    }

    private boolean validCell(int[][] grid, int i, int j) {
        if(i<0 || j<0 || i>=grid.length || j>= grid[i].length)
            return false;
        return true;
    }

    private Pair_PathWithMaximumGold[] addValidDirection() {
        Pair_PathWithMaximumGold[] moveDirectionArr = new Pair_PathWithMaximumGold[4];

        //top
        moveDirectionArr[0] = new Pair_PathWithMaximumGold(-1, 0);
        //bottom
        moveDirectionArr[1] = new Pair_PathWithMaximumGold(1, 0);
        //right
        moveDirectionArr[2] = new Pair_PathWithMaximumGold(0, 1);
        //left
        moveDirectionArr[3] = new Pair_PathWithMaximumGold(0, -1);

        return moveDirectionArr;
    }
}
