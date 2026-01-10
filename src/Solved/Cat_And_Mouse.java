package Solved;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


//  WRONG ANSWERS
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


//  WRONG ANSWERS


public class Cat_And_Mouse {


    public static void main(String[] args) {
        Cat_And_Mouse catAndMouse = new Cat_And_Mouse();
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
                int player = catAndMouse.catMouseGame(graph);
                System.out.println("Player Who Win: "+player);
            } else {
                //43
                int[][] graph = {{2, 5}, {3}, {0, 4, 5}, {1, 4, 5}, {2, 3}, {0, 2, 3}};
                System.out.println();
                int player = catAndMouse.catMouseGame(graph);
                System.out.println("Player Who Win: "+player);
            }
            t--;
        }
    }


    public int catMouseGame(int[][] graph) {

//        int winner1 = catMouseGameResultWithRecursion(graph,2, 1, 0);
//
//        System.out.println("winner by Algo1: "+winner1);

        int possibleMoves =  2 * graph.length + 2;
        int [][][]dp = new int[graph.length][graph.length][possibleMoves];
       // System.out.println("Initially: "+graph.length+"::::"+graph.length+":::"+possibleMoves);
        for(int mousePos = 0; mousePos < graph.length; mousePos++){
            for(int catPos = 0; catPos < graph.length; catPos++){
                for (int move = 0; move < possibleMoves; move++){
                    //System.out.println(mousePos +":::"+catPos+":::"+move);
                    dp[mousePos][catPos][move] = -1;
                }
            }
        }
        int winner2 = catMouseGameResultWithRecursionDp(graph, 2, 1, 0, dp);
        System.out.println("winner by Algo2: "+winner2);



        return winner2;
    }



    /*
        This is braking with "Time Limit Exceeded"
        And Failing for Input5
     */
    private int catMouseGameResultWithRecursion(int[][] graph, int currCatPosition, int currMousePosition, int move) {

        if(move > 2 * graph.length){ // draw i.e, every player visited avery node once
            System.out.println("Draw:::"+currMousePosition + "::::"+currCatPosition+":::"+move);
            return 0;
        }
        if(currMousePosition == 0){ // mouse win
            System.out.println("Mouse win:::"+currMousePosition + "::::"+currCatPosition+":::"+move);
            return 1;
        }
        if(currCatPosition == currMousePosition){ // cat win
            System.out.println("Cat win:::"+currMousePosition + "::::"+currCatPosition+":::"+move);
            return 2;
        }

        int ans;
        boolean draw = false;

        // Mouse turn
        if(move % 2 == 0){
            int[] connectedNodesArr = graph[currMousePosition];
            for(int connectedNode: connectedNodesArr){
                ans = catMouseGameResultWithRecursion(graph, currCatPosition, connectedNode, move + 1);

                /*
                    Mouse Move:
                    Priority 1 of Mouse: Win: Return 1 [Clear from Input3]
                        This is mouse move we mouse will prioritize his win.
                            As, mouse see his win he will immediately claim win.

                    Priority 2 of Mouse: Draw: Return 0

                    Priority 3 of Mouse: Cat Win: Return 2
                 */
                if(ans == 1){
                    System.out.println("Mouse move Mouse win:::"+currMousePosition + "::::"+currCatPosition+":::"+move);
                    return 1;
                }
                if(ans == 0){
                    draw = true;
                }
            }
            if(draw){
                System.out.println("Mouse move Draw:::"+currMousePosition + "::::"+currCatPosition+":::"+move);
                return 0;
            }else{
                System.out.println("Mouse move Cat win:::"+currMousePosition + "::::"+currCatPosition+":::"+move);
                return 2;
            }
        }else{ // cat turn
            int[] connectedNodesArr = graph[currCatPosition];
            for(int connectedNode: connectedNodesArr){

                if(connectedNode == 0){ // cat is not allowed in position 0(hole)
                    continue;
                }
                ans = catMouseGameResultWithRecursion(graph, connectedNode, currMousePosition, move + 1);

                /*
                    Cat Move:
                    Priority 1 of Cat: Win: Return 2
                        This is Cat move we Cat will prioritize his win.
                            As, Cat see his win he will immediately claim win.

                    Priority 2 of Cat: Draw: Return 0

                    Priority 3 of Cat: Mouse Win: Return 1
                 */
                if(ans == 2){
                    System.out.println("Cat move Cat win:::"+currMousePosition + "::::"+currCatPosition+":::"+move);
                    return 2;
                }
                if(ans == 0){
                    draw = true;
                }
            }
            if(draw){
                System.out.println("Cat move Draw:::"+currMousePosition + "::::"+currCatPosition+":::"+move);
                return 0;
            }else{
                System.out.println("Cat move Mouse Win:::"+currMousePosition + "::::"+currCatPosition+":::"+move);
                return 1;
            }
        }
    }

    //__________________________________________________________________________________________________________

    /*
        Failing for Input5
    */
    private int catMouseGameResultWithRecursionDp(int[][] graph, int currCatPosition, int currMousePosition, int move,
                                                  int[][][] dp) {

        int turn = move;
        if(move > 2 * graph.length){ // draw i.e, every player visited avery node once
            System.out.println("Draw:::"+currMousePosition + "::::"+currCatPosition+":::"+move);
            dp[currMousePosition][currCatPosition][turn] = 0;
            return 0;
        }
        if(currMousePosition == 0){ // mouse win
            dp[currMousePosition][currCatPosition][turn] = 1;
            System.out.println("Mouse win:::"+currMousePosition + "::::"+currCatPosition+":::"+move);
            return 1;
        }
        if(currCatPosition == currMousePosition){ // cat win
            dp[currMousePosition][currCatPosition][turn] = 2;
            System.out.println("Cat win:::"+currMousePosition + "::::"+currCatPosition+":::"+move);
            return 2;
        }

        if(dp[currMousePosition][currCatPosition][turn] != -1){
            return dp[currMousePosition][currCatPosition][turn];
        }

        int ans;
        boolean draw = false;

        // Mouse turn
        if(move % 2 == 0){
            int[] connectedNodesArr = graph[currMousePosition];
            for(int connectedNode: connectedNodesArr){
                ans = catMouseGameResultWithRecursionDp(graph, currCatPosition, connectedNode, move + 1, dp);

                /*
                    Mouse Move:
                    Priority 1 of Mouse: Win: Return 1 [Clear from Input3]
                        This is mouse move and mouse will prioritize his win.
                            As, mouse see his win he will immediately claim win.

                    Priority 2 of Mouse: Draw: Return 0

                    Priority 3 of Mouse: Cat Win: Return 2
                 */
                if(ans == 1){
                    dp[currMousePosition][currCatPosition][turn] = 1;
                    return 1;
                }
                if(ans == 0){
                    draw = true;
                }
            }
            if(draw){
                dp[currMousePosition][currCatPosition][turn] = 0;
                return 0;
            }else{
                dp[currMousePosition][currCatPosition][turn] = 2;
                return 2;
            }
        }else{ // cat turn
            int[] connectedNodesArr = graph[currCatPosition];
            for(int connectedNode: connectedNodesArr){

                if(connectedNode == 0){ // cat is not allowed in position 0(hole)
                    continue;
                }
                ans = catMouseGameResultWithRecursionDp(graph, connectedNode, currMousePosition, move + 1, dp);

                /*
                    Cat Move:
                    Priority 1 of Cat: Win: Return 2
                        This is Cat move and Cat will prioritize his win.
                            As, Cat see his win he will immediately claim win.

                    Priority 2 of Cat: Draw: Return 0

                    Priority 3 of Cat: Mouse Win: Return 1
                 */
                if(ans == 2){
                    dp[currMousePosition][currCatPosition][turn] = 2;
                    return 2;
                }
                if(ans == 0){
                    draw = true;
                }
            }
            if(draw){
                dp[currMousePosition][currCatPosition][turn] = 0;
                return 0;
            }else{
                dp[currMousePosition][currCatPosition][turn] = 1;
                return 1;
            }
        }
    }

}
