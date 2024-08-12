package Solved;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;


/*
https://leetcode.com/problems/random-pick-with-blacklist/

Input 1:
7
2 3 5 -999
7
Input 1 meaning:
n = 7
int[] blacklist = {2, 3, 5}
pick operation need to be call 7 times
Output:
0, 4, 1, 6, 1, 0, 4

Explanation
Random_Pick_with_Blacklist randomPick = new Random_Pick_with_Blacklist(7, {2, 3, 5});
solution.pick(); // return 0, any integer from [0,1,4,6] should be ok. Note that for every call of pick,
                 // 0, 1, 4, and 6 must be equally likely to be returned (i.e., with probability 1/4).
solution.pick(); // return 4
solution.pick(); // return 1
solution.pick(); // return 6
solution.pick(); // return 1
solution.pick(); // return 0
solution.pick(); // return 4

Input 2:
1
-999
3
Output:
0, 0, 0

Input 3:
5
2  1  0  -999
10
Output:
 */

public class Random_Pick_with_Blacklist {

    int[] arr;
    Map<Integer, Integer> map;
    int size;

    Random_Pick_with_Blacklist(int n, int[] blacklist) {
        this.size = n - blacklist.length;

        this.arr = new int[blacklist.length];
        System.arraycopy(blacklist, 0, this.arr,
                0, blacklist.length);
        Arrays.sort(this.arr);

        map = new HashMap<>();
        for(int i = 0; i<this.arr.length; i++)
            map.put(this.arr[i], i);
    }

    public int pick() {

        Random randomObj = new Random();
        int rand = randomObj.nextInt(this.size);

        int ans = pickRandom(rand);
        return ans;
    }

    private int pickRandom(int rand) {
        if(this.arr.length>0 && rand>=this.arr[0]){

            int index = binarySearchLesserThanOrEqualToX(rand);
            index = continuousIndexes(index);

            while(index<this.arr.length && this.map.containsKey(rand+index+1)){
                index = this.map.get(rand+index+1);
                index = continuousIndexes(index);
            }

            return (rand + index)+1;
        }
        return rand;
    }

    private int continuousIndexes(int index) {
        int i = index;
        for(i = index; i<this.arr.length-1; i++){
            if(!(this.arr[i]+1 == this.arr[i+1])){
                return i;
            }
        }
        return i;
    }

    private int binarySearchLesserThanOrEqualToX(int rand) {

        int left = 0, right = this.arr.length - 1;
        int index = -1;

        while (left<=right){
            int mid = left + (right - left)/2;

            if(this.arr[mid]<=rand){
                index = mid;
                left = mid+1;
            }else {
                right = mid-1;
            }
        }

        return index;
    }

    public static void main(String []args) {
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
                int n, x, k;

                System.out.println("Enter n That Denotes The Valid Range Up To [0, n-1]: ");
                n = input.nextInt();

                List<Integer> listOBlacklist = new ArrayList<>();
                System.out.println("Enter Your BlackList Array Integer, For Stop Insertion Press -999");
                x = input.nextInt();
                while (x != -999) {
                    listOBlacklist.add(x);
                    x = input.nextInt();
                }
                int[] blacklist = listOBlacklist.stream().mapToInt(i -> i).toArray();

                Random_Pick_with_Blacklist randomPick = new Random_Pick_with_Blacklist(n, blacklist);

                System.out.println("Enter k That Denotes How Many Times You Want To Pick Random " +
                        "Integers From Desired Set: ");
                k = input.nextInt();
                for(int i = 0; i<k; i++){
                    int randomInteger = randomPick.pick();
                    System.out.print(randomInteger+",  ");
                }
                System.out.println();

            } else {

                int n = 7;
                int[] blacklist = {2, 3, 5};
                int k = 7;

                Random_Pick_with_Blacklist randomPick = new Random_Pick_with_Blacklist(n, blacklist);

                for(int i = 0; i<k; i++){
                    int randomInteger = randomPick.pick();
                    System.out.print(randomInteger+",  ");
                }
                System.out.println();
            }
            t--;
        }
    }
}
