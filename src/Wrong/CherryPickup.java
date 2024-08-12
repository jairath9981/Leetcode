package Wrong;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

// Greedy Approach does not work here. Giving wrong answers. See Part 2
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

class Position_CherryPickup{
    int i;
    int j;
    int count;
    int id;
    Set<Integer> pickedCherry;

    public Position_CherryPickup(int i, int j, int count, int id, Set<Integer> pickedCherry) {
        this.i = i;
        this.j = j;
        this.id = id;
        this.count = count;

        this.pickedCherry = new HashSet<>();
        this.pickedCherry.addAll(pickedCherry);
    }

    public void setI(int i) {
        this.i = i;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setPickedCherry(Set<Integer> pickedCherry) {
        this.pickedCherry.addAll(pickedCherry);
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public int getCount() {
        return count;
    }

    public Set<Integer> getPickedCherry() {
        return pickedCherry;
    }
}

class ValidMovements{
    int i;
    int j;
    String direction;

    public ValidMovements(int i, int j, String direction) {
        this.i = i;
        this.j = j;
        this.direction = direction;
    }
}

public class CherryPickup {
    public static void main(String[] args) {
        CherryPickup cp = new CherryPickup();
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
                int maxCherryPicked = cp.cherryPickup(grid);
                System.out.println("Maximum Cherry That Can Be Picked: "+maxCherryPicked);
            }
            else
            {
                int [][]grid = {{0,1,-1},{1,0,-1},{1,1,1}};
                System.out.println();
                int maxCherryPicked = cp.cherryPickup(grid);
                System.out.println("Maximum Cherry That Can Be Picked: "+maxCherryPicked);
            }
            t--;
        }
    }

    public int cherryPickup(int[][] grid) {
        Set<Integer> pickedCherry = new HashSet<>();
        ValidMovements[] allowedMovementBottom = {new ValidMovements(0, 1, "RIGHT"),
                new ValidMovements(1, 0, "Down")}; // right, down
        int bottom = traverseBottomToPickCherry(grid, pickedCherry,
                allowedMovementBottom);
        int up = 0;
        if(bottom!=-1) {
            ValidMovements[] allowedMovementUp = {new ValidMovements(0, -1, "LEFT"),
                    new ValidMovements(-1, 0, "UP")}; //left, up

            up = traverseUpToPickCherry(grid, pickedCherry,
                    allowedMovementUp);
        }
        if(bottom!=-1 && up!=-1)
            return bottom+up;
        return 0;
    }

    private int traverseUpToPickCherry(int[][] grid, Set<Integer> alreadyPickedCherry,
                                       ValidMovements[] allowedMovement) {

        int maxCount = 0;
        Set<Integer> alreadyTraversed = new HashSet<>();
        Queue<Position_CherryPickup> quIndexes = new LinkedList<>();

        int gridSize  = grid.length;

        int id = gridIndexId(gridSize-1,gridSize-1, gridSize);
        quIndexes.add(new Position_CherryPickup(gridSize-1, gridSize-1, 0, id, alreadyPickedCherry));
        alreadyTraversed.add(id);


        int flag = 0;
        Position_CherryPickup finalPosition = null;
        while(!quIndexes.isEmpty()){
            Position_CherryPickup position = quIndexes.poll();

            if(position.i == 0 && position.j == 0)
                flag = 1;

            if(isCherry(position, grid, alreadyPickedCherry) == 1){
                position.count+=1;
                position.pickedCherry.add(position.id);
                if(maxCount<position.count) {
                    finalPosition = position;
                    maxCount = position.count;
                }
            }

            for(int i = 0; i<allowedMovement.length; i++){
                if(isAllowed(position.i, position.j,
                        allowedMovement[i].i, allowedMovement[i].j, grid)){
                    int newI = position.i + allowedMovement[i].i;
                    int newJ = position.j + allowedMovement[i].j;
                    id = gridIndexId(newI, newJ, gridSize);
                    if(needToAdd(position, newI, newJ, allowedMovement[i].direction, grid) &&
                            !alreadyTraversed.contains(id)){
                        quIndexes.add(new Position_CherryPickup(newI, newJ, position.count, id, position.pickedCherry));
                        alreadyTraversed.add(id);
                    }
                }
            }
        }
        if(flag == 1){
            if(finalPosition==null)
                return 0;
            else{
                alreadyPickedCherry.addAll(finalPosition.pickedCherry);
                return finalPosition.count;
            }
        }
        return -1;
    }

    private int traverseBottomToPickCherry(int[][] grid, Set<Integer> alreadyPickedCherry,
                   ValidMovements[] allowedMovement) {
        int maxCount = 0;
        Set<Integer> alreadyTraversed = new HashSet<>();
        Queue<Position_CherryPickup> quIndexes = new LinkedList<>();

        int gridSize  = grid.length;

        int id = gridIndexId(0,0, gridSize);
        quIndexes.add(new Position_CherryPickup(0, 0, 0, id, alreadyPickedCherry));
        alreadyTraversed.add(id);


        int flag = 0;
        Position_CherryPickup finalPosition = null;
        while(!quIndexes.isEmpty()){
            Position_CherryPickup position = quIndexes.poll();

            if(position.i == gridSize-1 && position.j == gridSize-1)
                flag = 1;

            if(isCherry(position, grid, alreadyPickedCherry) == 1){
                position.count+=1;
                position.pickedCherry.add(position.id);
                if(maxCount<position.count) {
                    finalPosition = position;
                    maxCount = position.count;
                }
            }

            for(int i = 0; i<allowedMovement.length; i++){
                if(isAllowed(position.i, position.j,
                        allowedMovement[i].i, allowedMovement[i].j, grid)){
                    int newI = position.i + allowedMovement[i].i;
                    int newJ = position.j + allowedMovement[i].j;
                    id = gridIndexId(newI, newJ, gridSize);
                    if(needToAdd(position, newI, newJ, allowedMovement[i].direction, grid) &&
                            !alreadyTraversed.contains(id)){
                        quIndexes.add(new Position_CherryPickup(newI, newJ, position.count, id, position.pickedCherry));
                        alreadyTraversed.add(id);
                    }
                }
            }
        }
        if(flag == 1 && finalPosition!=null){
            alreadyPickedCherry.addAll(finalPosition.pickedCherry);
            return finalPosition.count;
        }
        return -1;
    }

    private boolean needToAdd(Position_CherryPickup position, int newI, int newJ, String direction, int[][] grid) {
        if(grid[position.i][position.j] == 1){
            return true;
        }
        if(grid[position.i][position.j] == 0){
            if(direction.equals("Down")){
                if(isAllowed(newI, newJ, 0, -1, grid)){
                    int newII = newI + 0;
                    int newJJ = newJ - 1;
                    if(grid[newII][newJJ] == 1){
                        return false;
                    }else{
                        return true;
                    }
                }else{
                    return true;
                }
            }
            else if(direction.equals("RIGHT")){
                if(isAllowed(newI, newJ, -1, 0, grid)){
                    int newII = newI -1;
                    int newJJ = newJ + 0;
                    if(grid[newII][newJJ] == 1){
                        return false;
                    }else{
                        return true;
                    }
                }else{
                    return true;
                }
            }
            else if(direction.equals("UP")){
                if(isAllowed(newI, newJ, 0, 1, grid)){
                    int newII = newI + 0 ;
                    int newJJ = newJ + 1;
                    if(grid[newII][newJJ] == 1){
                        return false;
                    }else{
                        return true;
                    }
                }else{
                    return true;
                }
            }
            else if(direction.equals("LEFT")){
                if(isAllowed(newI, newJ, 1, 0, grid)){
                    int newII = newI + 1 ;
                    int newJJ = newJ + 0;
                    if(grid[newII][newJJ] == 1){
                        return false;
                    }else{
                        return true;
                    }
                }else{
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isAllowed(int prevI, int prevJ, int iMovement, int jMovement, int[][] grid) {
        int newI = prevI + iMovement;
        int newJ = prevJ + jMovement;

        if(newI>=0 && newI<grid.length &&
                newJ>=0 && newJ<grid[newI].length &&
                grid[newI][newJ]!=-1)
            return true;
        return false;
    }

    private int isCherry(Position_CherryPickup position, int[][] grid,
                         Set<Integer> alreadyPickedCherry) {
        int id = gridIndexId(position.i,position.j, grid.length);
        if(grid[position.i][position.j] == 1 && !alreadyPickedCherry.contains(id)){
            return 1;
        }
        return 0;
    }

    private int gridIndexId(int i, int j, int len){
        if(i == 0){
            return j;
        }
        return (i*len)+j;
    }
}
