package Interview;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


class Student_JavaStreams2 {
    int marks;
    String name;
    int id;

    public Student_JavaStreams2(int marks, String name, int id){
        this.marks = marks;
        this.name = name;
        this.id = id;
    }

    public int getMarks() {
        return marks;
    }

    public String getName() {
        return name;
    }
}
class JavaStreams2 {
    public static void main(String[] args) {
        Student_JavaStreams2 s1 = new Student_JavaStreams2(100, "Akshit", 1);
        Student_JavaStreams2 s2 = new Student_JavaStreams2(100, "saurabh", 1);
        Student_JavaStreams2 s3 = new Student_JavaStreams2(99, "rohani", 1);
        Student_JavaStreams2 s4 = new Student_JavaStreams2(99, "priyanka", 1);
        Student_JavaStreams2 s5 = new Student_JavaStreams2(1, "xyz", 1);
        Student_JavaStreams2 s6 = new Student_JavaStreams2(2, "abc", 1);
        Student_JavaStreams2 s7 = new Student_JavaStreams2(3, "pqr", 1);
        List<Student_JavaStreams2> listOdStudents = new ArrayList<>();
        listOdStudents.add(s1);
        listOdStudents.add(s2);
        listOdStudents.add(s3);
        listOdStudents.add(s4);
        listOdStudents.add(s5);
        listOdStudents.add(s6);
        listOdStudents.add(s7);


        Map<Integer, List<String>> map = listOdStudents.stream().collect(Collectors.groupingBy(
                Student_JavaStreams2::getMarks, LinkedHashMap:: new,
                Collectors.mapping(Student_JavaStreams2::getName, Collectors.toList())
        ));

        System.out.println(map);
    }
}