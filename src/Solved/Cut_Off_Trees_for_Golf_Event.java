package Solved;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

/*
https://leetcode.com/problems/cut-off-trees-for-golf-event/

Input:

1 2 3 -999
0 0 4 -999
7 6 5 -999
-9999

Output: 6

Explanation: Following the path above allows you to cut off the trees from shortest to tallest in 6 steps.

Input:

1 2 3 -999
0 0 0 -999
7 6 5 -999
-9999
Output: -1
Explanation: The trees in the bottom row cannot be accessed as the middle row is blocked.

Input:

2 3 4 -999
0 0 5 -999
8 7 6 -999
-9999
Output: 6
Explanation: You can follow the same path as Example 1 to cut off all the trees.
Note that you can cut off the first tree at (0, 0) before making any steps.
*/

class Point_Cut_Off_Trees_for_Golf_Event{

    int x;
    int y;
    int id;
    Point_Cut_Off_Trees_for_Golf_Event(int x, int y, int id){
        this.x = x;
        this.y = y;
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        else if(!(obj instanceof Point_Cut_Off_Trees_for_Golf_Event))
            return false;
        else{
            Point_Cut_Off_Trees_for_Golf_Event point =
                    (Point_Cut_Off_Trees_for_Golf_Event) obj;
            return (point.x == this.x && point.y == this.y);
        }
    }

    @Override
    public int hashCode(){
        return this.id;
    }
}

class CoordinateValue_Cut_Off_Trees_for_Golf_Event implements
        Comparable<CoordinateValue_Cut_Off_Trees_for_Golf_Event> {

    Point_Cut_Off_Trees_for_Golf_Event point;
    int val;

     public CoordinateValue_Cut_Off_Trees_for_Golf_Event(
           Point_Cut_Off_Trees_for_Golf_Event point, int val){

        this.point = point;
        this.val = val;
    }

    @Override
    public int compareTo(CoordinateValue_Cut_Off_Trees_for_Golf_Event obj2) {

        if(this.val > obj2.val)
            return 1;
        return -1;
    }

}

class Steps_Cut_Off_Trees_for_Golf_Event{

    Point_Cut_Off_Trees_for_Golf_Event point;
    int steps;

    public  Steps_Cut_Off_Trees_for_Golf_Event(
         Point_Cut_Off_Trees_for_Golf_Event point, int steps){

        this.point = point;
        this.steps = steps;
    }
}

public class Cut_Off_Trees_for_Golf_Event {

    public static void main(String[] args) {
        Cut_Off_Trees_for_Golf_Event tcfgv = new Cut_Off_Trees_for_Golf_Event();
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
                List<List<Integer>> forest = new ArrayList<>();
                int x, y;
                System.out.println("Enter Forest Matrix Row Wise. For Stop Insertion In Row Press -999 And For Stop " +
                        "Inserting In Forest Matrix Press -9999");
                x = input.nextInt();
                while(x!=-9999) {
                    List<Integer> forestRow = new ArrayList<>();
                    while(x!=-999 && x!=-9999){
                        forestRow.add(x);
                        x = input.nextInt();
                    }
                    forest.add(forestRow);
                    if(x==-9999)
                        break;
                    x = input.nextInt();
                }
                System.out.println();
                int ans = tcfgv.cutOffTree(forest);
                System.out.println("The Minimum Steps We Need To Walk To Cut Off All The Trees: "+ans);
            }
            else
            {
                Integer [][]forestArr = {
                        {54581641,64080174,24346381,69107959},
                        {86374198,61363882,68783324,79706116},
                        {668150,92178815,89819108,94701471},
                        {83920491,22724204,46281641,47531096},
                        {89078499,18904913,25462145,60813308}
                };
                // Ans 57 for this
                List<List<Integer>> forest = Arrays.stream(forestArr).map(Arrays::asList).collect(
                        Collectors.toList());
                System.out.println();
                int ans = tcfgv.cutOffTree(forest);
                System.out.println("The Minimum Steps We Need To Walk To Cut Off All The Trees: "+ans);
            }
            t--;
        }
    }

    public int cutOffTree(List<List<Integer>> forest) {
        
        PriorityQueue<CoordinateValue_Cut_Off_Trees_for_Golf_Event> minHeap = 
                buildMinHeap(forest);
        int minStepsToCutWholeForest = 0;

        CoordinateValue_Cut_Off_Trees_for_Golf_Event prevCoordinate =
          new CoordinateValue_Cut_Off_Trees_for_Golf_Event(
           new Point_Cut_Off_Trees_for_Golf_Event(0, 0 , generateId(0, 0, forest)),
                  forest.get(0).get(0));

            while (!minHeap.isEmpty()){
                CoordinateValue_Cut_Off_Trees_for_Golf_Event nextCoordinate = minHeap.poll();
                int steps = minSteps(forest, prevCoordinate.point, nextCoordinate.point);
                if(steps>=0)
                    minStepsToCutWholeForest+=steps;
                else
                    return -1;
                prevCoordinate = nextCoordinate;
            }
        return minStepsToCutWholeForest;
    }

    private int minSteps(List<List<Integer>> forest,
         Point_Cut_Off_Trees_for_Golf_Event currPoint,
         Point_Cut_Off_Trees_for_Golf_Event destinationPoint ){

        Queue<Steps_Cut_Off_Trees_for_Golf_Event> qu = new LinkedList<>();
        Set<Point_Cut_Off_Trees_for_Golf_Event> visited = new HashSet<>();

        int stepsToReachDestination = -1;

        qu.add(new Steps_Cut_Off_Trees_for_Golf_Event(currPoint, 0));
        visited.add(currPoint);

        while (!qu.isEmpty()){
            Steps_Cut_Off_Trees_for_Golf_Event stepObj = qu.poll();
            if(stepObj.point.equals(destinationPoint)){
                stepsToReachDestination = stepObj.steps;
                break;
            }
            addNeighbour(stepObj, forest, qu, visited, 'u');
            addNeighbour(stepObj, forest, qu, visited, 'd');
            addNeighbour(stepObj, forest, qu, visited, 'r');
            addNeighbour(stepObj, forest, qu, visited, 'l');;
        }
        return stepsToReachDestination;
    }

    private void addNeighbour(Steps_Cut_Off_Trees_for_Golf_Event stepObj,
        List<List<Integer>> forest, Queue<Steps_Cut_Off_Trees_for_Golf_Event> qu,
        Set<Point_Cut_Off_Trees_for_Golf_Event> visited, char direction) {

        Point_Cut_Off_Trees_for_Golf_Event currPoint = stepObj.point;
        Point_Cut_Off_Trees_for_Golf_Event nextPoint =
                createNextPointForDirection(forest, currPoint, direction);
        if(!visited.contains(nextPoint) && isPointValid(nextPoint, forest)){
            Steps_Cut_Off_Trees_for_Golf_Event nextStep =
                    new Steps_Cut_Off_Trees_for_Golf_Event(nextPoint, stepObj.steps+1);
            qu.add(nextStep);
            visited.add(nextStep.point);
        }
    }

    private Point_Cut_Off_Trees_for_Golf_Event createNextPointForDirection(
            List<List<Integer>> forest, Point_Cut_Off_Trees_for_Golf_Event currPoint,
            char direction){

        if(direction == 'u')
            return new Point_Cut_Off_Trees_for_Golf_Event(currPoint.x-1, currPoint.y,
                    generateId(currPoint.x-1, currPoint.y, forest));
        if(direction == 'd')
            return new Point_Cut_Off_Trees_for_Golf_Event(currPoint.x+1, currPoint.y,
                    generateId(currPoint.x+1, currPoint.y, forest));
        if(direction == 'r')
            return new Point_Cut_Off_Trees_for_Golf_Event(currPoint.x, currPoint.y+1,
                    generateId(currPoint.x, currPoint.y+1, forest));
        if(direction == 'l')
            return new Point_Cut_Off_Trees_for_Golf_Event(currPoint.x, currPoint.y-1,
                    generateId(currPoint.x, currPoint.y-1, forest));

        return null;
    }

    private boolean isPointValid(Point_Cut_Off_Trees_for_Golf_Event point,
                  List<List<Integer>> forest) {
        return (point.x>=0 && point.x<forest.size() &&
                point.y>=0 && point.y<forest.get(point.x).size() &&
                forest.get(point.x).get(point.y)>0);
    }

    private PriorityQueue<CoordinateValue_Cut_Off_Trees_for_Golf_Event> buildMinHeap(
            List<List<Integer>> forest) {

        PriorityQueue<CoordinateValue_Cut_Off_Trees_for_Golf_Event> minHeap =
                new PriorityQueue<>();

        for(int i = 0; i<forest.size(); i++){
            for (int j = 0; j<forest.get(i).size(); j++){
                int treeHeight = forest.get(i).get(j);
                if(treeHeight>1 || (i == 0 && j == 0 && treeHeight>0)){
                    minHeap.add(new CoordinateValue_Cut_Off_Trees_for_Golf_Event(
                       new Point_Cut_Off_Trees_for_Golf_Event(i, j,
                            generateId(i, j, forest)), treeHeight));
                }
            }
        }
        return minHeap;
    }

    private int generateId(int i, int j, List<List<Integer>>forest){
        if(i<0 || j<0)
            return -1;
        if(i == 0)
            return j;
        else
            return i*forest.get(0).size()+j;
    }
}
