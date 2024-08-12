package Solved;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/*
https://leetcode.com/problems/erect-the-fence/

Input:
1 1
2 2
2 0
2 4
3 3
4 2
-9 -9
Input meaning: trees Coordinates = [[1,1],[2,2],[2,0],[2,4],[3,3],[4,2]]
Output: [[1,1],[2,0],[3,3],[2,4],[4,2]]

Input:
1 2
2 2
4 2
-9 -9
Output: [[4,2],[2,2],[1,2]]

Input:
0  2
1  1
2  2
2  4
4  2
3  3
-9 -9
Output
[[4,2],[2,4],[0,2],[3,3],[1,1]]

Input:
3  7
6  8
7  8
11  10
4  3
8  5
7  13
4  13
-9 -9
Output:
[[11,10],[4,3],[4,13],[3,7],[8,5],[7,13]]


Input:
3  0
4  0
5  0
6  1
7  2
7  3
7  4
6  5
5  5
4  5
3  5
2  5
1  4
1  3
1  2
2  1
4  2
0  3
-9 -9
Output:
[[7,4],[3,0],[1,2],[2,5],[5,5],[4,5],[1,4],[2,1],[3,5],[0,3],[6,5],[7,2],[7,3],[4,0],[5,0],[6,1]]
[     ,     ,    ,     ,      ,     ,     ,     ,     ,     ,     ,      ,[7,3],      ,    ,     ]
 */

class Point_Erect_the_Fence{
    int x;
    int y;
    Point_Erect_the_Fence(int a, int b){
        this.x = a;
        this.y = b;
    }

    @Override
    public String toString() {
        return this.x + "```" + this.y;
    }
}

class EquationOfLine_Erect_the_Fence{
    double slope;
    double constant;

    public EquationOfLine_Erect_the_Fence(double m, double c) {
        this.slope = m;
        this.constant = c;
    }
}

public class Erect_the_Fence {
    public static void main(String[] args) {
        Erect_the_Fence etf = new Erect_the_Fence();
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
                List<List<Integer>> treeCoordinatesList = new ArrayList<>();
                int x, y;
                System.out.println("Enter Tree Coordinates For Stop Insertion Enter Any Negative Coordinates ");
                x = input.nextInt();
                y = input.nextInt();
                while(x>=0 && y>=0) {
                    treeCoordinatesList.add(List.of(x, y));
                    x = input.nextInt();
                    y = input.nextInt();
                }
                int[][] trees = treeCoordinatesList.stream().map( u -> u.stream().mapToInt(i->i).toArray() ).toArray(int[][]::new);

                int[][] borderTrees = etf.outerTrees(trees);
                System.out.println("Border Trees Are: ");
                etf.print2dArray(borderTrees);
            }
            else
            {
                int [][]trees = {{1,1},{2,2},{2,0},{2,4},{3,3},{4,2}};
                int[][] borderTrees = etf.outerTrees(trees);
                System.out.println("Border Trees Are: ");
                etf.print2dArray(borderTrees);
            }
            t--;
        }
    }

    public int[][] outerTrees(int[][] trees) {
        Map<Integer, List<Integer> > x_CoordinateToY_Coordinates = new HashMap<>();
        List<Integer> xCoordinates = new ArrayList<>();
        buildMap(trees, x_CoordinateToY_Coordinates, xCoordinates);

        Set<String> pointsOnUpperBoundary = new HashSet<>();
        Set<String> pointsOnLowerBoundary = new HashSet<>();
        addOuterBoundaryPoints(x_CoordinateToY_Coordinates, xCoordinates, pointsOnUpperBoundary, pointsOnLowerBoundary);

        pointsOnUpperBoundary.addAll(pointsOnLowerBoundary);
        int[][] resultPoints = new int[pointsOnUpperBoundary.size()][2];
        int i = 0;
        for (String strPoint: pointsOnUpperBoundary){
            String []str_arr = strPoint.split("```");

            resultPoints[i][0] = Integer.parseInt(str_arr[0]);
            resultPoints[i][1] = Integer.parseInt(str_arr[1]);

            i++;
        }
        return resultPoints;
    }

    private void addOuterBoundaryPoints(
            Map<Integer, List<Integer>> x_CoordinateToY_Coordinates,
            List<Integer> xCoordinates, Set<String> pointsOnUpperBoundary, Set<String> pointsOnLowerBoundary) {

        int leftMostX_Coordinate = xCoordinates.get(0);
        List<Integer> leftX_y_Coordinates = x_CoordinateToY_Coordinates.get(leftMostX_Coordinate);
        Point_Erect_the_Fence leftStartPoint = new Point_Erect_the_Fence(leftMostX_Coordinate,
                leftX_y_Coordinates.get(0));
        pointsOnUpperBoundary.add(leftStartPoint.toString());
        Point_Erect_the_Fence leftStartPointUp = addVerticalUpPoints(leftMostX_Coordinate, leftX_y_Coordinates,
                pointsOnUpperBoundary);

        int rightMostX_Coordinate = xCoordinates.get(xCoordinates.size() - 1);
        List<Integer> rightX_y_Coordinates = x_CoordinateToY_Coordinates.get(rightMostX_Coordinate);
        Point_Erect_the_Fence rightEndPoint = new Point_Erect_the_Fence(rightMostX_Coordinate,
                rightX_y_Coordinates.get(0));
        pointsOnUpperBoundary.add(rightEndPoint.toString());
        Point_Erect_the_Fence rightEndPointUp = addVerticalUpPoints(rightMostX_Coordinate, rightX_y_Coordinates,
                pointsOnUpperBoundary);

        addUpperBoundary(leftStartPointUp, rightEndPointUp, x_CoordinateToY_Coordinates,
                xCoordinates, pointsOnUpperBoundary);
        addLowerBoundary(leftStartPoint, rightEndPoint, x_CoordinateToY_Coordinates,
                xCoordinates, pointsOnLowerBoundary);
    }

    private Point_Erect_the_Fence addVerticalUpPoints(int x, List<Integer> yCoordinates,
                                     Set<String> pointsOnBoundary) {
        Point_Erect_the_Fence p1 = new Point_Erect_the_Fence(x, yCoordinates.get(0));

        for (int i = 1; i<yCoordinates.size(); i++){
            p1 = new Point_Erect_the_Fence(x, yCoordinates.get(i));
            pointsOnBoundary.add(p1.toString());
        }

        return p1;
    }

    private void addLowerBoundary(
            Point_Erect_the_Fence leftStartPoint, Point_Erect_the_Fence rightEndPoint,
            Map<Integer, List<Integer>> x_coordinateToY_coordinates, List<Integer> xCoordinates,
            Set<String> pointsOnBoundary) {

        Map<Point_Erect_the_Fence, Point_Erect_the_Fence> endToStart = new HashMap<>();
        int left = 0;
        Point_Erect_the_Fence start = leftStartPoint;
        int nextXIndex = binarySearchJustGreaterThenX(xCoordinates, left, start.x);
        left = nextXIndex;
        while(left!=-1){
            int nextX = xCoordinates.get(nextXIndex);
            List<Integer> nextYs =x_coordinateToY_coordinates .get(nextX);
            Point_Erect_the_Fence end = new Point_Erect_the_Fence(nextX, nextYs.get(0));

            addLowerBoundaryHelper(endToStart, pointsOnBoundary, start, end);

            nextXIndex = binarySearchJustGreaterThenX(xCoordinates, left, end.x);
            left = nextXIndex;
            start = end;
        }
    }

    private void addLowerBoundaryHelper(Map<Point_Erect_the_Fence, Point_Erect_the_Fence> endToStart,
                                        Set<String> pointsOnBoundary,
                                        Point_Erect_the_Fence currentStart, Point_Erect_the_Fence end) {
        if(!endToStart.isEmpty()) {
            Point_Erect_the_Fence prevPointStart = endToStart.get(currentStart); // currentStart is end of some Line Segment
            while (prevPointStart != null && isCurrentStartLieAbovePrevLine(prevPointStart, currentStart, end)) {
                // remove Start Point From Boundary
                pointsOnBoundary.remove(currentStart.toString());
                // remove from Map
                endToStart.remove(currentStart);

                currentStart = prevPointStart;
                prevPointStart = endToStart.get(currentStart);
            }
        }
        endToStart.put(end, currentStart);
        pointsOnBoundary.add(end.toString());
    }

    private void addUpperBoundary(
            Point_Erect_the_Fence leftStartPoint, Point_Erect_the_Fence rightEndPoint,
            Map<Integer, List<Integer>> x_coordinateToY_coordinates, List<Integer> xCoordinates,
            Set<String> pointsOnBoundary) {

        Map<Point_Erect_the_Fence, Point_Erect_the_Fence> endToStart = new HashMap<>();
        int left = 0;
        Point_Erect_the_Fence start = leftStartPoint;
        int nextXIndex = binarySearchJustGreaterThenX(xCoordinates, left, start.x);
        left = nextXIndex;
        while(left!=-1){
            int nextX = xCoordinates.get(nextXIndex);
            List<Integer> nextYs =x_coordinateToY_coordinates .get(nextX);
            Point_Erect_the_Fence end = new Point_Erect_the_Fence(nextX, nextYs.get(nextYs.size() - 1));

            addUpperBoundaryHelper(endToStart, pointsOnBoundary, start, end);

            nextXIndex = binarySearchJustGreaterThenX(xCoordinates, left, end.x);
            left = nextXIndex;
            start = end;
        }
    }

    private void addUpperBoundaryHelper(Map<Point_Erect_the_Fence, Point_Erect_the_Fence> endToStart,
                    Set<String> pointsOnBoundary,
                    Point_Erect_the_Fence currentStart, Point_Erect_the_Fence end) {
        if(!endToStart.isEmpty()) {
            Point_Erect_the_Fence prevPointStart = endToStart.get(currentStart); // currentStart is end of some Line Segment
            while (prevPointStart != null && isCurrentStartLieUnderPrevLine(prevPointStart, currentStart, end)) {
                // remove Start Point From Boundary
                pointsOnBoundary.remove(currentStart.toString());
                // remove from Map
                endToStart.remove(currentStart);

                currentStart = prevPointStart;
                prevPointStart = endToStart.get(currentStart);
            }
        }
        endToStart.put(end, currentStart);
        pointsOnBoundary.add(end.toString());
    }

    private EquationOfLine_Erect_the_Fence createEquationOfLine(
            Point_Erect_the_Fence p1, Point_Erect_the_Fence p2){

        double slope = ((double)p2.y - (double)p1.y)/((double)p2.x - (double)p1.x);
        double constant = (p1.y)/(slope*p1.x);

        EquationOfLine_Erect_the_Fence eq = new EquationOfLine_Erect_the_Fence(slope, constant);
        return eq;
    }

    private boolean isCurrentStartLieUnderPrevLine(Point_Erect_the_Fence prevPointStart,
                              Point_Erect_the_Fence currentStart, Point_Erect_the_Fence end){
        EquationOfLine_Erect_the_Fence eqFromPrevStartToPrevEnd = createEquationOfLine(prevPointStart, currentStart);
        EquationOfLine_Erect_the_Fence eqFromPrevStartToCurrentEnd = createEquationOfLine(prevPointStart, end);

        if(eqFromPrevStartToPrevEnd.slope<eqFromPrevStartToCurrentEnd.slope) {
            return true;
        }
        return false;
    }

    private boolean isCurrentStartLieAbovePrevLine(Point_Erect_the_Fence prevPointStart,
                                                   Point_Erect_the_Fence currentStart, Point_Erect_the_Fence end){
        EquationOfLine_Erect_the_Fence eqFromPrevStartToPrevEnd = createEquationOfLine(prevPointStart, currentStart);
        EquationOfLine_Erect_the_Fence eqFromPrevStartToCurrentEnd = createEquationOfLine(prevPointStart, end);

        if(eqFromPrevStartToPrevEnd.slope>eqFromPrevStartToCurrentEnd.slope) {
            return true;
        }
        return false;
    }

    int binarySearchJustGreaterThenX(List<Integer> list, int left, int x)
    {
        int right = list.size() - 1;
        int ansIndex = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid) <= x) {
                left = mid + 1;
            }
            else {
                ansIndex = mid;
                right = mid - 1;
            }
        }
        return ansIndex;
    }

    private void buildMap(int[][] trees,
                          Map<Integer, List<Integer>> x_CoordinateValueY_Coordinates, List<Integer> xCoordinates) {

        for (int i = 0; i<trees.length; i++) {
           if(x_CoordinateValueY_Coordinates.containsKey(trees[i][0])){
               List<Integer> prevList = x_CoordinateValueY_Coordinates.get(trees[i][0]);
               prevList.add(trees[i][1]);
           }
           else{
               List<Integer> newList = new ArrayList<>();
               newList.add(trees[i][1]);
               x_CoordinateValueY_Coordinates.put(trees[i][0], newList);
               xCoordinates.add(trees[i][0]);
           }
        }
        Collections.sort(xCoordinates);
        for (Map.Entry<Integer, List<Integer>> entry : x_CoordinateValueY_Coordinates.entrySet()) {
            Collections.sort(entry.getValue());
        }
    }

    private void print2dArray(int[][] arr) {
        for (int i = 0; i<arr.length; i++) {
            for (int j = 0; j<arr[i].length; j++){
                System.out.print(arr[i][j]);
                if(j<arr[i].length-1)
                    System.out.print(", ");
            }
            System.out.println();
        }
    }
}
