package Unsolved;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/*
https://leetcode.com/problems/zuma-game/

Example 1:

Input:
WRRBBW
RB
Input Meaning:
board = "WRRBBW", hand = "RB"
Output: -1
Explanation: It is impossible to clear all the balls. The best you can do is:
- Insert 'R' so the board becomes WRRRBBW. WRRRBBW -> WBBW.
- Insert 'B' so the board becomes WBBBW. WBBBW -> WW.
There are still balls remaining on the board, and you are out of balls to insert.
Example 2:

Input:
WWRRBBWW
WRBRW
Output: 2
Explanation: To make the board empty:
- Insert 'R' so the board becomes WWRRRBBWW. WWRRRBBWW -> WWBBWW.
- Insert 'B' so the board becomes WWBBBWW. WWBBBWW -> WWWW -> empty.
2 balls from your hand were needed to clear the board.
Example 3:

Input:
G
GGGGG
Output: 2
Explanation: To make the board empty:
- Insert 'G' so the board becomes GG.
- Insert 'G' so the board becomes GGG. GGG -> empty.
2 balls from your hand were needed to clear the board.
Example 4:

Input:
RBYYBBRRB
YRBGB
Output: 3
Explanation: To make the board empty:
- Insert 'Y' so the board becomes RBYYYBBRRB. RBYYYBBRRB -> RBBBRRB -> RRRB -> B.
- Insert 'B' so the board becomes BB.
- Insert 'B' so the board becomes BBB. BBB -> empty.
3 balls from your hand were needed to clear the board.

Input:
RRWWRRW
WR
Output: -1

Input:
WWGWGW
GWBWR
Output: 3

Input:
RWYWRRWRR
YRY
Output: 3
RWYWRRWRR --> 1 & 2    ----------->    RWY[YY]WRRWRR ->  RWWRRWRR
          --> 2        ----------->    RWWRR[R]WRR   ->  RWWWRR -->  RRR  ->  {}

Input:
RRWWRRBBRR
WB
Output: 2
	Explaination:
		   RRWWRRBBRR
		 1 RRWWRRBBRR ---> R[B]RWWRRBBRR
		 2 R[B]RWWRRBBRR  ----> R[B]RWW[W]RRBBRR  --->  R[B]RRRBBRR ---> R[B][RRR]BBRR -> R[B]BBRR ->  R[BBB]RR  -> RRR ->{}

Input:
RRYGGYYRRYYGGYRR
GGBBB
Output: 5
	Explaination:
		   RRYGGYYRRYYGGYRR
		 1 RRYGGYYRRYYGGYRR ---> R[B]RYGGYYRRYYGGYRR
		 2 R[B]RYGGYYRRYYGGYRR ->  R[B]RYGG[G]YYRRYYGGYRR -> R[B]RY[GGG]YYRRYYGGYRR  ->  R[B]RYYYRRYYGGYRR ->                   R[B]R[YYY]RRYYGGYRR ->  R[B]RRRYYGGYRR  -> R[B][RRR]YYGGYRR  -> R[GBYYGGYRR
		 3 R[B]YYGGYRR -> R[B]YYGG[G]YRR -> R[B]YY[GGG]YRR  ->  R[B]YYYRR -> R[B][YYY]RR  -> R[B]RR
		 4 R[B]RR - > R[B][B]RR
		 5 R[B][B]RR -> R[B][B][B]RR -> R[BBB]RR -> RRR -> [RRR] -> {}

Input:
WWBBWBBWW
BB
Output: -1
	Explaination:
		WWBBWBBWW
		 1 WWBBWBBWW ---> WWBB[B]WBBWW  ---> [WWW]BBWW --> BBWW
		 2 [BBB]WW  ----> WW

Input:
RRWWRRBBR
WB
Output: -1
 */


public class Zuma_Game {
    public static void main(String[] args) {
        Zuma_Game zuma_game = new Zuma_Game();
        Scanner input = new Scanner(System.in);
        int t = 123;
//        System.out.println("Enter Number Of Test Cases = ");
//        t = input.nextInt();
        while (t > 0) {
            int choice;
            System.out.println("Enter Your Choice, You Want to Test Your Own Input Or Want To Test With " +
                    "Example Input, Press 1 For Your Input Else Press Any Num Key = ");
            choice = input.nextInt();
            String board = "";
            String hand = "";
            if (choice == 1) {
                System.out.println("Enter Balls Colors On Board: ");
                input.nextLine();
                board = input.nextLine();
                System.out.println("Enter Balls Colors In Hand: ");
                hand = input.nextLine();
                int minBalls = zuma_game.findMinStep(board, hand);
                System.out.println("Minimum Number Of Balls Required To Clear Board Balls: "+minBalls);
            } else {
                board = "RBYYBBRRB";
                hand = "YRBGB";
                int minBalls = zuma_game.findMinStep(board, hand);
                System.out.println("Minimum Number Of Balls Required To Clear Board Balls: "+minBalls);
            }
            t--;
        }
    }

    int boardLength = 0;
    Map<Integer, Character> board_IndexWithColor;
    int ans = Integer.MAX_VALUE;
    int boardEmpty = 0;
    public int findMinStep(String board, String hand) {
        ans = Integer.MAX_VALUE;
        boardEmpty = 0;
        boardLength = board.length();
        Map<Character, Integer> handBalls = populateMapsForBallColorWithCount(hand);
        Map<Character, List<Integer>> boardBalls_wholeColorsWithIndexes  =
                populateMapsForBoardBallsWholeCount(board);
        if(isWinLookingPossible(handBalls, boardBalls_wholeColorsWithIndexes)){
            List<Integer> minCountForCount_1_bals = new ArrayList<>();
            minCountForCount_1_bals.add(0);
            this.board_IndexWithColor = convertStringToMap(
                    boardBalls_wholeColorsWithIndexes, minCountForCount_1_bals, handBalls);
            Map<Integer, Character> board_IndexWithColorCopy = new HashMap<>();
            board_IndexWithColorCopy.putAll(this.board_IndexWithColor);
            int minCount = minCountForCount_1_bals.get(0);

//            System.out.println("Whole  boardBalls_wholeColorsWithIndexes = "+boardBalls_wholeColorsWithIndexes);
//            System.out.println("board_IndexWithColor = "+board_IndexWithColorCopy);
//            System.out.println("minCount = "+minCount);
            findMinStepRecursion(board_IndexWithColorCopy, handBalls, minCount);
            if(boardEmpty == 1)
                return ans;
            else
                return -1;
        }
        return -1;
    }

    private int findMinStepRecursion(
            Map<Integer, Character> board_IndexWithColorCopy,
            Map<Character, Integer> handBalls, int minCount) {
        System.out.println("Recursion Start");
        System.out.println("board_IndexWithColorCopy = "+board_IndexWithColorCopy);
        System.out.println("handBalls = "+handBalls);
        if(!board_IndexWithColorCopy.isEmpty()){
            Map<Integer, List<Integer>> board_continuousCountWithStartIndex =
                    createMapForContinuousColorCountToStartIndex(board_IndexWithColorCopy);
            System.out.println("recursion board_continuousCountWithStartIndex = "+board_continuousCountWithStartIndex);
            List<Integer> startIndexesWithCount2 =
                    board_continuousCountWithStartIndex.getOrDefault(2, new ArrayList<>());
            List<Integer> startIndexesWithCount1 =
                    board_continuousCountWithStartIndex.getOrDefault(1, new ArrayList<>());
            for(Integer index: startIndexesWithCount2){
                System.out.println("2 recursion index = "+index+" startIndexesWithCount2 = "+startIndexesWithCount2);
                if(board_IndexWithColorCopy.containsKey(index)){
                    char color = board_IndexWithColorCopy.get(index);
                    int handBallCount = handBalls.getOrDefault(color, 0);
                    if(handBallCount>=1){
                        handBalls.put(color, handBallCount-1);
                        int a1 = index;;
                        int a2 = nextRightExisting(a1+1, board_IndexWithColorCopy);
                        List<Integer> indexToRemove = new ArrayList<>();
                        indexToRemove.add(a1); indexToRemove.add(a2);
                        System.out.println("2 recursion index found = "+index);
                        removeIndexesFromMap(indexToRemove,
                                board_IndexWithColorCopy);
                        removeRemainingContinuous(indexToRemove,
                                board_IndexWithColorCopy, a1, 2);
                        System.out.println("2 recursion board_IndexWithColorCopy = "+board_IndexWithColorCopy);
                        System.out.println("2 recursion board_IndexWithColor = "+board_IndexWithColor);
                        int x = minCount+1;
                        System.out.println("2 recursion minCount = "+x);
                        findMinStepRecursion(board_IndexWithColorCopy, handBalls, minCount+1);
                        handBalls.put(color, handBallCount);
                        addIndexesInMap(indexToRemove, board_IndexWithColorCopy);
                        System.out.println("22 recursion board_IndexWithColorCopy = "+board_IndexWithColorCopy);
                    }else{
                        continue;
                    }
                }
            }
            for (int index:startIndexesWithCount1){
                System.out.println("1 recursion index = "+index+" startIndexesWithCount1 = "+startIndexesWithCount1);
                if(board_IndexWithColorCopy.containsKey(index)){
                    char color = board_IndexWithColorCopy.get(index);
                    int handBallCount = handBalls.getOrDefault(color, 0);
                    if(handBallCount>=2){
                        handBalls.put(color, handBallCount-2);
                        int a1 = index;
                        List<Integer> indexToRemove = new ArrayList<>();
                        indexToRemove.add(a1);
                        System.out.println("1 recursion index found = "+index);
                        removeIndexesFromMap(indexToRemove,
                                board_IndexWithColorCopy);
                        removeRemainingContinuous(indexToRemove,
                                board_IndexWithColorCopy, a1, 1);
                        System.out.println("1 recursion board_IndexWithColorCopy = "+board_IndexWithColorCopy);
                        System.out.println("1 recursion board_IndexWithColor = "+board_IndexWithColor);
                        int x = minCount+2;
                        System.out.println("1 recursion minCount = "+x);
                        findMinStepRecursion(board_IndexWithColorCopy, handBalls, minCount+2);
                        addIndexesInMap(indexToRemove, board_IndexWithColorCopy);
                        handBalls.put(color, handBallCount);
                        System.out.println("11 recursion board_IndexWithColorCopy = "+board_IndexWithColorCopy);
                    }else{
                        continue;
                    }
                }
            }
        }
        else{
            System.out.println("board empty = "+minCount);
            boardEmpty = 1;
            ans = Math.min(ans, minCount);
        }
        return minCount;
    }

    private void addIndexesInMap(List<Integer> indexToRemove,
                                 Map<Integer, Character> board_IndexWithColorCopy) {
        for(int i = 0; i<indexToRemove.size(); i++){
            board_IndexWithColorCopy.put(indexToRemove.get(i),
                    board_IndexWithColor.get(indexToRemove.get(i)));
        }
    }

    private void removeIndexesFromMap(List<Integer> indexToRemove,
                                      Map<Integer, Character> board_IndexWithColorCopy) {
        for (int i = 0; i<indexToRemove.size(); i++){
            board_IndexWithColorCopy.remove(indexToRemove.get(i));
        }
    }

    private void removeRemainingContinuous(List<Integer>indexToRemove,
                                           Map<Integer, Character> board_IndexWithColorCopy,
                                           int startIndex, int count) {
        int leftIndex = startIndex - 1;
        int rightIndex = nextRightExisting(leftIndex+1, board_IndexWithColorCopy);
        System.out.println("nextExistingRightIndex = "+rightIndex);
        List<Integer> continuousColorJoined =
                getListOfIndexesFowWhichContinuousColorJoined(
                        leftIndex, rightIndex, board_IndexWithColorCopy);
        System.out.println("continuousColorJoined = "+continuousColorJoined);
        while(continuousColorJoined!=null){
            indexToRemove.addAll(continuousColorJoined);
            removeIndexesFromMap(continuousColorJoined, board_IndexWithColorCopy);

            leftIndex = continuousColorJoined.get(0) - 1;
            rightIndex = nextRightExisting(leftIndex+1, board_IndexWithColorCopy);
            System.out.println("nextExistingRightIndex = "+rightIndex);
            continuousColorJoined =
                    getListOfIndexesFowWhichContinuousColorJoined(
                            leftIndex, rightIndex, board_IndexWithColorCopy);

            System.out.println("continuousColorJoined = "+continuousColorJoined);
        }
    }

    private int nextRightExisting(int start,
                                  Map<Integer, Character> board_indexWithColorCopy) {
        for(int i = start; i<boardLength; i++){
            if(board_indexWithColorCopy.containsKey(i))
                return i;
        }
        return -1;
    }

    private List<Integer> getListOfIndexesFowWhichContinuousColorJoined(int leftIndex,
                                                                        int rightIndex, Map<Integer, Character> board_IndexWithColorCopy) {
        char leftColor = 'x';
        char rightColor = 'y';
        List<Integer> leftSideContinuous = new ArrayList<>();
        List<Integer> rightSideContinuous = new ArrayList<>();

        for(int i = leftIndex; i>=0; i--){
            if(board_IndexWithColorCopy.containsKey(i)){
                if(leftSideContinuous.size() >= 1){
                    if(leftColor!=board_IndexWithColorCopy.get(i))
                        break;
                }
                leftColor = board_IndexWithColorCopy.get(i);
                leftSideContinuous.add(0, i);
            }
        }
        System.out.println("leftColor = "+leftColor+"  leftSideContinuous = "+leftSideContinuous);
        for(int i = rightIndex; i<boardLength && i!=-1; i++){
            if(board_IndexWithColorCopy.containsKey(i)){
                if(rightSideContinuous.size() >= 1){
                    if(rightColor!=board_IndexWithColorCopy.get(i))
                        break;
                }
                rightColor = board_IndexWithColorCopy.get(i);
                rightSideContinuous.add(i);
            }
        }
        System.out.println("rightColor = "+rightColor+"  rightSideContinuous = "+rightSideContinuous);
        int flag = 0, flag2 = 0;
        List<Integer> continuousColorIndex = new ArrayList<>();
        if(leftColor==rightColor && leftSideContinuous.size()+rightSideContinuous.size()>=3){
            flag = 1;
            flag2 = 1;
            continuousColorIndex.addAll(leftSideContinuous);
            continuousColorIndex.addAll(rightSideContinuous);
        }
        if(flag == 0 && leftSideContinuous.size()>=3){
            flag2 = 1;
            continuousColorIndex.addAll(leftSideContinuous);
        }
        if(flag == 0 && rightSideContinuous.size()>=3){
            flag2 = 1;
            continuousColorIndex.addAll(rightSideContinuous);
        }
        if(flag2 == 1)
            return continuousColorIndex;
        return null;
    }

    private Map<Integer, List<Integer>> createMapForContinuousColorCountToStartIndex(
            Map<Integer, Character> board_IndexWithColorCopy) {
        Map<Integer, List<Integer>> board_continuousCountWithStartIndex =
                new HashMap<>();
        for(int i = 0; i<boardLength; ) {
            if(board_IndexWithColorCopy.containsKey(i)){
                int startIndex = i;
                int count = 1;
                char color = board_IndexWithColorCopy.get(i);
                i = i+1;
                while(i<boardLength){
                    if(board_IndexWithColorCopy.containsKey(i)){
                        if(color == board_IndexWithColorCopy.get(i)) {
                            count++;
                            i = i+1;
                        }
                        break;
                    }else{
                        i++;
                    }
                }
                List<Integer> startIndexes =  board_continuousCountWithStartIndex.getOrDefault(
                        count, new ArrayList<>());
                if(startIndexes.isEmpty()){
                    startIndexes.add(startIndex);
                    board_continuousCountWithStartIndex.put(count, startIndexes);
                }
                else {
                    startIndexes.add(startIndex);
                }
            }
            else {
                i++;
            }
        }
        return board_continuousCountWithStartIndex;
    }

    private Map<Integer, Character> convertStringToMap(
            Map<Character, List<Integer>> boardBalls_wholeColorsWithIndexes,
            List<Integer> minCountForCount_1_bals, Map<Character, Integer> handBalls) {
        Map<Integer, Character> board_IndexWithColor = new HashMap<>();
        for(Map.Entry<Character, List<Integer>> boardBalls: boardBalls_wholeColorsWithIndexes.entrySet()) {
            List<Integer> indexes = boardBalls.getValue();
            if(indexes.size()!=1){
                for (int i: indexes){
                    board_IndexWithColor.put(i, boardBalls.getKey());
                }
            }else {
                int handBallCount = handBalls.get(boardBalls.getKey());
                handBalls.put(boardBalls.getKey(), handBallCount-2);
                int steps = minCountForCount_1_bals.get(0) + 2;
                minCountForCount_1_bals.set(0, steps);
            }
        }
        return board_IndexWithColor;
    }

    private Map<Character, List<Integer>> populateMapsForBoardBallsWholeCount(String boardBalls) {
        Map<Character, List<Integer>> boardBalls_wholeColorsWithIndexes =
                new HashMap<>();
        for(int i = 0; i<boardBalls.length(); i++){
            List<Integer> indexes =  boardBalls_wholeColorsWithIndexes.getOrDefault(
                    boardBalls.charAt(i), new ArrayList<>());
            if(indexes.isEmpty()){
                indexes.add(i);
                boardBalls_wholeColorsWithIndexes.put(boardBalls.charAt(i), indexes);
            }else{
                indexes.add(i);
            }
        }
        return boardBalls_wholeColorsWithIndexes;
    }

    private Map<Character, Integer> populateMapsForBallColorWithCount(String balls) {
        Map<Character, Integer> ballsColorsWithCount = new HashMap<>();
        for(int i = 0; i<balls.length(); i++){
            Integer count =  ballsColorsWithCount.getOrDefault(balls.charAt(i), 0);
            ballsColorsWithCount.put(balls.charAt(i), count+1);
        }
        return ballsColorsWithCount;
    }

    private boolean isWinLookingPossible(Map<Character, Integer> handBalls,
                                         Map<Character, List<Integer>> boardBalls_wholeColorsWithIndexes) {
        for(Map.Entry<Character, List<Integer>> boardBalls: boardBalls_wholeColorsWithIndexes.entrySet()){
            List<Integer> indexes = boardBalls.getValue();
            if(indexes.size() == 1){
                int handCount = handBalls.getOrDefault(boardBalls.getKey(), 0);
                if(handCount<2)
                    return false;
            }
            else if(indexes.size() == 2){
                int handCount = handBalls.getOrDefault(boardBalls.getKey(), 0);
                if(handCount<1)
                    return false;
            }
        }
        return true;
    }
}
