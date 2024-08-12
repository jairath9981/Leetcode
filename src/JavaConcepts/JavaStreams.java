package JavaConcepts;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
collect all Person whose age is less than 30. And add them in AnotherPerson class. Using Streams.
 */

/*
map:
By using map, you transform the object values.

The map operation allows us to apply a function, that takes in a parameter of one type, and
returns something else.

filter:
Filter is used for filtering the data, it always returns the boolean value. If it returns
true, the item is added to list else it is filtered out (ignored)
*/

class Person{
    int age;
    String gender;
    String name;

    Person(int age, String gender, String name){
        this.age = age;
        this.gender = gender;
        this.name = name;
    }

    void display(){
        System.out.println("Name: " + this.name
                + ", Age: " + this.age
                + ", Gender: " + this.gender);
    }
}

class AnotherPerson{
    int age;
    String gender;
    String name;

    AnotherPerson(int age, String gender, String name){
        this.age = age;
        this.gender = gender;
        this.name = name;
    }

    void display(){
        System.out.println("Name: " + this.name
                + ", Age: " + this.age
                + ", Gender: " + this.gender);
    }
}

public class JavaStreams {
    public static void main(String[] args) {
        List<Person> listOfPerson = new ArrayList<>();
        listOfPerson.add(new Person(25, "Male", "Akshit Jairath"));
        listOfPerson.add(new Person(23, "Girl", "Shruti Jairath"));
        listOfPerson.add(new Person(27, "Male", "Anshul Jairath"));
        listOfPerson.add(new Person(32, "Girl", "Neha Jairath"));
        listOfPerson.add(new Person(33, "Male", "Vivek Jairath"));

        // collect all Person whose age is less than 30. And add them in AnotherPerson class.
        // Using Streams.
        JavaStreams javaStreams = new JavaStreams();
        int x = 30;
        List<AnotherPerson> listOfAnotherPerson =
                javaStreams.collectPersonWhoseAgeIsLessThanX(listOfPerson, x);

        System.out.println("List of Another Person Whose Age is Less Than 30");
        for (int i = 0; i<listOfAnotherPerson.size(); i++){
            listOfAnotherPerson.get(i).display();
        }
    }

    public List<AnotherPerson> collectPersonWhoseAgeIsLessThanX(
            List<Person> listOfPerson, int x){
        List<AnotherPerson> listOfAnotherPerson =
                listOfPerson.stream().map(p->new AnotherPerson(
                        p.age, p.gender, p.name)).filter(ap-> ap.age<x).collect(Collectors.toList());
        return listOfAnotherPerson;
    }
}
