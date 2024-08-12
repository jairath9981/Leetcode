package Solved;

import java.util.Scanner;

/*
https://leetcode.com/problems/student-attendance-record-ii/


Input: 2
Input meaning: n = 2
Output: 8
Explanation: There are 8 records with length 2 that are eligible for an award:
"PP", "AP", "PA", "LP", "PL", "AL", "LA", "LL"
Only "AA" is not eligible because there are 2 absences (there need to be fewer than 2).

Input:1
Output: 3

Input: 10101
Output: 183236316
 */

public class Student_Attendance_Record22 {

    public static void main(String[] args) {
        Student_Attendance_Record22 str22 = new Student_Attendance_Record22();
        Scanner input = new Scanner(System.in);
        int t = 123;
//        System.out.println("Enter Number Of Test Cases = ");
//        t = input.nextInt();
        while (t > 0) {
            int choice;
            System.out.println("Enter Your Choice, You Want to Test Your Own Input Or Want To Test With " +
                    "Example Input, Press 1 For Your Input Else Press Any Num Key = ");
            choice = input.nextInt();
            if (choice == 1) {
                int n;
                System.out.println("Enter Number Of Days School Opened For Student Attendance: ");
                n = input.nextInt();
                int result = str22.checkRecord(n);
                System.out.println("Number Of Ways Possible Student Attendance Is Eligible For Award: "
                        + result);
            } else {
                int n  = 1;
                int result = str22.checkRecord(n);
                System.out.println("Number Of Ways Possible Student Attendance Is Eligible For Award: "
                        + result);
            }
            t--;
        }
    }

    public final int mod = 1000000007;
    public int checkRecord(int n) {
        if (n >= 1) {
            // count for n = 1

            /*
                can form from previous state By Simply Appending P
                    zero_Consecutive_L_plus_0A (append P) +
                    one_Consecutive_L_plus_0A (append P)  +
                    two_Consecutive_L_plus_0A  (append P) +
             */
            long zero_Consecutive_L_plus_0A = 1; // P

            /*
                can form from previous state By Simply Appending P Or A
                    zero_Consecutive_L_plus_0A (append A) +
                    zero_Consecutive_L_plus_1A (append P) +
                    one_Consecutive_L_plus_0A (append A)  +
                    one_Consecutive_L_plus_1A (append P) +
                    two_Consecutive_L_plus_0A (append A) +
                    two_Consecutive_L_plus_1A  (append P)
             */
            long zero_Consecutive_L_plus_1A = 1; // A

            /*
                can form from previous state zero_Consecutive_L_plus_0A
             */
            long one_Consecutive_L_plus_0A = 1; // L

            /*
                can form from previous state zero_Consecutive_L_plus_1A
             */
            long one_Consecutive_L_plus_1A = 0;

            /*
                can form from previous state one_Consecutive_L_plus_0A
             */
            long two_Consecutive_L_plus_0A = 0;

            /*
                can form from previous state one_Consecutive_L_plus_1A
             */
            long two_Consecutive_L_plus_1A = 0;

            for (int i = 2; i<=n; i++){
                long prev_zero_Consecutive_L_plus_0A = zero_Consecutive_L_plus_0A;
                long prev_zero_Consecutive_L_plus_1A = zero_Consecutive_L_plus_1A;
                long prev_one_Consecutive_L_plus_0A = one_Consecutive_L_plus_0A;
                long prev_one_Consecutive_L_plus_1A = one_Consecutive_L_plus_1A;
                long prev_two_Consecutive_L_plus_0A = two_Consecutive_L_plus_0A;
                long prev_two_Consecutive_L_plus_1A = two_Consecutive_L_plus_1A;
                zero_Consecutive_L_plus_0A = (prev_zero_Consecutive_L_plus_0A + prev_one_Consecutive_L_plus_0A +
                        prev_two_Consecutive_L_plus_0A) % mod;
                zero_Consecutive_L_plus_1A = (prev_zero_Consecutive_L_plus_1A + prev_zero_Consecutive_L_plus_0A +
                        prev_one_Consecutive_L_plus_0A + prev_one_Consecutive_L_plus_1A + prev_two_Consecutive_L_plus_0A +
                        prev_two_Consecutive_L_plus_1A) % mod;
                one_Consecutive_L_plus_0A = (prev_zero_Consecutive_L_plus_0A) % mod;
                one_Consecutive_L_plus_1A = (prev_zero_Consecutive_L_plus_1A) % mod;
                two_Consecutive_L_plus_0A = (prev_one_Consecutive_L_plus_0A) % mod;
                two_Consecutive_L_plus_1A = (prev_one_Consecutive_L_plus_1A) % mod;
            }
            return (int) ((zero_Consecutive_L_plus_0A + zero_Consecutive_L_plus_1A +
                                one_Consecutive_L_plus_0A + one_Consecutive_L_plus_1A +
                                two_Consecutive_L_plus_0A + two_Consecutive_L_plus_1A) % mod);
        }
        return 0;
    }
}
