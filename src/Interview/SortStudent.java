package Interview;

import java.util.Comparator;

class Student_SortStudent implements Comparator<Student_SortStudent> {
    String name;
    String id;

    public Student_SortStudent(String name, String id){
        this.name = name;
        this.id = id;
    }

    public int m1(){
        int a = 0;
        try{
            a = 10;
            return a;
        }catch(Exception e){
            throw e;
        }finally{
            a = 15;
            System.out.println("a: "+a);
        }
    }

    @Override
    public int compare(Student_SortStudent o1, Student_SortStudent o2) {
        return o1.name.compareTo(o2.name);
    }
}

public class SortStudent {
    public static void main(String[]args){
        Student_SortStudent s1 = new Student_SortStudent("jairath", "123");
        int x = s1.m1();
        System.out.println("returned Value: "+x);
    }
}
