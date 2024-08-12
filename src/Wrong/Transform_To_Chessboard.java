package Wrong;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
        Very, Very Difficult Question. Based on lot of analyzation.
        For Input 5, do not work. Re-Design SeePart2.
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
*/


public class Transform_To_Chessboard {
    public static void main(String[] args) {
        Transform_To_Chessboard ttc = new Transform_To_Chessboard();
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
                int minSwap = ttc.movesToChessboard(board);
                System.out.println("Minimum Row/Column Swap Required: "+minSwap);
            }
            else
            {
                int [][]board = {{0,1,-1},{1,0,-1},{1,1,1}};
                System.out.println();
                int minSwap = ttc.movesToChessboard(board);
                System.out.println("Minimum Row/Column Swap Required: "+minSwap);
            }
            t--;
        }
    }

    public int movesToChessboard(int[][] board1) {

        int[][] board2 = new int[board1.length][board1.length];

        for(int i = 0; i<board1.length; i++)
            for (int j = 0; j<board1.length; j++)
                board2[i][j] = board1[i][j];

        List<List<String>> rowString1 = buildRowStringsForBoard(board1);
        List<List<String>> colString1 = buildColStringsForBoard(board1);

        int count1 = transformToChessBoard(board1, rowString1, colString1,
                false);

        List<List<String>> rowString2 = buildRowStringsForBoard(board2);
        List<List<String>> colString2 = buildColStringsForBoard(board2);
        int count2 = transformToChessBoard(board2, rowString2, colString2,
                true);

        if(count1 == -1)
            return count2;
        else if(count2 == -1)
            return count1;
        return Math.min(count1, count2);
    }

    private int transformToChessBoard(int[][] board,
        List<List<String>> rowString, List<List<String>> colString,
        boolean changeFirstCell) {

        int count = 0;
        for(int i = 0; i<board.length; i++){
            for(int j = 0; j<board.length; j++){
                if((i==0  && j==0 && changeFirstCell) || isSwapReq(i, j, board)){
                    int swapHappen = swapMechanism(i, j, board,
                            rowString, colString, changeFirstCell);
                    if(swapHappen == -1)
                        return -1;
                    else
                        count+=1;
                }
            }
        }
        return count;
    }

    private int swapMechanism(int i, int j, int[][] board,
         List<List<String>> listOfRowString, List<List<String>> listOfColString,
         boolean changeFirstCell) {
        String row = "", col = "";
        if(j - 1>=0)
            row = listOfRowString.get(i).get(j - 1);
        if(i-1>=0)
            col = listOfColString.get(i-1).get(j);
        row+= getOppositeValue(board[i][j]);
        col+= getOppositeValue(board[i][j]);

        int[]columnSwapperInfo = new int[2];
        // columnNumber            // how much character match
        columnSwapperInfo[0] = -1; columnSwapperInfo[1] = -1;
        matchColumn(col, listOfColString, j, columnSwapperInfo);
        int[]rowSwapperInfo = new int[2];
        // rowNumber            // how much character match
        rowSwapperInfo[0] = -1; rowSwapperInfo[1] = -1;
        matchRow(row, listOfRowString, i, rowSwapperInfo);
        if((i == 0 && j== 0 && changeFirstCell && rowSwapperInfo[0]!=-1)
            || isNeedToSwapWithRow(rowSwapperInfo, columnSwapperInfo)){
            swapRow(listOfRowString, listOfColString, board, rowSwapperInfo, i);
            return 1;
        }else if(isNeedToSwapWithCol(columnSwapperInfo, rowSwapperInfo)){
            swapCol(listOfColString, listOfRowString, board, columnSwapperInfo, j);
            return 1;
        }else{
            return -1;
        }
    }

    private void swapCol(List<List<String>> listOfColString, List<List<String>> listOfRowString,
                         int[][] board, int[] columnSwapperInfo, int curr_j) {
        int colNum = columnSwapperInfo[0];
        for(int i = 0; i<listOfColString.size(); i++){
            // swap in list
            String a = listOfColString.get(i).get(curr_j);
            String b = listOfColString.get(i).get(colNum);

            listOfColString.get(i).set(curr_j, b);
            listOfColString.get(i).set(colNum, a);

            // swap in board
            int c = board[i][curr_j];
            int d = board[i][colNum];

            board[i][curr_j] = d;
            board[i][colNum] = c;
        }
        List<List<String>> temp = buildRowStringsForBoard(board);
        for(int i =0; i<listOfColString.size(); i++){
            listOfRowString.set(i, temp.get(i));
        }
    }

    private void swapRow(List<List<String>> listOfRowString, List<List<String>> listOfColString,
                         int[][] board, int[] rowSwapperInfo, int curr_i) {
        int rowNum =rowSwapperInfo[0];
        swapRowRow(listOfRowString, curr_i, rowNum);
        swapRowInBoard(board, curr_i, rowNum);
        List<List<String>> temp = buildColStringsForBoard(board);
        for(int i =0; i<listOfColString.size(); i++){
            listOfColString.set(i, temp.get(i));
        }
    }

    private void swapRowInBoard(int[][] board, int curr_i, int rowNum) {
        for(int j = 0; j<board.length; j++){
            int a = board[curr_i][j];
            int b = board[rowNum][j];

            board[curr_i][j] = b;
            board[rowNum][j] = a;
        }
    }

    private void swapRowRow(List<List<String>> listOfRowString,
                    int prevRowIndex, int needToSwapWithRowIndex) {
        List<String> a = listOfRowString.get(prevRowIndex);
        List<String> b = listOfRowString.get(needToSwapWithRowIndex);

        listOfRowString.set(prevRowIndex, b);
        listOfRowString.set(needToSwapWithRowIndex, a);
    }

    private boolean isNeedToSwapWithCol(int[] columnSwapperInfo, int[] rowSwapperInfo) {
        if(columnSwapperInfo[1]==-1)
            return false;
        else if(rowSwapperInfo[1]==-1)
            return true;
        else if(rowSwapperInfo[1]<columnSwapperInfo[1])
            return true;
        return false;
    }

    private boolean isNeedToSwapWithRow(int[] rowSwapperInfo, int[] columnSwapperInfo) {
        if(rowSwapperInfo[1]==-1)
            return false;
        else if(columnSwapperInfo[1]==-1)
            return true;
        else if(rowSwapperInfo[1]>=columnSwapperInfo[1])
            return true;
        return false;
    }

    private void matchRow(String row, List<List<String>> listOfRowString,
                          int currRow, int[] rowSwapperInfo) {
        for(int i = listOfRowString.size(); i>=row.length(); i--){
            int col =  i - 1;
            int flagMatch = 0;
            for(int k = currRow + 1; k<listOfRowString.size(); k++){
                String stringInList = listOfRowString.get(k).get(col);
                if(matchSpecialString(stringInList, row)){
                    flagMatch = 1;
                    storeInArray(rowSwapperInfo, currRow, k, i);
                }
            }
            if(flagMatch == 1){
                break;
            }
        }
    }

    private void matchColumn(String col, List<List<String>> listOfColString,
                             int currCol, int[] columnSwapperInfo) {

        for(int i = listOfColString.size(); i>=col.length(); i--){
            int row =  i - 1;
            int flagMatch = 0;
            for(int j = currCol+1; j<listOfColString.size(); j++){
                String stringInList = listOfColString.get(row).get(j);
                if(matchSpecialString(stringInList, col)){
                    flagMatch = 1;
                    storeInArray(columnSwapperInfo, currCol, j, i);
                }
            }
            if(flagMatch == 1){
                break;
            }
        }
    }

    private void storeInArray(int[] sapperInfoArr, int currVal, int swapWith,
                              int numOfChar) {
        if(sapperInfoArr[0]==-1){
            sapperInfoArr[0] = swapWith;
            sapperInfoArr[1] = numOfChar;
        }
        else if((currVal%2 == 1 && swapWith%2 == 0)
                || (currVal%2 == 0 && swapWith%2 == 1)){
            sapperInfoArr[0] = swapWith;
            sapperInfoArr[1] = numOfChar;
        }
    }

    private boolean matchSpecialString(String stringInList, String stringNeedToFind) {
        for(int i = stringInList.length()-1; i>=0; i--){
            char c = ' ';
            if(i>=stringNeedToFind.length()){
                c = getCharacter(stringNeedToFind, i);
            }else{
                c = stringNeedToFind.charAt(i);
            }
            if(c!=stringInList.charAt(i))
                return false;
        }
        return true;
    }

    private char getCharacter(String stringNeedToFind, int destIndex) {
        int maxIndexPresent = stringNeedToFind.length() - 1;
        int diff = destIndex - maxIndexPresent;

        int currLastNum = stringNeedToFind.charAt(maxIndexPresent)-'0';
        if(diff%2 == 0){
            return (char)(currLastNum + '0');
        }else{
            return (char)(getOppositeValue(currLastNum) + '0');
        }
    }

    private int getOppositeValue(int num) {
        if(num == 0)
            return 1;
        if(num == 1)
            return 0;
        return -1;
    }

    private boolean isSwapReq(int curr_i, int curr_j, int[][] board) {
        // left NeighBor
        if(isValidCell(curr_i, curr_j - 1, board)
                && board[curr_i][curr_j] == board[curr_i][curr_j - 1]){
            return true;
        }
        // top NeighBor
        if(isValidCell(curr_i - 1, curr_j, board)
                && board[curr_i][curr_j] == board[curr_i - 1][curr_j]){
            return true;
        }
        return false;
    }

    private boolean isValidCell(int i, int j, int[][] board) {
        if(i<0 || i>=board.length)
            return false;
        if(j<0 || j>=board[0].length)
            return false;
        return true;
    }

    private List<List<String>> buildRowStringsForBoard(int[][] board) {
        List<List<String>> listOfBoardString = new ArrayList<>();

        for (int i = 0; i<board.length; i++){
            String rowI = "";
            List<String> listOfRow = new ArrayList<>();
            for (int j = 0; j<board[i].length; j++){
                rowI+= board[i][j];
                listOfRow.add(rowI);
            }
            listOfBoardString.add(listOfRow);
        }
        return listOfBoardString;
    }

    private List<List<String>> buildColStringsForBoard(int[][] board) {
        List<List<String>> listOfBoardString = new ArrayList<>();

        for (int i = 0; i<board.length; i++){
            String colJ = "";
            List<String> listOfCol = new ArrayList<>();
            for (int j = 0; j<board[i].length; j++){
                if(i>0)
                    colJ = listOfBoardString.get(i-1).get(j) + board[i][j];
                else
                    colJ = String.valueOf(board[i][j]);
                listOfCol.add(colJ);
            }
            listOfBoardString.add(listOfCol);
        }

        return listOfBoardString;
    }
}
