package Solved;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
https://leetcode.com/problems/24-game/

Input: 4 1 8 7
Input meaning: cards = [4,1,8,7]
Output: true
Explanation: (8-4) * (7-1) = 24

Input: 1 2 1 2
Output: false

Input: 3 9 7 7
Output: true

Input: 3 3 8 8
Output: true

Input:  1 1 7 7
Output: false
 */

public class Game_24 {

    public static void main(String[] args) {
        Game_24 game24 = new Game_24();
        Scanner input = new Scanner(System.in);
        int t = 123;
//        System.out.println("Enter Number Of Test Cases = ");
//        t = input.nextInt();
        while (t > 0) {
            int choice;
            System.out.println("Enter Your Choice, You Want to Test Your Own Input Or Want To Test With " +
                    "Example Input, Press 1 For Your Input Else Press Any Num Key = ");
            choice = input.nextInt();
            int x;
            if (choice == 1) {
                List<Integer> gameCards = new ArrayList<>();
                System.out.println("Enter 4 Positive Number In Range Of [1-9] For 24 Game: ");
                int count = 0;
                while(count<4){
                    x = input.nextInt();
                    gameCards.add(x);
                    count++;
                }
                int[] cards = gameCards.stream().mapToInt(i->i).toArray();
                System.out.println("Can We Create 24 Number With Cards: "+game24.judgePoint24(cards));
            } else {
                int []cards = {4,1,8,7};
                System.out.println("Can We Create 24 Number With Cards: "+game24.judgePoint24(cards));
            }
            t--;
        }
    }

    private static final int gameNumToWin = 24;
    public boolean judgePoint24(int[] cards) {

        List<Integer> answerSignal = new ArrayList<>();
        answerSignal.add(0);

        double[] gameCards = new double[cards.length];
        for (int i = 0; i<cards.length; i++)
            gameCards[i] = cards[i];
        judgePoint24Helper(gameCards, answerSignal, '$');
        if(answerSignal.get(0) == 1)
            return true;
        else
            return false;
    }

    private void judgePoint24Helper(double[] gameCards, List<Integer> answerSignal,
                                    char lastOperator) {
        //printDoubleArray(gameCards);
        if(gameCards.length == 1) {
            int answer = (int)Math.round(gameCards[0]);
            if(gameCards[0] == gameNumToWin || (lastOperator == '/' && answer == gameNumToWin)){
                answerSignal.set(0, 1);
                return;
            }
        }else{
            for(int i = 0; i<gameCards.length-1; i++){
                for(int j = i+1; j<gameCards.length; j++){
                    mathematicalOperators(gameCards, i, j, answerSignal);
                }
            }
        }
    }

    private void mathematicalOperators(double[] gameCards, int card1, int card2,
                       List<Integer> answerSignal) {
        //addition
        if(answerSignal.get(0)!=1) {
            double[] newGameCards = generateNewArray(gameCards[card1] + gameCards[card2],
                    gameCards, card1, card2);
            judgePoint24Helper(newGameCards, answerSignal, '+');
        }

        //Subtraction
        if(answerSignal.get(0)!=1) {
            double[] newGameCards = generateNewArray(gameCards[card1] - gameCards[card2],
                    gameCards, card1, card2);
            judgePoint24Helper(newGameCards, answerSignal, '-');
        }
        if(answerSignal.get(0)!=1) {
            double[] newGameCards = generateNewArray(gameCards[card2] - gameCards[card1],
                    gameCards, card1, card2);
            judgePoint24Helper(newGameCards, answerSignal, '-');
        }

        //Multiplication
        if(answerSignal.get(0)!=1) {
            double[] newGameCards = generateNewArray(gameCards[card1] * gameCards[card2],
                    gameCards, card1, card2);
            judgePoint24Helper(newGameCards, answerSignal, '*');
        }

        //Division
        if(answerSignal.get(0)!=1 && gameCards[card2]!=0) {
            double[] newGameCards = generateNewArray(gameCards[card1]/gameCards[card2],
                    gameCards, card1, card2);
            judgePoint24Helper(newGameCards, answerSignal, '/');
        }
        if(answerSignal.get(0)!=1 && gameCards[card1]!=0) {
            double[] newGameCards = generateNewArray(gameCards[card2]/gameCards[card1],
                    gameCards, card1, card2);
            judgePoint24Helper(newGameCards, answerSignal, '/');
        }
    }

    private double[] generateNewArray(double num1, double[] gameCards, int a, int b) {
        double[] newGameCards = new double[gameCards.length - 1];
        int j = 0;
        newGameCards[j] = num1;
        j++;
        List<Integer> remainingIndexes = getRemainingIndexes(gameCards.length, a, b);
        for(int i = 0; i<remainingIndexes.size(); i++){
            newGameCards[j] = gameCards[remainingIndexes.get(i)];
            j++;
        }
        return newGameCards;
    }

    private List<Integer> getRemainingIndexes(int n, int a, int b) {
        List<Integer> remainingIndexes = new ArrayList<>();
        for(int i = 0; i<n; i++){
            if(i!=a && i!=b)
                remainingIndexes.add(i);
        }

        return remainingIndexes;
    }

    private void printDoubleArray(double[] gameCards) {
        for (int i = 0; i<gameCards.length; i++){
            System.out.print(gameCards[i]+"  ");
        }
        System.out.println();
    }
}
