package Solved;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/*
https://leetcode.com/problems/three-equal-parts/description/?page=10

Input 1:
1 0 1 0 1 -999
Input Meaning: arr = [1,0,1,0,1]
Output: [0,3]

Input 2:
1 1 0 1 1 2
Input Meaning: arr = [1,1,0,1,1]
Output: [-1,-1]


Input 3:
1 1 0 0 1 3
Input Meaning: arr = [1,1,0,0,1]
Output: [0,2]

Input 108:
0 0 0 0 0 4
Input Meaning: arr = [0,0,0,0,0]
Output: [0,4]

Input 111:
1 1 1 0 0 1 1 0 1 0 1 1 1 1 1 1 4
Input Meaning: arr = [1,1,1,0,0,1,1,0,1,0,1,1,1,1,1,1]
Output: [-1,-1]

Input 118:
1 1 0 1 1 1 0 1 4
Input Meaning: arr = [1,1,0,1,1,1,0,1]
Output: [-1,-1]
 */


public class Three_Equal_Parts {

    public static void main(String[] args) {
        Three_Equal_Parts threeEqualParts = new Three_Equal_Parts();
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

                int x;
                List<Integer> binaryList = new ArrayList<>();
                System.out.println("Enter Your binary Array[0/1], For Stop Insertion Press any non binary number");
                x = input.nextInt();
                while(x == 0 || x == 1) {
                    binaryList.add(x);
                    x = input.nextInt();
                }
                int[] arr = binaryList.stream().mapToInt(i->i).toArray();
                int []ans = threeEqualParts.threeEqualParts(arr);
                if(ans[0] == -1){
                    System.out.println("Binary array three equal parts not possible");
                    System.out.println(ans[0]+"  "+ans[1]);
                }else{
                    System.out.println("Binary array three equal parts = ["+ans[0]+", "+ans[1]+"]");
                }
            }
            else {
                int [] arr = {1, 0, 1, 0, 1};
                int []ans = threeEqualParts.threeEqualParts(arr);
                if(ans[0] == -1){
                    System.out.println("Binary array three equal parts not possible");
                    System.out.println(ans[0]+"  "+ans[1]);
                }else{
                    System.out.println("Binary array three equal parts = ["+ans[0]+", "+ans[1]+"]");
                }
            }
            t--;
        }
    }


    public int[] threeEqualParts(int[] arr) {
        return threeEqualPartsAlgo1(arr);
    }

    private int[] threeEqualPartsAlgo1(int[] arr) {
        int []ans = {-1, -1};

        int oneCount = countOfAllOnes(arr);
        if(oneCount == 0) return new int[]{0, arr.length - 1};
        if(oneCount % 3 != 0) return ans;

        int endZeroCount = countOfEndingZeros(arr);
        int oneTargetCount = oneCount/3;

        StringBuilder aBinaryPart = new StringBuilder();
        int a = equalPart(arr, 0, aBinaryPart, oneTargetCount, endZeroCount);
        System.out.println("aBinaryPart: "+aBinaryPart);
        if(a == -1) return ans;

        StringBuilder bBinaryPart = new StringBuilder();
        int b = equalPart(arr, a+1, bBinaryPart, oneTargetCount, endZeroCount);
        System.out.println("bBinaryPart: "+bBinaryPart);
        if(b == -1 || !isBinaryEqual(aBinaryPart, bBinaryPart)) return ans;

        StringBuilder cBinaryPart = new StringBuilder();
        int c = equalPart(arr, b+1, cBinaryPart, oneTargetCount, endZeroCount);
        System.out.println("cBinaryPart: "+cBinaryPart);
        if(c == -1 || !isBinaryEqual(aBinaryPart, cBinaryPart)) return ans;

        ans[0] = a;
        ans[1] = b+1;
        return ans;
    }

    private int equalPart(int[] arr, int startFrom, StringBuilder binaryPart, int oneTargetCountReq,
                          int endZeroCountReq) {

        int index = binaryEqualPartWithReqOnes(arr, startFrom, binaryPart, oneTargetCountReq);
        index = binaryEqualPartWithReqEndingZeros(arr, index, binaryPart, endZeroCountReq);
        return index;
    }

    private int binaryEqualPartWithReqOnes(int[] arr, int startFrom, StringBuilder binaryPart,
                                           int oneTargetCountReq) {
        int oneCounter = 0;
        int i;
        for (i = startFrom; i<arr.length; i++){
            if(arr[i] == 1){
                oneCounter++;
                binaryPart.append(arr[i]);
            }
            if(arr[i] == 0 && oneCounter > 0){
                binaryPart.append(arr[i]);
            }
            if(oneCounter == oneTargetCountReq){
                break;
            }
        }
        if(i == arr.length)
            return -1;

        return i;
    }

    private int binaryEqualPartWithReqEndingZeros(int[] arr, int startFrom, StringBuilder binaryPart,
                                                  int endZeroCountReq) {

        if(endZeroCountReq > 0) {
            int zeroCounter = 0;
            int j;
            for (j = startFrom + 1; j < arr.length; j++) {
                if (arr[j] != 0) {
                    break;
                }
                if (arr[j] == 0) {
                    zeroCounter++;
                    binaryPart.append(arr[j]);
                }
                if (zeroCounter == endZeroCountReq) {
                    break;
                }
            }
            if(zeroCounter == endZeroCountReq){
                return j;
            }
            return -1;
        }
        return startFrom;
    }

    private boolean isBinaryEqual(StringBuilder aBinaryPart, StringBuilder bBinaryPart) {
        if(aBinaryPart.length()!=bBinaryPart.length())
            return false;

        for(int i = 0; i<aBinaryPart.length(); i++){
            if(aBinaryPart.charAt(i)!=bBinaryPart.charAt(i))
                return false;
        }
        return true;
    }

    private int countOfAllOnes(int[] arr) {
        int counter = 0;
        for (int num : arr) {
            if (num == 1) {
                counter++;
            }
        }
        return counter;
    }

    private int countOfEndingZeros(int[] arr) {
        int counter = 0;
        for (int i = arr.length - 1; i>=0; i--){
            if(arr[i] == 0){
                counter++;
            }else{
                break;
            }
        }
        return counter;
    }
}
