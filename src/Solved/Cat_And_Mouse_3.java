package Solved;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;


/*
https://leetcode.com/problems/cat-and-mouse/description/

Input1: graph meaning= [[2,5],[3],[0,4,5],[1,4,5],[2,3],[0,2,3]]

2 5 -999
3 -999
0 4 5 -999
1 4 5 -999
2 3 -999
0 2 3 -999
-9999
Output: 0



Input2:
1 3 -999
0 -999
3 -999
0 2 -999
-9999
Output: 1

Input3:
2  3 -999
3  4 -999
0  4 -999
0  1 -999
1  2 -999
-9999
Output: 1

Input4:
3  4  6  7  9  15  16  18 -999
4  5  8  19 -999
3  4  6  9  17  18 -999
0  2  11  15 -999
0  1  10  6  2  12  14  16 -999
1  10  7  9  15  17  18 -999
0  10  4  7  9  2  11  12  13  14  15  17  19 -999
0  10  5  6  9  16  17 -999
1  9  14  15  16  19 -999
0  10  5  6  7  8  2  11  13  15  16  17  18 -999
4  5  6  7  9  18 -999
3  6  9  12  19 -999
4  6  11  15  17  19 -999
6  9  15  17  18  19 -999
4  6  8  15  19 -999
0  3  5  6  8  9  12  13  14  16  19 -999
0  4  7  8  9  15  17  18  19 -999
5  6  7  9  2  12  13  16 -999
0  10  5  9  2  13  16 -999
1  6  8  11  12  13  14  15  16 -999
-9999
Output: 1


Input5:
5  7  9 -999
3  4  5  6 -999
3  4  5  8 -999
1  2  6  7 -999
1  2  5  7  9 -999
0  1  2  4  8 -999
1  3  7  8 -999
0  3  4  6  8 -999
2  5  6  7  9 -999
0  4  8 -999
-9999
Output: 1
*/


enum Cat_And_Mouse_PayerEnum {
    MOUSE,
    CAT
}

enum Cat_And_Mouse_WinnerEnum {
    MOUSE,
    CAT,
    DRAW
}

class Cat_And_Mouse_PlayersPositionInfo {
    int mousePosition;
    int catPosition;
    Cat_And_Mouse_PayerEnum whoNeedsToMoveNow; // or whoNeedsToMoveNext
    Cat_And_Mouse_WinnerEnum winner;

    public Cat_And_Mouse_PlayersPositionInfo(int mousePosition, int catPosition,
                    Cat_And_Mouse_PayerEnum whoNeedsToMoveNow, Cat_And_Mouse_WinnerEnum winner) {
        this.mousePosition = mousePosition;
        this.catPosition = catPosition;
        this.whoNeedsToMoveNow = whoNeedsToMoveNow;
        this.winner = winner;
    }

    public Cat_And_Mouse_PlayersPositionInfo(int mousePosition, int catPosition,
                                             Cat_And_Mouse_PayerEnum whoNeedsToMoveNow){
        this.mousePosition = mousePosition;
        this.catPosition = catPosition;
        this.whoNeedsToMoveNow = whoNeedsToMoveNow;
    }

    public Cat_And_Mouse_PlayersPositionInfo(){

    }

    @Override
    public String toString() {
        return "PlayersPositionInfo{" +
                "mousePosition=" + mousePosition +
                ", catPosition=" + catPosition +
                ", whoNeedsToMoveNow=" + whoNeedsToMoveNow +
                ", winner=" + winner +
                '}';
    }
}


class Cat_And_Mouse_PlayerMoves {
    Cat_And_Mouse_WinnerEnum whoWins;
    int currentPossibleMoves;

    public Cat_And_Mouse_PlayerMoves(Cat_And_Mouse_WinnerEnum whoWins, int currentPossibleMoves) {
        this.whoWins = whoWins;
        this.currentPossibleMoves = currentPossibleMoves;
    }
    public Cat_And_Mouse_PlayerMoves(){

    }

    @Override
    public String toString() {
        return "PlayerMoves{" +
                "whoWins=" + whoWins +
                ", currentPossibleMoves=" + currentPossibleMoves +
                '}';
    }
}

public class Cat_And_Mouse_3 {

    public static void main(String[] args) {
        Cat_And_Mouse_3 catAndMouse_3 = new Cat_And_Mouse_3();
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
            if (choice == 1) {
                List<List<Integer>> graphEdges = new ArrayList<>();
                int x, y;
                System.out.println("Enter Graph Edges Vertex Wise. For Stop Insertion Of Edge From Any Vertex Press -999 " +
                        "And When You Cover All Edges From All Vertex Press -9999");
                x = input.nextInt();
                while(x!=-9999) {
                    List<Integer> edgesFromCurrVertex = new ArrayList<>();
                    while(x!=-999 && x!=-9999){
                        edgesFromCurrVertex.add(x);
                        x = input.nextInt();
                    }
                    graphEdges.add(edgesFromCurrVertex);
                    if(x==-9999)
                        break;
                    x = input.nextInt();
                }
                int[][] graph = graphEdges.stream()
                        .map(e->e.stream().mapToInt(Integer::intValue).toArray())
                        .toArray(int[][]::new);
                System.out.println();
                int player = catAndMouse_3.catMouseGame(graph);
                System.out.println("Player Who Win: "+player);
            } else {
                //43
                int[][] graph = {{2, 5}, {3}, {0, 4, 5}, {1, 4, 5}, {2, 3}, {0, 2, 3}};
                System.out.println();
                int player = catAndMouse_3.catMouseGame(graph);
                System.out.println("Player Who Win: "+player);
            }
            t--;
        }
    }


    public int catMouseGame(int[][] graph) {

        int winner = predictWinnerFromResolvedStateAlgo3(graph);
        return winner;
    }


    public int predictWinnerFromResolvedStateAlgo3(int[][] graph){
        /*
             3rd dimension:
                0 index is telling mouse needs to take next move, as just now or currently cat take a move.
                1 index is telling cat needs to take next move, as just now or currently mouse take a move.
                Or in other words:
                    0: currently cat take a move
                    1: currently mouse take a move
                Or in other words:
                    0: next turn is of mouse
                    1: next turn is of cat
         */
        Cat_And_Mouse_PlayerMoves[][][]dp = new Cat_And_Mouse_PlayerMoves[graph.length][graph.length][2];
        Queue<Cat_And_Mouse_PlayersPositionInfo> resolvedStateQu = new LinkedList<>();

        initialize(dp, resolvedStateQu, graph);
        Cat_And_Mouse_WinnerEnum winner = predictWinner(resolvedStateQu, dp, graph);

        if(winner.equals(Cat_And_Mouse_WinnerEnum.MOUSE)) {
            System.out.println("Mouse win");
            return 1;
        }
        if(winner.equals(Cat_And_Mouse_WinnerEnum.CAT)) {
            System.out.println("Cat win");
            return 2;
        }

        System.out.println("Draw");
        return 0;

    }

    private Cat_And_Mouse_WinnerEnum predictWinner(Queue<Cat_And_Mouse_PlayersPositionInfo> resolvedStateQu,
                                             Cat_And_Mouse_PlayerMoves[][][] dp, int[][] graph) {

        /*
            1. we will predictWinner by BFS.
            2, We will try to predict previous state from resolvedState.
                And check do we knew the result of the previous state with the help of dp.
         */

        while(!resolvedStateQu.isEmpty()){

            Cat_And_Mouse_PlayersPositionInfo resolvedState = resolvedStateQu.remove();
//            System.out.println("resolvedState: "+resolvedState);
            /*
                adjacentNodes so that we can know the previous state.
                Previous state to reach to this resolved state.
                    So, that we can know which player moved in previous move, to reach to this resolved state.
                        if now CAT needs to take a move now it means previous moves was took by mouse
             */
            int[] adjacentNodes = resolvedState.whoNeedsToMoveNow == Cat_And_Mouse_PayerEnum.CAT
                    ? graph[resolvedState.mousePosition]: graph[resolvedState.catPosition];
            for(int movedFrom: adjacentNodes){

                Cat_And_Mouse_PlayersPositionInfo previousState =
                    resolvedState.whoNeedsToMoveNow == Cat_And_Mouse_PayerEnum.CAT ?
                    new Cat_And_Mouse_PlayersPositionInfo(movedFrom, resolvedState.catPosition,
                            Cat_And_Mouse_PayerEnum.MOUSE):
                    new Cat_And_Mouse_PlayersPositionInfo(resolvedState.mousePosition, movedFrom,
                            Cat_And_Mouse_PayerEnum.CAT);

                /*
                    can we resolve this previousState. i.e, Can we predict winner for this previousState.
                    Cases where we can depict previous state winner:
                    Caution:
                        1. In previous position cat should not be at 0 position.
                        2 impossible turns: when in previous state mouse was on node 0 and still mouse moved to get resolved state.
                                This is impossible if mouse was on 0 node why he will move now.
                                this dp[0][cat][0]  <---- in initialization also we did not put on any value fir this
                        3. Should not be already resolved state.
                    Result:
                        1. previous player moved current resolved state winner.
                        2. last possible move

                 */
                int turn = previousState.whoNeedsToMoveNow.equals(Cat_And_Mouse_PayerEnum.MOUSE) ? 0 : 1 ;
//                System.out.println("previousState: "+previousState +"  turn: "+turn +
//                        "   dp: "+dp[previousState.mousePosition][previousState.catPosition][turn]);
                if(previousState.catPosition != 0 /* cat is not allowed to move to 0 position */ &&
                        dp[previousState.mousePosition][previousState.catPosition][turn] != null /*impossible turns  */ &&
                        dp[previousState.mousePosition][previousState.catPosition][turn].whoWins == null /* not already resolved */
                        && (previousState.whoNeedsToMoveNow.name().equals(resolvedState.winner.name()) /* previous player took a move and win in the resolved state*/
                        || (--dp[previousState.mousePosition][previousState.catPosition][turn].currentPossibleMoves == 0 /*last possible moves expired */)
                )){
                    previousState.winner = resolvedState.winner;
//                    System.out.println("Previous State Winner: "+previousState);
                    if(previousState.mousePosition == 1 && previousState.catPosition == 2 && turn == 0){
                        return previousState.winner;
                    }
                    resolvedStateQu.add(previousState);
                    dp[previousState.mousePosition][previousState.catPosition][turn].whoWins = previousState.winner;
                }
//                else{
//                    System.out.println("Ignore this previous state: "+previousState);
//                }
            }
        }
        /*
            resolved state are over(Empty). Now player will re move on nodes[Back-forth].
         */
        return Cat_And_Mouse_WinnerEnum.DRAW;
    }


    private void initialize(Cat_And_Mouse_PlayerMoves[][][]dp,
                            Queue<Cat_And_Mouse_PlayersPositionInfo> resolvedStateQu, int[][] graph){

        /*
            We start our cat loop from index 1.
            And if Mouse reaches hole node no need to move further i.e, currentPossibleMoves = 0
            Mouse will reach in hole node in his turn of move and mourn in indicated with turn index 1
            because 0 indicates current cat move on the other hand 1 indicates current mouse turn
         */
        for(int cat = 1; cat<graph.length; cat++){
            dp[0][cat][1] = new Cat_And_Mouse_PlayerMoves(Cat_And_Mouse_WinnerEnum.MOUSE, 0);
            resolvedStateQu.add(new Cat_And_Mouse_PlayersPositionInfo(0, cat,
                    Cat_And_Mouse_PayerEnum.CAT,  Cat_And_Mouse_WinnerEnum.MOUSE));
        }

        for(int mouse = 1; mouse<graph.length; mouse++){
            for(int cat = 1; cat<graph.length; cat++){
                for(int turn = 0; turn<2; turn++){

                    Cat_And_Mouse_PlayerMoves playerMoves = new Cat_And_Mouse_PlayerMoves();
                    // cat just take a move we need to know is there any moves left from mouse
                    if(turn == 0){
                        playerMoves.currentPossibleMoves = graph[mouse].length;
                    }else{
                        playerMoves.currentPossibleMoves = graph[cat].length
                                - (isHoleNodeAnAdjacentNode(graph[cat])?1:0);
                    }

                    if(mouse == cat){
                        playerMoves.whoWins = Cat_And_Mouse_WinnerEnum.CAT;

                        Cat_And_Mouse_PayerEnum whoNeedsToMoveNext = turn == 0
                                ? Cat_And_Mouse_PayerEnum.MOUSE: Cat_And_Mouse_PayerEnum.CAT;
                        resolvedStateQu.add(new Cat_And_Mouse_PlayersPositionInfo(mouse,
                                cat, whoNeedsToMoveNext, Cat_And_Mouse_WinnerEnum.CAT));
                    }else{
                        playerMoves.whoWins = null;
                    }
                    dp[mouse][cat][turn] = playerMoves;
                }
            }
        }
    }

    private boolean isHoleNodeAnAdjacentNode(int[] arr) {

        for (int i: arr) {
            if (i == 0) {
                return true;
            }
        }
        return false;
    }
}
