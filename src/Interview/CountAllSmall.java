package Interview;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

/*
3   4   1   1   3   5   9    4   2   3   4   7     -999
3   6   0   0   3   9   11   6   2   3   6   10
 */
class Pair implements Comparable<Pair>{
    int val;
    int index;

    Pair(int val, int index){
        this.val = val;
        this.index = index;
    }

    @Override
    public int compareTo(Pair o) {
        if(this.val>o.val)
            return 1;
        return -1;
    }
}

public class CountAllSmall {

    public static void main(String []args) {
        CountAllSmall sliding_window_median = new CountAllSmall();
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
                int x;
                List<Integer> listOfNumbers = new ArrayList<>();
                System.out.println("Enter Your Integer Array, For Stop Insertion Press -999");
                x = input.nextInt();
                while (x != -999) {
                    listOfNumbers.add(x);
                    x = input.nextInt();
                }
                int[] nums = listOfNumbers.stream().mapToInt(i -> i).toArray();
                int[] ans = sliding_window_median.countAllSmall(nums);
                System.out.println("Ans");
                printArray(ans);
            } else {
                int[] nums = {3,2,1,1};
                int[] ans = sliding_window_median.countAllSmall(nums);
                System.out.println("Ans");
                printArray(ans);
            }
            t--;
        }
    }

    private int[] countAllSmall(int[] nums) {
        PriorityQueue<Pair> pq = new PriorityQueue<>();
        for(int i = 0; i<nums.length; i++){
            pq.add(new Pair(nums[i], i));
        }
        int small = 0;
        Pair pair = pq.poll();
        int prev = pair.val;
        nums[pair.index] = small;
        small++;
        int same = 1;
        while(!pq.isEmpty()){
            Pair pair2 = pq.poll();
            if(pair2.val == prev){
                nums[pair2.index] = small - same;
                same++;
            }else {
                same = 1;
                nums[pair2.index] = small;
            }
            small++;
            prev = pair2.val;

        }
        return nums;
    }

    private static void printArray(int[] ans) {
        for(int i = 0; i<ans.length; i++){
            System.out.println(ans[i]+"   ");
        }
    }
}


