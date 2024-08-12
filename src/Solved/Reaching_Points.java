package Solved;

import java.util.Scanner;

/*
https://leetcode.com/problems/reaching-points/

Input:
1
1
3
5
Input 1 meaning: sx = 1, sy = 1, tx = 3, ty = 5
Output: true
Explanation:
One series of moves that transforms the starting point to the target is:
(1, 1) -> (1, 2)
(1, 2) -> (3, 2)
(3, 2) -> (3, 5)

Input 2:
1
1
2
2
Output: false

Input 3:
1
1
1
1
Output: true

Input 4:
1
14
999999995
14
Output: true
*/


public class Reaching_Points {

    public static void main(String[] args) {
        Reaching_Points reaching_points = new Reaching_Points();
        Scanner input = new Scanner(System.in);
        int t = 123;
//        System.out.println("Enter Number Of Test Cases = ");
//        t = input.nextInt();
        while (t > 0) {
            int choice;
            System.out.println("Enter Your Choice, You Want to Test Your Own Input Or Want To Test With " +
                    "Example Input, Press 1 For Your Input Else Press Any Num Key = ");
            choice = input.nextInt();
            int sx, sy, tx, ty;
            if (choice == 1) {
                System.out.println("Enter Source x-Coordinate: ");
                sx = input.nextInt();
                System.out.println("Enter Source y-Coordinate: ");
                sy = input.nextInt();
                System.out.println("Enter Target x-Coordinate: ");
                tx = input.nextInt();
                System.out.println("Enter Target y-Coordinate: ");
                ty = input.nextInt();
                boolean isReachable = reaching_points.reachingPoints(sx, sy, tx, ty);
                System.out.println("Is It Possible To Convert The Point (sx, sy) To " +
                        "The Point (tx, ty) Through Some Operations: "+isReachable);
            } else {
                sx = 1;
                sy = 1;
                tx = 3;
                ty = 5;
                boolean isReachable = reaching_points.reachingPoints(sx, sy, tx, ty);
                System.out.println("Is It Possible To Convert The Point (sx, sy) To " +
                        "The Point (tx, ty) Through Some Operations: "+isReachable);
            }
            t--;
        }
    }

    public boolean reachingPoints(int sx, int sy, int tx, int ty) {
        if(sx == tx && sy == ty)
            return true;
        if(sx>tx || sy>ty)
            return false;
        boolean reachable = traverseFromTargetToSource(sx, sy, tx, ty);
        return reachable;
    }

    private boolean traverseFromTargetToSource(int sx, int sy, int tx, int ty) {
        while(tx>sx || ty>sy){
            int flagOperation = 0;
            if(ty>tx && ty!=sy){
                int notGreaterThen = ty-sy;
                ty = ty - (getMultiple(ty, tx, notGreaterThen)*tx);
                flagOperation = 1;
                //System.out.println("Operation on ty: "+ty);
            }else if(tx>ty && tx!=sx){
                int notGreaterThen = tx-sx;
                tx = tx - (getMultiple(tx, ty, notGreaterThen)*ty);
                flagOperation = 1;
                //System.out.println("Operation on tx: "+tx);
            }
            if(flagOperation == 0){
                return false;
            }
        }
        if(sx == tx && sy == ty)
            return true;
        return false;
    }

    private int getMultiple(int a, int b, int c) {
        if(a>b){
            int divisor = a/b;
            if(b*divisor>c){
                divisor = binarySearchLessThenEqualTo(1, divisor, b, c);
            }
            return divisor;
        }
        return -1;
    }

    private int binarySearchLessThenEqualTo(int low, int high,
                int multiplicationFactor, int comparisonFactor) {
        int ans = low;
        while(low<=high){
            int midDivisor = low + (high - low)/2;
            if(midDivisor*multiplicationFactor>comparisonFactor){
                high = midDivisor-1;
            }else {
                ans = midDivisor;
                low = midDivisor+1;
            }
        }
        return ans;
    }
}
