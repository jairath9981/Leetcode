package JavaConcepts;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


class Student{
    int marks;
    String name;
    int id;

    public Student(int marks, String name, int id){
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
        Student s1 = new Student(100, "Akshit", 1);
        Student s2 = new Student(100, "saurabh", 1);
        Student s3 = new Student(99, "rohani", 1);
        Student s4 = new Student(99, "priyanka", 1);
        Student s5 = new Student(1, "xyz", 1);
        Student s6 = new Student(2, "abc", 1);
        Student s7 = new Student(3, "pqr", 1);
        List<Student> listOdStudents = new ArrayList<>();
        listOdStudents.add(s1);
        listOdStudents.add(s2);
        listOdStudents.add(s3);
        listOdStudents.add(s4);
        listOdStudents.add(s5);
        listOdStudents.add(s6);
        listOdStudents.add(s7);


        Map<Integer, List<String>> map = listOdStudents.stream().collect(Collectors.groupingBy(
                Student::getMarks, LinkedHashMap:: new,
                Collectors.mapping(Student::getName, Collectors.toList())
        ));

        System.out.println(map);
    }
}