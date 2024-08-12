package Solved;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

/*
https://leetcode.com/problems/bricks-falling-when-hit/

Input 1:
1 0 0 0 -999
1 1 1 0 -999
-999
1 0 -999
-999
Input 1 Meaning: grid = [[1,0,0,0],[1,1,1,0]], hits = [[1,0]]
Output: [2]
Explanation: Starting with the grid:
[[1,0,0,0],
 [1,1,1,0]]
We erase the underlined brick at (1,0), resulting in the grid:
[[1,0,0,0],
 [0,1,1,0]]
The two underlined bricks are no longer stable as they are no longer connected to the top nor adjacent to another stable brick, so they will fall. The resulting grid is:
[[1,0,0,0],
 [0,0,0,0]]
Hence the result is [2].


Input 2:
1 0 0 0 -999
1 1 0 0 -999
-999
1 1 -999
1 0 -999
-999
Output: [0,0]
Explanation: Starting with the grid:
[[1,0,0,0],
 [1,1,0,0]]
We erase the underlined brick at (1,1), resulting in the grid:
[[1,0,0,0],
 [1,0,0,0]]
All remaining bricks are still stable, so no bricks fall. The grid remains the same:
[[1,0,0,0],
 [1,0,0,0]]
Next, we erase the underlined brick at (1,0), resulting in the grid:
[[1,0,0,0],
 [0,0,0,0]]
Once again, all remaining bricks are still stable, so no bricks fall.
Hence the result is [0,0].

Input 3:
1  1  1  0  1  1  1  1 -999
1  0  0  0  0  1  1  1 -999
1  1  1  0  0  0  1  1 -999
1  1  0  0  0  0  0  0 -999
1  0  0  0  0  0  0  0 -999
1  0  0  0  0  0  0  0 -999
-999
4  6 -999
3  0 -999
2  3 -999
2  6 -999
4  1 -999
5  2 -999
2  1 -999
-999
Output: [0,2,0,0,0,0,2]
*/

class Pair_Bricks_Falling_When_Hit{
    int a;
    int b;

    public Pair_Bricks_Falling_When_Hit(int a, int b){
        this.a = a;
        this.b = b;
    }
}

public class Bricks_Falling_When_Hit {
    public static void main(String[] args) {
        Bricks_Falling_When_Hit bricks_falling_when_hit = new Bricks_Falling_When_Hit();
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
                System.out.println("Enter Grid Values[0, 1]. For Stop Insertion At Any Row Press -999");
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

                List<List<Integer>> listOfHitsValues = new ArrayList<>();
                System.out.println("Enter Hits Values. For Stop Insertion At Any Row Press -999");
                x = input.nextInt();
                while(x!=-999) {
                    List<Integer> rowOfHis = new ArrayList<>();
                    while(x!=-999) {
                        rowOfHis.add(x);
                        x = input.nextInt();
                    }
                    if(!rowOfHis.isEmpty())
                        listOfHitsValues.add(rowOfHis);
                    x = input.nextInt();
                }

                int[][] hits = listOfHitsValues.stream().map( u -> u.stream().mapToInt(i->i).toArray() ).toArray(int[][]::new);

                System.out.println();

                int[] result = bricks_falling_when_hit.hitBricks(grid, hits);
                System.out.println("***** Result Array *************");
                for (int i = 0; i<result.length; i++)
                    System.out.print(result[i]+"  ");
                System.out.println();
            }
            else
            {
                int[][] grid = {{1,0,0,0},{1,1,1,0}};
                int[][] hits = {{1,0}};
                System.out.println();
                int[] result = bricks_falling_when_hit.hitBricks(grid, hits);
                System.out.println("***** Result Array *************");
                for (int i = 0; i<result.length; i++)
                    System.out.print(result[i]+"  ");
                System.out.println();
            }
            t--;
        }
    }

    public int[] hitBricks(int[][] grid, int[][] hits) {
        int []result = new int[hits.length];
        Pair_Bricks_Falling_When_Hit []validDirection = validDirection();
        for(int i = 0; i<hits.length; i++){
            int row = hits[i][0];
            int col = hits[i][1];
            
            int removed = countOfUnstableBricksAfterRemovingHitBrick(
                    grid, row, col, validDirection);
            result[i] = removed;
        }
        return result;
    }


    private int countOfUnstableBricksAfterRemovingHitBrick(int[][] grid,
                int row, int col, Pair_Bricks_Falling_When_Hit[] validDirection) {

        int count = 0;
        if(grid[row][col] == 1) {
            Set<Integer> visitedCellId = new HashSet<>();
            Set<Integer> leadToSuccessfulPath = new HashSet<>();

            grid[row][col] = 0;

            for (int i = 0; i < validDirection.length; i++) {

                int immediateNeighbourOfHitBrickRow = row + validDirection[i].a;
                int immediateNeighbourOfHitBrickCol = col + validDirection[i].b;
                int immediateNeighbourOfHitBrickId = createId(grid,
                        immediateNeighbourOfHitBrickRow, immediateNeighbourOfHitBrickCol);

                if (immediateNeighbourOfHitBrickId != -1 &&
                        grid[immediateNeighbourOfHitBrickRow][immediateNeighbourOfHitBrickCol] == 1) {
                    Queue<Integer> cellIdQueue = new LinkedList<>();
                    List<Integer> visitedPaths = new ArrayList<>();

                    cellIdQueue.add(immediateNeighbourOfHitBrickId);
                    visitedCellId.add(immediateNeighbourOfHitBrickId);
                    visitedPaths.add(immediateNeighbourOfHitBrickId);

                    int flag = bfsToContUnstableBricks(grid, cellIdQueue,
                            visitedCellId, leadToSuccessfulPath, visitedPaths, validDirection);
                    if (flag == 0) {
                        count += visitedPaths.size();
                        markUnstableBricksOnGrid(grid, visitedPaths);
                    }
                }
            }
        }
        return count;
    }

    private int  bfsToContUnstableBricks(int[][] grid, Queue<Integer> cellIdQueue,
                     Set<Integer> visitedCellId, Set<Integer> leadToSuccessfulPath,
          List<Integer> visitedPaths, Pair_Bricks_Falling_When_Hit[] validDirection) {

        while (!cellIdQueue.isEmpty()) {
            int queueId = cellIdQueue.poll();
            Pair_Bricks_Falling_When_Hit currCoordinate = getCoordinateFromId(grid, queueId);

            if((currCoordinate.a == 0 && grid[currCoordinate.a][currCoordinate.b] == 1) ||
                leadToSuccessfulPath.contains(queueId)){
                addFixedBricks(leadToSuccessfulPath, visitedPaths);
                return 1;
            }else {
                for (int j = 0; j < validDirection.length; j++) {

                    int nextRow = currCoordinate.a + validDirection[j].a;
                    int nextCol = currCoordinate.b + validDirection[j].b;
                    int nextId = createId(grid, nextRow, nextCol);

                    if(leadToSuccessfulPath.contains(nextId)){
                        addFixedBricks(leadToSuccessfulPath, visitedPaths);
                        return 1;
                    }
                    if (isValid(grid, nextRow, nextCol) && !visitedCellId.contains(nextId) &&
                            grid[nextRow][nextCol] == 1) {
                        cellIdQueue.add(nextId);
                        visitedCellId.add(nextId);
                        visitedPaths.add(nextId);
                    }
                }
            }
        }
        return 0;
    }

    private void markUnstableBricksOnGrid(int[][] grid,
               List<Integer> visitedPaths) {
        for (int i = 0; i<visitedPaths.size(); i++){
            Pair_Bricks_Falling_When_Hit coordinate = getCoordinateFromId(grid, visitedPaths.get(i));

            grid[coordinate.a][coordinate.b] = 0;
        }
    }

    private void addFixedBricks(Set<Integer> leadToSuccessfulPath,
            List<Integer> visitedPaths) {
        for (int i = 0; i<visitedPaths.size(); i++)
            leadToSuccessfulPath.add(visitedPaths.get(i));
    }

    private boolean isValid(int[][] grid, int row, int col) {
        if (row<0 || col<0 || row>=grid.length || col>=grid[row].length)
            return false;
        return true;
    }

    private int createId(int[][] grid, int row, int col){
        if(isValid(grid, row, col))
            return (row*grid[row].length) + col;
        return -1;
    }

    private Pair_Bricks_Falling_When_Hit getCoordinateFromId(int[][] grid, int id){
       int row = id/grid[0].length;
       int col = id%grid[0].length;

       Pair_Bricks_Falling_When_Hit coordinate = new Pair_Bricks_Falling_When_Hit(row, col);

       return coordinate;
    }

    private Pair_Bricks_Falling_When_Hit[] validDirection() {
        Pair_Bricks_Falling_When_Hit []validDirection = new Pair_Bricks_Falling_When_Hit[4];

        //top
        validDirection[0] = new Pair_Bricks_Falling_When_Hit(-1, 0);
        //bottom
        validDirection[1] = new Pair_Bricks_Falling_When_Hit(1, 0);
        //right
        validDirection[2] = new Pair_Bricks_Falling_When_Hit(0, 1);
        //left
        validDirection[3] = new Pair_Bricks_Falling_When_Hit(0, -1);
        return validDirection;
    }
}
