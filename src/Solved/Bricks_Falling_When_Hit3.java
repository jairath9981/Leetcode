package Solved;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
https://leetcode.com/problems/bricks-falling-when-hit/
*/

class Pair_Bricks_Falling_When_Hit3{
    int a;
    int b;

    public Pair_Bricks_Falling_When_Hit3(int a, int b){
        this.a = a;
        this.b = b;
    }
}

class UnionFind_Bricks_Falling_When_Hit3 {
    public int []parent;
    public int []rank;
    public int[] size;

    public UnionFind_Bricks_Falling_When_Hit3(int m, int n){
        parent = new int[(m*n)+1];
        rank = new int[(m*n)+1];
        size = new int[(m*n)+1];

        for(int i = 0; i<(m*n)+1; i++){
            parent[i] = i;
            rank[i] = 0;
            size[i] = 1;
        }
        size[0] = 0;
    }

    int find(int x){
        if(parent[x]!=x)
            parent[x] = find(parent[x]);
        return parent[x];
    }

    void union(int x, int y){
        int p1 = find(x);
        int p2 = find(y);

        if(p1!=p2) {
            if (rank[p1] > rank[p2]) {
                parent[p2] = p1;
                size[p1] += size[p2];
            } else if (rank[p2] > rank[p1]) {
                parent[p1] = p2;
                size[p2] += size[p1];
            } else {
                parent[p2] = p1;
                rank[p1]++;
                size[p1] += size[p2];
            }
        }
    }
}


public class Bricks_Falling_When_Hit3 {
    public static void main(String[] args) {
        Bricks_Falling_When_Hit3 bricks_falling_when_hit = new Bricks_Falling_When_Hit3();
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
        markBricksToRemove(grid, hits);
        
        int m = grid.length;
        int n = grid[0].length;
        UnionFind_Bricks_Falling_When_Hit3 unionFind = 
                new UnionFind_Bricks_Falling_When_Hit3(m, n);

        Pair_Bricks_Falling_When_Hit3[] validDirections = validDirection();
        initializeUnion(grid, validDirections, unionFind);

        int[] result = new int[hits.length];

        int currBricksConnectedToRoof = unionFind.size[unionFind.find(0)];
        for (int i = hits.length-1; i>=0; i--){
            int x = hits[i][0];
            int y = hits[i][1];
            if(grid[x][y] == 2){
                grid[x][y] = 1;
                unionHelper(grid, x, y, validDirections, unionFind);

                int newBricksConnectedToRoof = unionFind.size[unionFind.find(0)];
                if(newBricksConnectedToRoof>currBricksConnectedToRoof){
                    result[i] = newBricksConnectedToRoof - currBricksConnectedToRoof - 1;
                    currBricksConnectedToRoof = newBricksConnectedToRoof;
                }
            }
        }
        return result;
    }

    private void initializeUnion(int[][] grid,
       Pair_Bricks_Falling_When_Hit3[] validDirections,
       UnionFind_Bricks_Falling_When_Hit3 unionFind) {

        for(int i = 0; i<grid.length; i++){
            for (int j = 0; j<grid[i].length; j++){
                if(grid[i][j] == 1){
                    unionHelper(grid, i, j, validDirections, unionFind);
                }
            }
        }
    }

    private void unionHelper(int[][] grid, int row, int col,
         Pair_Bricks_Falling_When_Hit3[] validDirections,
         UnionFind_Bricks_Falling_When_Hit3 unionFind) {

        int currentId = createId(grid, row, col);

        for(int dir = 0; dir<validDirections.length; dir++){
            int neighbourRow = row+validDirections[dir].a;
            int neighbourCol = col+validDirections[dir].b;

            if(isValid(grid, neighbourRow, neighbourCol) &&
                    grid[neighbourRow][neighbourCol] == 1){
                int neighbourId = createId(grid, neighbourRow, neighbourCol);
                unionFind.union(currentId, neighbourId);
            }
        }

        if(row == 0)
            unionFind.union(0, currentId);
    }

    private void markBricksToRemove(int[][] grid, int[][] hits) {
        for (int i = 0; i<hits.length; i++){
            int x = hits[i][0];
            int y = hits[i][1];
            if(grid[x][y] == 1)
                grid[x][y] = 2;
        }
    }

    private boolean isValid(int[][] grid, int row, int col) {
        if (row<0 || col<0 || row>=grid.length || col>=grid[row].length)
            return false;
        return true;
    }

    private int createId(int[][] grid, int row, int col){
        if(isValid(grid, row, col))
            return ((row*grid[row].length) + col)+1;
        return -1;
    }
    
    private Pair_Bricks_Falling_When_Hit3[] validDirection() {
        Pair_Bricks_Falling_When_Hit3 []validDirection = new Pair_Bricks_Falling_When_Hit3[4];

        //top
        validDirection[0] = new Pair_Bricks_Falling_When_Hit3(-1, 0);
        //bottom
        validDirection[1] = new Pair_Bricks_Falling_When_Hit3(1, 0);
        //right
        validDirection[2] = new Pair_Bricks_Falling_When_Hit3(0, 1);
        //left
        validDirection[3] = new Pair_Bricks_Falling_When_Hit3(0, -1);
        return validDirection;
    }
}
