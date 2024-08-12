package Solved;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

/*
https://leetcode.com/problems/swim-in-rising-water/

Input:
0 2 -999
1 3 -999
-999
Input meaning: grid = [[0,2],[1,3]]
Output: 3
Explanation:
At time 0, you are in grid location (0, 0).
You cannot go anywhere else because 4-directionally adjacent neighbors have a higher elevation
than t = 0. You cannot reach point (1, 1) until time 3.
When the depth of water is 3, we can swim anywhere inside the grid.

Input:
0  1  2  3  4 -999
24 23 22 21 5 -999
12 13 14 15 16 -999
11 17 18 19 20 -999
10  9  8  7  6 -999
-999
Output: 16
Explanation: The final route is shown.
We need to wait until time 16 so that (0, 0) and (4, 4) are connected.
*/


class Pair_Swim_In_Rising_Water{
    int a;
    int b;

    public Pair_Swim_In_Rising_Water(int a, int b) {
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

public class Swim_In_Rising_Water {
    public static void main(String[] args) {
        Swim_In_Rising_Water swimInRisingWater = new Swim_In_Rising_Water();
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
                int mintTimeToReach = swimInRisingWater.swimInWater(grid);
                System.out.println("Minimum Time To Reach Destination: "+mintTimeToReach);
            }
            else
            {
                int [][]grid = {{0,2},{1,3}};
                System.out.println();
                int mintTimeToReach = swimInRisingWater.swimInWater(grid);
                System.out.println("Minimum Time To Reach Destination: "+mintTimeToReach);
            }
            t--;
        }
    }

    public int swimInWater(int[][] grid) {
        return bfsTraversal(grid);
    }

    private int bfsTraversal(int[][] grid) {
        int minTime = Integer.MAX_VALUE;
        Map<Integer, Integer> idToTime = new HashMap<>();
        Queue<Pair_Swim_In_Rising_Water> qu = new LinkedList<>();
        addInQueue(qu, 0, grid[0][0]);
        addInMap(idToTime, 0, grid[0][0]);

        while(!qu.isEmpty()){
            Pair_Swim_In_Rising_Water currPairIdAndTime = qu.poll();
            Pair_Swim_In_Rising_Water rowAndColumn = fetchCoordinate(
                    currPairIdAndTime.a, grid);
            //left -> j - 1
            if(isValid(rowAndColumn.a, rowAndColumn.b - 1, grid)
                && isNeedToAdd(rowAndColumn.a, rowAndColumn.b - 1,
                    grid, idToTime, currPairIdAndTime.b)){

                int waitTime = waitTime(rowAndColumn.a, rowAndColumn.b - 1,
                        grid, currPairIdAndTime.b);

                int leftNeighbourId = getId(rowAndColumn.a, rowAndColumn.b - 1, grid);
                addInQueue(qu, leftNeighbourId, currPairIdAndTime.b+waitTime);
                addInMap(idToTime, leftNeighbourId, currPairIdAndTime.b+waitTime);
            }
            //right -> j + 1
            if(isValid(rowAndColumn.a, rowAndColumn.b + 1, grid)
                    && isNeedToAdd(rowAndColumn.a, rowAndColumn.b + 1,
                    grid, idToTime, currPairIdAndTime.b)){

                int waitTime = waitTime(rowAndColumn.a, rowAndColumn.b + 1,
                        grid, currPairIdAndTime.b);

                int rightNeighbourId = getId(rowAndColumn.a, rowAndColumn.b + 1, grid);
                addInQueue(qu, rightNeighbourId, currPairIdAndTime.b+waitTime);
                addInMap(idToTime, rightNeighbourId, currPairIdAndTime.b+waitTime);
            }
            //up -> i - 1
            if(isValid(rowAndColumn.a - 1, rowAndColumn.b, grid)
                    && isNeedToAdd(rowAndColumn.a - 1, rowAndColumn.b,
                    grid, idToTime, currPairIdAndTime.b)){

                int waitTime = waitTime(rowAndColumn.a - 1, rowAndColumn.b,
                        grid, currPairIdAndTime.b);

                int upNeighbourId = getId(rowAndColumn.a - 1, rowAndColumn.b, grid);
                addInQueue(qu, upNeighbourId, currPairIdAndTime.b+waitTime);
                addInMap(idToTime, upNeighbourId, currPairIdAndTime.b+waitTime);
            }
            //down -> i + 1
            if(isValid(rowAndColumn.a + 1, rowAndColumn.b, grid)
                    && isNeedToAdd(rowAndColumn.a + 1, rowAndColumn.b,
                    grid, idToTime, currPairIdAndTime.b)){

                int waitTime = waitTime(rowAndColumn.a + 1, rowAndColumn.b,
                        grid, currPairIdAndTime.b);

                int downNeighbourId = getId(rowAndColumn.a + 1, rowAndColumn.b, grid);
                addInQueue(qu, downNeighbourId, currPairIdAndTime.b+waitTime);
                addInMap(idToTime, downNeighbourId, currPairIdAndTime.b+waitTime);
            }
            if(rowAndColumn.a == grid.length - 1 &&
                   rowAndColumn.b == grid[grid.length - 1].length - 1 &&
                minTime>currPairIdAndTime.b){
                minTime = currPairIdAndTime.b;
            }
        }
        return minTime;
    }

    private boolean isNeedToAdd(int nextRow, int nextColumn, int[][] grid,
                   Map<Integer, Integer> idToTime, int currRowColumnTime) {

        int nextId = getId(nextRow, nextColumn, grid);
        if(!idToTime.containsKey(nextId))
            return true;
        int prevTimeToNextId = idToTime.get(nextId);
        int currTimeToNextId = currRowColumnTime;
        if(grid[nextRow][nextColumn]>currRowColumnTime){
            currTimeToNextId+= grid[nextRow][nextColumn] - currRowColumnTime;
        }
        if(currTimeToNextId<prevTimeToNextId)
            return true;
        return false;
    }

    private int waitTime(int nextRow, int nextColumn, int[][] grid,
                         int currRowColumnTime){
        if(grid[nextRow][nextColumn]<=currRowColumnTime){
            return 0;
        }else{
            return grid[nextRow][nextColumn]-currRowColumnTime;
        }
    }

    private int getId(int row, int column, int[][] grid){
        return row*grid[row].length+column;
    }

    private Pair_Swim_In_Rising_Water fetchCoordinate(int id,
                           int[][] grid){
        int row = id/grid[0].length;
        int col = id - (row*grid[0].length);

        Pair_Swim_In_Rising_Water pair =
                new Pair_Swim_In_Rising_Water(row, col);

        return pair;
    }

    private boolean isValid(int row, int column, int[][] grid){
        if(row<0 || row>=grid.length)
            return false;
        if(column<0 || column>=grid[row].length)
            return false;
        return true;
    }

    private void addInMap(Map<Integer, Integer> idToTime,
                          int id, int time) {
        idToTime.put(id, time);
    }

    private void addInQueue(Queue<Pair_Swim_In_Rising_Water> qu,
                          int id, int time) {

        Pair_Swim_In_Rising_Water pair =
                new Pair_Swim_In_Rising_Water(id, time);
        qu.add(pair);
    }
}
