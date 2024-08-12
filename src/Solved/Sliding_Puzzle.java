package Solved;

/*
https://leetcode.com/problems/sliding-puzzle/

Input 1:
1 2 3
4 0 5
Input 1 Meaning: board = [[1,2,3],[4,0,5]]
Output: 1
Explanation: Swap the 0 and the 5 in one move.

Input 2:
1 2 3
5 4 0
Output: -1
Explanation: No number of moves will make the board solved.

Input 3:
4 1 2
5 0 3
Output: 5
Explanation: 5 is the smallest number of moves that solves the board.
An example path:
After move 0: [[4,1,2],[5,0,3]]
After move 1: [[4,1,2],[0,5,3]]
After move 2: [[0,1,2],[4,5,3]]
After move 3: [[1,0,2],[4,5,3]]
After move 4: [[1,2,0],[4,5,3]]
After move 5: [[1,2,3],[4,5,0]]
*/

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

class Pair_Sliding_Puzzle{
    int i;
    int j;
    public Pair_Sliding_Puzzle(int i, int j){
        this.i = i;
        this.j = j;
    }
}

class Queue_Sliding_Puzzle{

    String strOfBoard;
    int indexOfZero;
    int count;

    public Queue_Sliding_Puzzle(String strOfBoard,
          int indexOfZero, int count) {
        this.strOfBoard = strOfBoard;
        this.indexOfZero = indexOfZero;
        this.count = count;
    }
}

public class Sliding_Puzzle {
    public static void main(String[] args) {
        Sliding_Puzzle sd = new Sliding_Puzzle();
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
                List<List<Integer>> listOfBoard = new ArrayList<>();
                int x;
                System.out.println("Enter Board Values[0, 5]. Only 3 Values Can Be Inserted Into Row");
                int row = 0;
                while(row<2) {
                    x = input.nextInt();
                    List<Integer> rowOfBoard = new ArrayList<>();
                    int col = 0;
                    while(x>=0 && x<=5 && col<3) {
                        rowOfBoard.add(x);
                        col++;
                        if(col<3)
                            x = input.nextInt();
                    }
                    if(!rowOfBoard.isEmpty())
                        listOfBoard.add(rowOfBoard);
                    row++;
                }
                int[][] board = listOfBoard.stream().map( u -> u.stream().mapToInt(
                        i->i).toArray() ).toArray(int[][]::new);

                System.out.println();
                int minSwapCount = sd.slidingPuzzle(board);
                System.out.println("Minimum Swap Required: "+minSwapCount);
            }
            else
            {
                int [][]board = {{1,2,3},{4,0,5}};
                System.out.println();
                int minSwapCount = sd.slidingPuzzle(board);
                System.out.println("Minimum Swap Required: "+minSwapCount);
            }
            t--;
        }
    }

    int rowLength = -1;
    int colLength = -1;
    public StringBuilder winingString;

    public int slidingPuzzle(int[][] board) {

        rowLength = board.length;
        colLength = board[0].length;
        winingString = new StringBuilder();
        formWiningString();

        int countSwap = -1;
        String strOfBoard = createStringForBoard(board);
        
        Queue<Queue_Sliding_Puzzle> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        initializeQueueAndSet(strOfBoard, queue, visited);

        while(!queue.isEmpty()){
            Queue_Sliding_Puzzle queueObj = queue.poll();
            if(queueObj.strOfBoard.equals(winingString.toString())) {
                countSwap = queueObj.count;
                break;
            }
            addInQueueAndSet(queueObj, queue, visited);
        }
        return countSwap;
    }

    private void addInQueueAndSet(Queue_Sliding_Puzzle prev,
            Queue<Queue_Sliding_Puzzle> queue, Set<String> visited) {

        Pair_Sliding_Puzzle boarIndexOfZero =
                getCorrectIndexOfMatrixFromStringIndex(prev.indexOfZero);
        //right
        Pair_Sliding_Puzzle rightNeighbour = new Pair_Sliding_Puzzle(
                boarIndexOfZero.i, boarIndexOfZero.j+1);
        addInQueueAndSetHelper(rightNeighbour, prev, queue, visited);
        //left
        Pair_Sliding_Puzzle leftNeighbour = new Pair_Sliding_Puzzle(
                boarIndexOfZero.i, boarIndexOfZero.j-1);
        addInQueueAndSetHelper(leftNeighbour, prev, queue, visited);
        //top
        Pair_Sliding_Puzzle topNeighbour = new Pair_Sliding_Puzzle(
                boarIndexOfZero.i-1, boarIndexOfZero.j);
        addInQueueAndSetHelper(topNeighbour, prev, queue, visited);
        //bottom
        Pair_Sliding_Puzzle bottomNeighbour = new Pair_Sliding_Puzzle(
                boarIndexOfZero.i+1, boarIndexOfZero.j);
        addInQueueAndSetHelper(bottomNeighbour, prev, queue, visited);
    }

    private void addInQueueAndSetHelper(
            Pair_Sliding_Puzzle neighbourIndexInBoard, Queue_Sliding_Puzzle prev,
            Queue<Queue_Sliding_Puzzle> queue, Set<String> visited) {

        if(isValid(neighbourIndexInBoard.i, neighbourIndexInBoard.j)){
            int strIndexOfNeighbour = getStringIndexFromBoardRowAndCol(
                    neighbourIndexInBoard.i, neighbourIndexInBoard.j);
            StringBuilder strBuilderOfBoardAfterSwap = new StringBuilder(prev.strOfBoard);

            //swap
            char neighbourCharOfZero = strBuilderOfBoardAfterSwap.charAt(strIndexOfNeighbour);
            strBuilderOfBoardAfterSwap.setCharAt(strIndexOfNeighbour, '0');
            strBuilderOfBoardAfterSwap.setCharAt(prev.indexOfZero, neighbourCharOfZero);

            String strOfBoardAfterSwap = strBuilderOfBoardAfterSwap.toString();
            if(!visited.contains(strOfBoardAfterSwap)){
                Queue_Sliding_Puzzle queueObj = new Queue_Sliding_Puzzle(
                        strOfBoardAfterSwap, strIndexOfNeighbour, prev.count+1);
                queue.add(queueObj);

                visited.add(strOfBoardAfterSwap);
            }
        }
    }

    private void initializeQueueAndSet(String strOfBoard,
            Queue<Queue_Sliding_Puzzle> queue, Set<String> visited) {

        int index = strOfBoard.indexOf("0");

        Queue_Sliding_Puzzle queueObj = new Queue_Sliding_Puzzle(strOfBoard, index, 0);

        queue.add(queueObj);
        visited.add(strOfBoard);
    }

    private String createStringForBoard(int[][] board) {
        StringBuilder str = new StringBuilder();
        for(int i = 0; i<board.length; i++)
            for (int j = 0; j<board[i].length; j++)
                str.append(board[i][j]);
        return str.toString();
    }


    private void formWiningString() {
        int mul = rowLength*colLength-1;
        for(int i = 1; i<=mul; i++)
            winingString.append(i);

        winingString.append('0');
    }

    private boolean isValid(int i, int j){
        if(i<0 || i>=rowLength)
            return false;
        if(j<0 || j>=colLength)
            return false;
        return true;
    }

    private int getStringIndexFromBoardRowAndCol(int row, int col){
        return row*colLength+col;
    }

    private Pair_Sliding_Puzzle getCorrectIndexOfMatrixFromStringIndex(int strIndex){
        int row = strIndex/colLength;
        int col = strIndex%colLength;

        return new Pair_Sliding_Puzzle(row, col);
    }
}
