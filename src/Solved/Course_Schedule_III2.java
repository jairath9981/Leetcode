package Solved;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

/*
https://leetcode.com/problems/course-schedule-iii/

Example 1:

Input:
100   200
200   1300
1000  1250
2000  3200
-999 -999
Input: Meaning: courses = [[100,200],[200,1300],[1000,1250],[2000,3200]]
Output: 3
Explanation:
There are totally 4 courses, but you can take 3 courses at most:
First, take the 1st course, it costs 100 days so you will finish it on the 100th day, and ready to take the next course on the 101st day.
Second, take the 3rd course, it costs 1000 days so you will finish it on the 1100th day, and ready to take the next course on the 1101st day.
Third, take the 2nd course, it costs 200 days so you will finish it on the 1300th day.
The 4th course cannot be taken now, since you will finish it on the 3300th day, which exceeds the closed date.

Input: courses =
1  2
-999 -999
Output: 1

Input: courses =
3  2
4  3
-999 -999
Output: 0

Input: courses =
1    2
2    3
-999 -999
Output: 2

Input: courses =
9  14
7  12
1  11
4  7
-999 -999
Output: 3

Input: courses =
7   17
3   12
10   20
9   10
5   20
10   19
4   18
-999 -999
Output: 4

Input: courses =
5  5
4  6
2  6
-999 -999
Output: 2

Input: courses =
9  20
4  14
4  10
6  7
2  14
8  10
6  6
5  7
-999 -999
Output: 4

Input: courses =
3  14
6  18
10  16
3  4
4  5
7  15
2  9
1  6
10  13
5  16
-999 -999
Output: 5
 */

public class Course_Schedule_III2 {
    public static void main(String[] args) {
        Course_Schedule_III2 cs3 = new Course_Schedule_III2();
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
                List<List<Integer>> listOfCourses = new ArrayList<>();
                int x, y;
                System.out.println("Enter Duration And LastDay of Course. For Stop Insertion Enter Any Negative Duration And LastDay");
                x = input.nextInt();
                y = input.nextInt();
                while(x>=0 && y>=0) {
                    listOfCourses.add(List.of(x, y));
                    x = input.nextInt();
                    y = input.nextInt();
                }
                int[][] courses = listOfCourses.stream().map( u -> u.stream().mapToInt(i->i).toArray() ).toArray(int[][]::new);

                System.out.println();
                int maxCourse = cs3.scheduleCourse(courses);
                System.out.println("Maximum Courses One Can Adopt: "+maxCourse);
            }
            else
            {
//                int [][]courses = {{7,11},{1,11},{1,3},{2,6},{5,6},{7,7},{4,8},{2,20},{1,17},{8,11}};
                int [][]courses = {{824,2478},{836,7122},{457,9364},{616,3339},{887,3371},{265,943},
                        {869,3603},{892,916},{816,4782},{3,1420},{657,4790},{256,9900},{316,9003},{225,1797},
                        {613,8852},{218,4369},{333,1776},{578,9762},{55,2756},{645,3767}};
                System.out.println();
                int maxCourse = cs3.scheduleCourse(courses);
                System.out.println("Maximum Courses One Can Adopt: "+maxCourse);
            }
            t--;
        }
    }

    public int scheduleCourse(int[][] courses) {
        Arrays.sort(courses, (a,b) -> a[1] - b[1]);
        int max =  countMaxCourseCanAccommodate(courses);
        return max;
    }

    private int countMaxCourseCanAccommodate(int[][]courses) {
        int durationTimeReached = 0;
        PriorityQueue<Integer> maxDurationAccommodated = new PriorityQueue<>(Collections.reverseOrder());
        for(int i = 0; i<courses.length; i++) {
            int courseDuration = courses[i][0];
            int courseEndDateForCompletion = courses[i][1];
            durationTimeReached+=courseDuration;
            maxDurationAccommodated.add(courseDuration);
            if(durationTimeReached>courseEndDateForCompletion){
                durationTimeReached-=maxDurationAccommodated.peek();
                maxDurationAccommodated.poll();
            }
        }
        return maxDurationAccommodated.size();
    }
}
