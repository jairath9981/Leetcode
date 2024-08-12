package Solved;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
    Very difficult
    How To Think:
        1) Observation
            How To Observe:
                 We need to form chessboard by swapping some rows and columns. It Means Question is
                 formed by swapping some rows and columns of correct chessBoard. So start your
                 Observation by swapping rows and columns of correct chessBoard.
                 correct chessBoard:
                 n = 4
                 0 1 0 1                  1 0 1 0
                 1 0 1 0       OR         0 1 0 1
                 0 1 0 1                  1 0 1 0
                 1 0 1 0                  0 1 0 1

                 n = 5
                 0 1 0 1 0                  1 0 1 0 1
                 1 0 1 0 1     OR           0 1 0 1 0
                 0 1 0 1 0                  1 0 1 0 1
                 1 0 1 0 1                  0 1 0 1 0
                 0 1 0 1 0                  1 0 1 0 1
                First Observation after swapping:
                 a) corner element of every rectangle is:
                   0, 0, 0, 0
                   1, 1 ,1, 1
                   0, 1, 0, 1
                   it means xor of them equal to zero.

                 b) In Even Case Length 0 and Length of 1 = n/2 in ever row and column.
                    In Odd Case Length of 1 or 0 is +1 greater than other i.e. for one of the element
                    Length is (n+1)/2
*/


/*
https://leetcode.com/problems/transform-to-chessboard/

Input 1:
0 1 1 0 -999
0 1 1 0 -999
1 0 0 1 -999
1 0 0 1 -999
-999
Input Meaning: board = [[0,1,1,0],[0,1,1,0],[1,0,0,1],[1,0,0,1]]
Output: 2
Explanation: The first move swaps the 0 and 1 column. (index)
1 0 1 0
1 0 1 0
0 1 0 1
0 1 0 1
The second move swaps the 1 and 2 row. (index)
1 0 1 0
0 1 0 1
1 0 1 0
0 1 0 1

Input 2:
0 1 -999
1 0 -999
-999
Output: 0
Explanation: Also note that the board with 0 in the top left corner, is also a valid chessboard.

Input 3:
1 0 -999
1 0 -999
-999
Output: -1
Explanation: No matter what sequence of moves you make, you cannot end with a valid chessboard.

Input 4:
1 1 0 -999
0 0 1 -999
0 0 1 -999
-999
Output: 2
Explanation: Swap row 0 and 1 (index)
0 0 1
1 1 0
0 0 1
Now Swap column 1 & 2 (index)
0 1 0
1 0 1
0 1 0

Input 5:
1 0 0 -999
0 1 1 -999
1 0 0 -999
-999
Output: 1
Explanation: Swap col 0 and 1. (index)
0 1 0
1 0 1
0 1 0

Input 6:
1 1 0 -999
0 0 1 -999
0 0 1 -999
-999
Output: 2
Explanation: Swap row 0 and 1. (index)
0 0 1
1 1 0
0 0 1
Swap row 1 and 2. (index)
0 1 0
1 0 1
0 1 0
*/


public class Transform_To_Chessboard2 {
    public static void main(String[] args) {
        Transform_To_Chessboard2 ttc2 = new Transform_To_Chessboard2();
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
                List<List<Integer>> listOfBoardValues = new ArrayList<>();
                int x;
                System.out.println("Enter Chess Board Values[0, 1]. For Stop Insertion At Any Row Press -999");
                x = input.nextInt();
                while(x!=-999) {
                    List<Integer> rowOfBoard = new ArrayList<>();
                    while(x!=-999) {
                        rowOfBoard.add(x);
                        x = input.nextInt();
                    }
                    if(!rowOfBoard.isEmpty())
                        listOfBoardValues.add(rowOfBoard);
                    x = input.nextInt();
                }
                int[][] board = listOfBoardValues.stream().map( u -> u.stream().mapToInt(
                        i->i).toArray() ).toArray(int[][]::new);

                System.out.println();
                int minSwap = ttc2.movesToChessboard(board);
                System.out.println("Minimum Row/Column Swap Required: "+minSwap);
            }
            else
            {
                int [][]board = {{0,1,-1},{1,0,-1},{1,1,1}};
                System.out.println();
                int minSwap = ttc2.movesToChessboard(board);
                System.out.println("Minimum Row/Column Swap Required: "+minSwap);
            }
            t--;
        }
    }

    public int movesToChessboard(int[][] board) {
        if(!isCornerConditionSatisfy(board))
            return -1;

        int rowSwaps = 0, colSwaps = 0;
        int noOfOnesInFirstRow = 0, noOfOnesInFirstCol = 0;
        for (int i = 0; i<board.length; i++){
            noOfOnesInFirstRow+= board[0][i];
            noOfOnesInFirstCol+= board[i][0];
            /*
                number of cell possess wrong value
                Assuming first cell[0][0] always start with 1
                (And out of 4 Rectangle Corner Cells check 3 here.
                board[0][0], board[0][j] and board[i][0]
                one would be correct otherwise xor value is not equal to zero)
             */
            if(board[i][0] == i%2)
                rowSwaps++;
            if(board[0][i] == i%2)
                colSwaps++;
        }
        if(!isCountConditionSatisfy(board.length,
                noOfOnesInFirstRow, noOfOnesInFirstCol)){
            return -1;
        }
        if(board.length%2 == 0){ // Input 2
            rowSwaps = Math.min(rowSwaps, board.length - rowSwaps);
            colSwaps = Math.min(colSwaps, board.length - colSwaps);
        }
        else{ // Input 6
            if(rowSwaps%2 == 1)
                rowSwaps = board.length - rowSwaps;
            if(colSwaps%2 == 1)
                colSwaps = board.length - colSwaps;
        }
        // divide by 2: each swap will rectify 2 positions. as one need to be swapped by other.
        // So either 2 rows or 2 columns are swapped together.
        return (rowSwaps+colSwaps)/2;
    }

    private boolean isCountConditionSatisfy(int n,
              int noOfOnesInFirstRow, int noOfOnesInFirstCol) {

        if(noOfOnesInFirstRow < (n/2) || noOfOnesInFirstRow > ((n+1)/2))
            return false;

        if(noOfOnesInFirstCol < (n/2) || noOfOnesInFirstCol > ((n+1)/2))
            return false;

        return true;
    }

    private boolean isCornerConditionSatisfy(int[][] board) {
        for (int i = 0; i<board.length; i++){
            for (int j = 0; j<board[i].length; j++){
               /*
                    (Rectangle 4 Corner Cells)
                    upperLeftCorner, UpperRightCorner,
                    LowerLeftCorner, LowerRightCorner
                */
                if((board[0][0] ^ board[0][j] ^ board[i][0] ^ board[i][j])== 1)
                    return false;
            }
        }
        return true;
    }
}
