package Solved;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

/*
https://leetcode.com/problems/contain-virus/

Input 1:
0 1 0 0 0 0 0 1 -999
0 1 0 0 0 0 0 1 -999
0 0 0 0 0 0 0 1 -999
0 0 0 0 0 0 0 0 -999
-999
Input 1 meaning: isInfected =
[
[0,1,0,0,0,0,0,1],
[0,1,0,0,0,0,0,1],
[0,0,0,0,0,0,0,1],
[0,0,0,0,0,0,0,0]
]
Output: 10
Explanation: There are 2 contaminated regions.
On the first day, add 5 walls to quarantine the viral region on the left. The board after the
virus spreads is:
[
[0,1,0,0,0,0,1,1],
[0,1,0,0,0,0,1,1],
[0,0,0,0,0,0,1,1],
[0,0,0,0,0,0,0,1]
]
On the second day, add 5 walls to quarantine the viral region on the right. The virus is fully
contained.

Input 2:
1 1 1 -999
1 0 1 -999
1 1 1 -999
-999
Output: 4
Explanation: Even though there is only one cell saved, there are 4 walls built.
Notice that walls are only built on the shared boundary of two different cells.

Input 3:
1 1 1 0 0 0 0 0 0 -999
1 0 1 0 1 1 1 1 1 -999
1 1 1 0 0 0 0 0 0 -999
-999
Output: 13
Explanation: The region on the left only builds two new walls.
*/


class CoordinatesContainVirus{
    int i;
    int j;
}

class AnswerHelperContainVirus{
    int maxContaminated;
    int onePointIdOfMaxRegion;
    List<Integer> onePointOfOtherRegions;
    AnswerHelperContainVirus(){
        maxContaminated = 0;
        onePointOfOtherRegions = new ArrayList<>();
    }
}

public class ContainVirus {
    public static void main(String[] args) {
        ContainVirus cv = new ContainVirus();
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
                List<List<Integer>> listOfIsInfected = new ArrayList<>();
                int x;
                System.out.println("Enter Contaminated Region Info. 0->Not Infected. 1-> For Infected. " +
                        "For Stop Insertion At Any Row Press -999");
                x = input.nextInt();
                while(x!=-999) {
                    List<Integer> rowOfIsInfected = new ArrayList<>();
                    while(x!=-999) {
                        rowOfIsInfected.add(x);
                        x = input.nextInt();
                    }
                    if(!rowOfIsInfected.isEmpty())
                        listOfIsInfected.add(rowOfIsInfected);
                    x = input.nextInt();
                }
                int[][] isInfected = listOfIsInfected.stream().map(u -> u.stream().mapToInt(i->i).toArray()).toArray(
                        int[][]::new);

                System.out.println();
                int minWalls = cv.containVirus(isInfected);
                System.out.println("Min. Number Of Walls To Save Region: "+minWalls);
            }
            else
            {
                int[][] isInfected = {{0,1,0,0,0,0,0,1},
                                {0,1,0,0,0,0,0,1},
                                {0,0,0,0,0,0,0,1},
                                {0,0,0,0,0,0,0,0}};

                System.out.println();
                int minWalls = cv.containVirus(isInfected);
                System.out.println("Min. Number Of Walls To Save Region: "+minWalls);
            }
            t--;
        }
    }

    public int containVirus(int[][] isInfected) {
        int walls = 0;
        AnswerHelperContainVirus answerHelperContainVirus = findNextRedRegion(isInfected);
        while(answerHelperContainVirus.maxContaminated!=0) {
            walls += addWalls(answerHelperContainVirus, isInfected);
            spreadOne(answerHelperContainVirus, isInfected);
            answerHelperContainVirus = findNextRedRegion(isInfected);
        }
        return walls;
    }

    private void spreadOne(AnswerHelperContainVirus answerHelperContainVirus, int[][] isInfected) {
        Set<Integer> visitedIds = new HashSet<>();
        Queue<Integer> quId = new LinkedList<>();

        for(int i = 0; i<answerHelperContainVirus.onePointOfOtherRegions.size(); i++){
            quId.add(answerHelperContainVirus.onePointOfOtherRegions.get(i));
            visitedIds.add(answerHelperContainVirus.onePointOfOtherRegions.get(i));

            while(!quId.isEmpty()){
                int pollId = quId.poll();
                CoordinatesContainVirus coordinatesContainVirus =
                        giveCoordinatesToUniqueId(pollId, isInfected);
                int currI = coordinatesContainVirus.i;
                int currJ = coordinatesContainVirus.j;

                //right neighbour -> j + 1
                if(currJ<isInfected[currI].length-1){
                    spreadOneHelper(currI, currJ + 1, isInfected, quId, visitedIds);
                }

                //left neighbour -> j - 1
                if(currJ>0){
                    spreadOneHelper(currI, currJ - 1, isInfected, quId, visitedIds);
                }
                //down neighbour -> i + 1
                if(currI<isInfected.length-1){
                    spreadOneHelper(currI + 1, currJ, isInfected, quId, visitedIds);
                }
                //up neighbour -> i - 1
                if(currI>0){
                    spreadOneHelper(currI -  1, currJ, isInfected, quId, visitedIds);
                }
            }
        }
    }

    private void spreadOneHelper(int neighI, int neighJ, int[][] isInfected,
               Queue<Integer> quId, Set<Integer> visitedIds) {
        int neighId = getId(neighI, neighJ, isInfected);

        if(isInfected[neighI][neighJ] == 0){
            isInfected[neighI][neighJ] = 1;
            visitedIds.add(neighId);
        }else if(isInfected[neighI][neighJ] == 1 && !visitedIds.contains(neighId)){
            quId.add(neighId);
            visitedIds.add(neighId);
        }
    }

    private int addWalls(AnswerHelperContainVirus answerHelperContainVirus, int[][] isInfected) {
        Queue<Integer> quId = new LinkedList<>();
        Set<Integer> visitedIds = new HashSet<>();

        quId.add(answerHelperContainVirus.onePointIdOfMaxRegion);
        visitedIds.add(answerHelperContainVirus.onePointIdOfMaxRegion);
        int walls = 0;
        while (!quId.isEmpty()){

            int pollId = quId.poll();
            CoordinatesContainVirus coordinatesContainVirus =
                    giveCoordinatesToUniqueId(pollId, isInfected);
            int currI = coordinatesContainVirus.i;
            int currJ = coordinatesContainVirus.j;

            if(isInfected[currI][currJ] == 1) {
                isInfected[currI][currJ] = 2;
                //right neighbour -> j + 1
                if (currJ < isInfected[currI].length - 1) {
                    walls += addWallsHelper(currI, currJ + 1, isInfected, quId, visitedIds);
                }

                //left neighbour -> j - 1
                if (currJ > 0) {
                    walls += addWallsHelper(currI, currJ - 1, isInfected, quId, visitedIds);
                }
                //down neighbour -> i + 1
                if (currI < isInfected.length - 1) {
                    walls += addWallsHelper(currI + 1, currJ, isInfected, quId, visitedIds);
                }
                //up neighbour -> i - 1
                if (currI > 0) {
                    walls += addWallsHelper(currI - 1, currJ, isInfected, quId, visitedIds);
                }
            }
        }
        return walls;
    }

    private int addWallsHelper(int neighI, int neighJ, int[][] isInfected,
          Queue<Integer> quId, Set<Integer> visitedIds) {
        int neighId = getId(neighI, neighJ, isInfected);
        if (isInfected[neighI][neighJ] == 1 && !visitedIds.contains(neighId)){
            quId.add(neighId);
            visitedIds.add(neighId);
        }else if(isInfected[neighI][neighJ] == 0){
            return 1;
        }
        return 0;
    }

    private AnswerHelperContainVirus findNextRedRegion(int[][] isInfected) {
        AnswerHelperContainVirus answerHelperContainVirus = new AnswerHelperContainVirus();
        Set<Integer> visited = new HashSet<>();
        for(int i = 0; i<isInfected.length; i++){
            for (int j = 0; j<isInfected[i].length; j++){
                if(isInfected[i][j] == 1 && !visited.contains(getId(i, j, isInfected))){
                    BFSSearch(i, j, isInfected, visited, answerHelperContainVirus);
                }
            }
        }
        return answerHelperContainVirus;
    }

    private void BFSSearch(int i, int j, int[][] isInfected, Set<Integer> visited,
              AnswerHelperContainVirus answerHelperContainVirus) {

        Queue<Integer> queue = new LinkedList<>();
        int id = getId(i, j, isInfected);
        queue.add(id);
        visited.add(id);
        Set<Integer> notContaminated = new HashSet<>();
        int pollId = -1;

        while(!queue.isEmpty()){

            pollId = queue.poll();
            CoordinatesContainVirus coordinatesContainVirus =
                    giveCoordinatesToUniqueId(pollId, isInfected);
            int currI = coordinatesContainVirus.i;
            int currJ = coordinatesContainVirus.j;

            //right neighbour -> j + 1
            if(currJ<isInfected[currI].length-1){
                addContaminatedAndNonContaminatedRegions(currI, currJ + 1,
                        queue, visited, notContaminated, isInfected);
            }

            //left neighbour -> j - 1
            if(currJ>0){
                addContaminatedAndNonContaminatedRegions(currI, currJ - 1,
                        queue, visited, notContaminated, isInfected);
            }
            //down neighbour -> i + 1
            if(currI<isInfected.length-1){
                addContaminatedAndNonContaminatedRegions(currI + 1, currJ,
                        queue, visited, notContaminated, isInfected);
            }
            //up neighbour -> i - 1
            if(currI>0){
                addContaminatedAndNonContaminatedRegions(currI - 1, currJ,
                        queue, visited, notContaminated, isInfected);
            }
        }
        // saveInAnswer
        saveInAnswerHelper(answerHelperContainVirus, notContaminated, pollId);
    }

    private void saveInAnswerHelper(AnswerHelperContainVirus answerHelperContainVirus,
                Set<Integer> notContaminated, int pollId) {
        if(pollId!=-1) {
            if (answerHelperContainVirus.maxContaminated == 0) {
                answerHelperContainVirus.maxContaminated = notContaminated.size();
                answerHelperContainVirus.onePointIdOfMaxRegion = pollId;
            } else if (answerHelperContainVirus.maxContaminated >= notContaminated.size()) {
                answerHelperContainVirus.onePointOfOtherRegions.add(pollId);
            } else if (answerHelperContainVirus.maxContaminated < notContaminated.size()) {
                answerHelperContainVirus.maxContaminated = notContaminated.size();
                answerHelperContainVirus.onePointOfOtherRegions.add(
                        answerHelperContainVirus.onePointIdOfMaxRegion);
                answerHelperContainVirus.onePointIdOfMaxRegion = pollId;
            }
        }
    }

    private void addContaminatedAndNonContaminatedRegions(int neighI, int neighJ,
             Queue<Integer> queue, Set<Integer> visited, Set<Integer> notContaminated, int[][] isInfected) {

        int neighId = getId(neighI, neighJ, isInfected);
        if(isInfected[neighI][neighJ] == 1 && !visited.contains(neighId)){
            queue.add(neighId);
            visited.add(neighId);
        }else if(isInfected[neighI][neighJ] == 0 && !notContaminated.contains(neighId)){
            notContaminated.add(neighId);
        }
    }

    private int getId(int i, int j, int[][] isInfected){
        if(i == 0){
            return j+1;
        }
        else
            return (i*isInfected[i].length)+(j+1);
    }

    private CoordinatesContainVirus giveCoordinatesToUniqueId(int id, int[][] isInfected){
        CoordinatesContainVirus coordinatesContainVirus = new CoordinatesContainVirus();
        if(id%isInfected[0].length == 0){
            coordinatesContainVirus.i = (id/isInfected[0].length) - 1;
            coordinatesContainVirus.j = isInfected[0].length - 1;
        }else{
            coordinatesContainVirus.i = (id/isInfected[0].length);
            coordinatesContainVirus.j = (id%isInfected[0].length) - 1;
        }
        return coordinatesContainVirus;
    }
}
