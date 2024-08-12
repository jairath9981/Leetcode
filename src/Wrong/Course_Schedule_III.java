package Wrong;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

/*
https://leetcode.com/problems/course-schedule-iii/

                    // Giving Wrong Answer For Some Input So Will Try To Rectify in part2
        //         int [][]courses = {{824,2478},{836,7122},{457,9364},{616,3339},{887,3371},{265,943},
        //                        {869,3603},{892,916},{816,4782},{3,1420},{657,4790},{256,9900},{316,9003},{225,1797},
        //                        {613,8852},{218,4369},{333,1776},{578,9762},{55,2756},{645,3767}};

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

class CourseDetails_Course_Schedule_III {
    int courseDuration;
    int lastDayOfCourseCompletion;
    int lastDayToStartCourse;

    public CourseDetails_Course_Schedule_III(int courseDuration, int lastDayOfCourseCompletion,
                                             int lastDayToStartCourse){
        this.courseDuration = courseDuration;
        this.lastDayOfCourseCompletion = lastDayOfCourseCompletion;
        this.lastDayToStartCourse = lastDayToStartCourse;
    }

    public int getCourseDuration() { return courseDuration; }

    public int getLastDayOfCourseCompletion() { return lastDayOfCourseCompletion; }

    public int getLastDayToStartCourse() { return lastDayToStartCourse; }

    @Override
    public String toString() {
        return "CourseDetails_Course_Schedule_III{" +
                "courseDuration=" + courseDuration +
                ", lastDayOfCourseCompletion=" + lastDayOfCourseCompletion +
                ", lastDayToStartCourse=" + lastDayToStartCourse +
                '}';
    }
    @Override
    public boolean equals(Object obj){
        if (!(obj instanceof CourseDetails_Course_Schedule_III))
            return false;

        CourseDetails_Course_Schedule_III courseDetailsCourseScheduleIii = (CourseDetails_Course_Schedule_III)obj;
        return getCourseDuration() == courseDetailsCourseScheduleIii.getCourseDuration() &&
                getLastDayOfCourseCompletion() == courseDetailsCourseScheduleIii.getLastDayOfCourseCompletion();
    }
}

class AscOrder_LastDayToStartCourse_CourseDetails_Course_Schedule_III implements
        Comparator<CourseDetails_Course_Schedule_III> {

    @Override
    public int compare(CourseDetails_Course_Schedule_III o1,
                       CourseDetails_Course_Schedule_III o2) {
        if(o1.lastDayToStartCourse >o2.lastDayToStartCourse)
            return 1;
        else if(o1.lastDayToStartCourse >=o2.lastDayToStartCourse){
            if(o1.courseDuration>o2.courseDuration)
                return 1;
        }
        return -1;
    }
}

class DescOrder_CourseDuration_CourseDetails_Course_Schedule_III implements
        Comparator<CourseDetails_Course_Schedule_III> {

    @Override
    public int compare(CourseDetails_Course_Schedule_III o1,
                       CourseDetails_Course_Schedule_III o2) {
        if(o1.courseDuration<o2.courseDuration)
            return 1;
        return -1;
    }
}

public class Course_Schedule_III {
    public static void main(String[] args) {
        Course_Schedule_III cs3 = new Course_Schedule_III();
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
//                maxCourse = cs3.scheduleCourse2(courses);
//                System.out.println("Maximum Courses One Can Adopt: "+maxCourse);
            }
            t--;
        }
    }

    public int scheduleCourse(int[][] courses) {
        List<CourseDetails_Course_Schedule_III> ascOrderLastDayToStartCourse =
                sortCoursesOnBasisOfLastDayToStartCourse(courses);
        System.out.println("ascOrderLastDayToStartCourse = "+ascOrderLastDayToStartCourse.toString());
        int max =  countMaxCourseCanAccommodate(ascOrderLastDayToStartCourse);
        return max;
    }

    private int countMaxCourseCanAccommodate(
            List<CourseDetails_Course_Schedule_III> ascOrderLastDayToStartCourse) {
        int count = 0;
        int durationTimeReached = 0;
        PriorityQueue<CourseDetails_Course_Schedule_III> maxHeapForDuration_ForCourseAccommodated =
                new PriorityQueue<>(new DescOrder_CourseDuration_CourseDetails_Course_Schedule_III());
        Map<CourseDetails_Course_Schedule_III, Integer> courseAdoptedToIndexInList = new HashMap<>();
        for(int i = 0; i<ascOrderLastDayToStartCourse.size() && i>=0; i++){
            CourseDetails_Course_Schedule_III courseDetailsToAccommodate =
                    ascOrderLastDayToStartCourse.get(i);
             System.out.println("courseDetailsToAccommodate-> duration = "
                     +courseDetailsToAccommodate.courseDuration+" endDate = "
                     +courseDetailsToAccommodate.lastDayOfCourseCompletion+" LastStartDate = "
                     +courseDetailsToAccommodate.lastDayToStartCourse);
            /* we can adopt this course */
            if(courseAdoptedToIndexInList.isEmpty() ||
                    !courseAdoptedToIndexInList.containsKey(courseDetailsToAccommodate)) {
                if (courseDetailsToAccommodate.lastDayToStartCourse >= durationTimeReached) {
                    durationTimeReached = durationTimeReached + courseDetailsToAccommodate.courseDuration;
                    count++;
                    maxHeapForDuration_ForCourseAccommodated.add(courseDetailsToAccommodate);
                    courseAdoptedToIndexInList.put(courseDetailsToAccommodate, i);
                     System.out.println("course Accommodated DurationReached = " + durationTimeReached
                             + " count = " + count);
                } else if (!maxHeapForDuration_ForCourseAccommodated.isEmpty() &&
                        courseDetailsToAccommodate.courseDuration <
                                maxHeapForDuration_ForCourseAccommodated.peek().courseDuration) {
                    // we can't adopt one of the course but can we reduce durationTimeReached by adopting
                    // this course
                    CourseDetails_Course_Schedule_III maxDurationCourseAccommodated =
                            maxHeapForDuration_ForCourseAccommodated.peek();

                    System.out.println("Need To Reduce Duration By: "+maxDurationCourseAccommodated.courseDuration);
                    durationTimeReached = durationTimeReached - maxDurationCourseAccommodated.courseDuration;
                    maxHeapForDuration_ForCourseAccommodated.poll();
                    courseAdoptedToIndexInList.remove(maxDurationCourseAccommodated);

                    durationTimeReached = durationTimeReached + courseDetailsToAccommodate.courseDuration;
                    maxHeapForDuration_ForCourseAccommodated.add(courseDetailsToAccommodate);
                    courseAdoptedToIndexInList.put(courseDetailsToAccommodate, i);
                     System.out.println("course Accommodated Final Reduced DurationReached = " + durationTimeReached
                             + " count = " + count);
                    int index = binarySearchGreaterThenOrEqualToX(ascOrderLastDayToStartCourse, durationTimeReached,
                            courseAdoptedToIndexInList) - 1;
                    if(index>=0 && index<i)
                        i = index;
                     System.out.println("Returned Index = "+i);
                }
            }
        }
        return count;
    }

    private int binarySearchGreaterThenOrEqualToX(
            List<CourseDetails_Course_Schedule_III> ascOrderLastDayToStartCourse, int x,
            Map<CourseDetails_Course_Schedule_III, Integer> courseAdoptedToIndexInList) {
        int ans = -2;
        int left = 0;
        int right = ascOrderLastDayToStartCourse.size()-1;
        while (right>=left){
            int mid = left+(right-left)/2;
            if(ascOrderLastDayToStartCourse.get(mid).lastDayToStartCourse>=x){
                if(!courseAdoptedToIndexInList.containsKey(ascOrderLastDayToStartCourse.get(mid))){
                    ans = mid;
                    right = mid - 1;
                }else{
                    left = mid + 1;
                }
            }else if(ascOrderLastDayToStartCourse.get(mid).lastDayToStartCourse<x){
                left = mid + 1;
            }
        }
        return  ans;
    }

    private List<CourseDetails_Course_Schedule_III> sortCoursesOnBasisOfLastDayToStartCourse(int[][] courses) {
        List<CourseDetails_Course_Schedule_III> ascOrderLastDayToStartCourse =
                new ArrayList<>();
        for(int i = 0; i<courses.length; i++){
            int courseDuration = courses[i][0];
            int courseCompletionDay = courses[i][1];
            if(courseCompletionDay>=courseDuration) {
                int courseLastDayToAdopt = courseCompletionDay - courseDuration;
                ascOrderLastDayToStartCourse.add(new CourseDetails_Course_Schedule_III(courseDuration,
                        courseCompletionDay, courseLastDayToAdopt));
            }
        }
        ascOrderLastDayToStartCourse.sort(new AscOrder_LastDayToStartCourse_CourseDetails_Course_Schedule_III());
        return ascOrderLastDayToStartCourse;
    }
}
