package Try;


import java.util.ArrayList;
import java.util.List;
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
-999
Output: 0



Input2:
1 3 -999
0 -999
3 -999
0 2 -999
-999
Output: 1
*/



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
                System.out.println();
                int player = catAndMouse.catMouseGame(forest);
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
        return 0;
    }

}
