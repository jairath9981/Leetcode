package Solved;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

/*
https://leetcode.com/problems/bricks-falling-when-hit/
*/

class Pair_Bricks_Falling_When_Hit2{
    int a;
    int b;

    public Pair_Bricks_Falling_When_Hit2(int a, int b){
        this.a = a;
        this.b = b;
    }
}

public class Bricks_Falling_When_Hit2 {
    public static void main(String[] args) {
        Bricks_Falling_When_Hit2 bricks_falling_when_hit = new Bricks_Falling_When_Hit2();
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
        int[]result = new int[hits.length];

        Map<Integer, Set<Integer>> powerGivers = parentToChildRelationship(grid);
        Map<Integer, Set<Integer>> powerTakers = childToParentRelationship(powerGivers);
//        System.out.println("powerGivers: "+powerGivers);
//        System.out.println("powerTakers: "+powerTakers);
        for (int i= 0; i<hits.length; i++){
            int row = hits[i][0];
            int col = hits[i][1];
            int fall = countBricksFall(grid, row, col, powerGivers, powerTakers);
            result[i] = fall;
        }
        return result;
    }

    private int countBricksFall(int[][] grid, int row, int col,
           Map<Integer, Set<Integer>> powerGivers, Map<Integer, Set<Integer>> powerTakers) {
        int count = 0;
        int id = createId(grid, row, col);
//        grid[row][col] = 0;
        if(powerGivers.containsKey(id) || powerTakers.containsKey(id)){
            Queue<Pair_Bricks_Falling_When_Hit2> qu = new LinkedList<>();
            addInFallQueue(qu, id, powerGivers.get(id));
            maintainPowerGiverMap(id, powerGivers, powerTakers);
            while(!qu.isEmpty()){
                Pair_Bricks_Falling_When_Hit2 quPair = qu.poll();
                Pair_Bricks_Falling_When_Hit2 quCoordinate = getCoordinateFromId(grid, quPair.a);
                if(powerTakers.get(quPair.a).size() == 1
                        //&& grid[quCoordinate.a][quCoordinate.b] == 1
                ){
                    count++;
                    //grid[quCoordinate.a][quCoordinate.b] = 0;
                    addInFallQueue(qu, quPair.a, powerGivers.get(quPair.a));
                    maintainPowerGiverMap(quPair.a, powerGivers, powerTakers);
                }
                removeFromMap(powerTakers, quPair.a, quPair.b);
            }
        }
        return count;
    }

    private void maintainPowerGiverMap(int id,
         Map<Integer, Set<Integer>> powerGivers,
         Map<Integer, Set<Integer>> powerTakers) {

        powerGivers.remove(id);
        Set<Integer> powerTakingFrom = powerTakers.get(id);
        powerTakers.remove(id);
        if(powerTakingFrom!=null) {
            for (int powerSender : powerTakingFrom) {
                removeFromMap(powerGivers, powerSender, id);
            }
        }
    }

    private void removeFromMap(Map<Integer, Set<Integer>> map, int key, int value) {
        if(map.get(key)!=null) {
            if (map.get(key).size() > 1)
                map.get(key).remove(value);
            else
                map.remove(key);
        }
    }

    private void addInFallQueue(Queue<Pair_Bricks_Falling_When_Hit2> qu, int id,
                                Set<Integer> setOfPowerSendTo) {
        if(setOfPowerSendTo!=null) {
            for (int powerSendTo : setOfPowerSendTo) {
                qu.add(new Pair_Bricks_Falling_When_Hit2(powerSendTo, id));
            }
        }
    }

    private Map<Integer, Set<Integer>> parentToChildRelationship(int[][] grid) {
        Map<Integer, Set<Integer>> powerGivers = new HashMap<>();
        Pair_Bricks_Falling_When_Hit2[] validDirections = validDirection();
        // Iterate On Zeroth Row
        for(int j = 0; j<grid[0].length; j++){
            if(grid[0][j] == 1){
                //System.out.println("J: "+j);
                parentToChildRelationshipHelper(grid, 0, j, validDirections,
                        powerGivers);
            }
        }
        return powerGivers;
    }

    private void parentToChildRelationshipHelper(
            int[][] grid, int rowPowerStarter, int colPowerStarter,
      Pair_Bricks_Falling_When_Hit2[] validDirections, Map<Integer, Set<Integer>> powerGivers) {

        Set<Integer> addedAsPowerGiver = new HashSet<>();
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> qu = new LinkedList<>();

        int id = createId(grid, rowPowerStarter, colPowerStarter);
        visited.add(id);
        qu.add(id);

        while (!qu.isEmpty()){
            int currentId = qu.poll();
            Pair_Bricks_Falling_When_Hit2 currentCoordinate = getCoordinateFromId(grid, currentId);

            for (int dir = 0; dir<validDirections.length; dir++){
                int neighbourRow = currentCoordinate.a + validDirections[dir].a;
                int neighbourCol = currentCoordinate.b + validDirections[dir].b;
                int neighbourId = createId(grid, neighbourRow, neighbourCol);
                if(isValid(grid, neighbourRow, neighbourCol) &&
                        grid[neighbourRow][neighbourCol] == 1 &&
                        !addedAsPowerGiver.contains(neighbourId) &&
                        (!powerGivers.containsKey(currentId) ||
                       !powerGivers.get(currentId).contains(neighbourId))){

                    addInMapKeyIntValueSetOfInt(powerGivers, currentId, neighbourId);
                    addedAsPowerGiver.add(currentId);
                    if(!visited.contains(neighbourId)) {
                        qu.add(neighbourId);
                        visited.add(neighbourId);
                    }
                }
            }
        }
    }


    private Map<Integer, Set<Integer>> childToParentRelationship(Map<Integer, Set<Integer>> powerGivers) {
        Map<Integer, Set<Integer>> powerTakers = new HashMap<>();
        for (Map.Entry<Integer, Set<Integer>>map: powerGivers.entrySet()){
            int key = map.getKey();
            Set<Integer> value = map.getValue();
            for (Integer powerTaker: value){
                addInMapKeyIntValueSetOfInt(powerTakers, powerTaker, key);
            }
        }
        return powerTakers;
    }

    private void addInMapKeyIntValueSetOfInt(Map<Integer, Set<Integer>> map,
                                             int key, int valueOfSet) {
        if(!map.containsKey(key)){
            map.put(key, new HashSet<>());
        }
        map.get(key).add(valueOfSet);
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

    private Pair_Bricks_Falling_When_Hit2 getCoordinateFromId(int[][] grid, int id){
        int row = id/grid[0].length;
        int col = id%grid[0].length;

        Pair_Bricks_Falling_When_Hit2 coordinate = new Pair_Bricks_Falling_When_Hit2(row, col);

        return coordinate;
    }

    private Pair_Bricks_Falling_When_Hit2[] validDirection() {
        Pair_Bricks_Falling_When_Hit2 []validDirection = new Pair_Bricks_Falling_When_Hit2[4];

        //top
        validDirection[0] = new Pair_Bricks_Falling_When_Hit2(-1, 0);
        //bottom
        validDirection[1] = new Pair_Bricks_Falling_When_Hit2(1, 0);
        //right
        validDirection[2] = new Pair_Bricks_Falling_When_Hit2(0, 1);
        //left
        validDirection[3] = new Pair_Bricks_Falling_When_Hit2(0, -1);
        return validDirection;
    }
}
