package JavaConcepts;

/*
(a) Abstract class can not be instantiated. Declaring a class as abstract with no
abstract functions means that we don't allow it to be instantiated on its own. InFact we can not
instantiate Abstract class in any situation.
(b) But Abstract class can have Constructor. Advantage of Constructor in Abstract
Class. -> To initialize variable of Abstract Class.
(c) Call to 'super()' must be first statement in constructor body.
*/

abstract class AbstractClass {
    int i;
     AbstractClass(int i){
         this.i = i;
        System.out.println("Constructor of Abstract Class(Parent)");
    }
    public void display(){
        System.out.println("Non Abstract Function Of Abstract Class. Value Of i = "+i);
        System.out.println("Abstract Class Without Abstract Function");
    }
}

public class AbstractExample1 extends AbstractClass {
    AbstractExample1(){
        // if you remove comments of "System.out.println" error will come
        // (c) Call to 'super()' must be first statement in constructor body
        //System.out.println("Before Calling Constructor Of Abstract Class(Child Class)");
        super(9981);
        System.out.println("Constructor Of AbstractExample1(Child Class). Value Of i = "+i);
    }

    public static void main(String[] args) {
        // if you remove comments of "AbstractClass abstractClass = new AbstractClass()" error
        // will come
        //(a) Abstract class can not be instantiated
        //AbstractClass abstractClass = new AbstractClass();
        AbstractExample1 abstractClass1 = new AbstractExample1();
        abstractClass1.display();
    }
}
