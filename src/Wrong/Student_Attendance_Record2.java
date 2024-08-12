package Wrong;

import java.util.Scanner;

/*
https://leetcode.com/problems/student-attendance-record-ii/

// Some Optimization issue giving time out error Try to Resolve This problem in 2nd part

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

public class Student_Attendance_Record2 {

    public static void main(String[] args) {
        Student_Attendance_Record2 str2 = new Student_Attendance_Record2();
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
                int result = str2.checkRecord(n);
                System.out.println("Number Of Ways Possible Student Attendance Is Eligible For Award: "
                        + result);
            } else {
                int n  = 1;
                int result = str2.checkRecord(n);
                System.out.println("Number Of Ways Possible Student Attendance Is Eligible For Award: "
                        + result);
            }
            t--;
        }
    }

    public final int mod = 1000000007;
    public int checkRecord(int n) {
        if(n >= 1) {
            int absent = 0;
            int present = 0;
            int consecutiveLate = 0;

            int[]totalValidRecord = new int[1];
            totalValidRecord[0] = 0;
            checkRecordHelper(n, present, absent, consecutiveLate, totalValidRecord);
            return totalValidRecord[0];
        }
        return 0;
    }

    private void checkRecordHelper(int n, int present, int absent, int consecutiveLate,
                  int[] totalValidRecord) {
        if(n>=1){
            // add present
            checkRecordHelper(n-1, present+1, absent, 0, totalValidRecord);
            // add absent
            if(absent == 0){
                 checkRecordHelper(n-1, present, absent+1, 0, totalValidRecord);
            }
            // add late
            if(consecutiveLate < 2){
                 checkRecordHelper(n-1, present, absent, consecutiveLate+1, totalValidRecord);
            }
        }
        if(n == 0){
            totalValidRecord[0] = (totalValidRecord[0] + 1) % mod;
        }
    }
}
