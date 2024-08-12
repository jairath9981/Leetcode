package JavaConcepts;

/*
Best can be explained with Garbage Collector

1 The "finally" block will be executed even after a return statement in a try block.
The "finally" block will always execute even an exception occurred or not in Java.

2) Scenarios Of return(try/catch/finally):
    a) if return is present in all 3 blocks try, catch and finally -> finally overrides return of
    try and catch
    b) if return is present in 2 blocks try and catch -> catch return will be returned only
    when exception occurred. And remember try return is unreachable when exception occured in
    try block.
    c) if try returns some variable and finally overrides that variable value then finally successfully
    overrides that variable value but return value will not be the updated one.

3) Scenarios where finally block is unreachable:
    a)If you invoke System.exit().
    b) If you invoke Runtime.getRuntime().halt(exitStatus)
    c) If the JVM crashes first
    d) If the JVM reaches an infinite loop (or some other non-interpretable,
    non-terminating statement) in the try or catch block
    e) If the OS forcibly terminates the JVM process; e.g., kill -9 <pid> on UNIX
    f) If the host system dies; e.g., power failure, hardware error, OS panic, et cetera

4) Ways to achieve Garbage Collection:
   a) Nullifying the reference variable
   b) Re-assigning the reference variable
   c) An object created inside the method
   d) Island of Isolation
 */

class EmployeeLinkList {
    int id;
    String name;
    int age;
    int salary;
    private static int nextId = 1;
    private final String departmentName = "Backend Engineers";

    EmployeeLinkList nextEmployee;

    EmployeeLinkList(String name, int age, int salary){
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.id = nextId++;

        this.nextEmployee = null;
    }

    int divideSalary(int x){
        try{
            System.out.println("In Try block. Salary: "+this.salary+" x: "+x);
            int value = this.salary/x;
            return value;
        }catch (Exception e){
            System.out.println("Exception Caught. "+e.getMessage());
            return -1;
        }finally {
            System.out.println("Finally Block");
        }
    }

    public void show() {
        System.out.println("Id: " + this.id
                + ", Name: " + this.name
                + ", Age: " + this.age
                + ", Salary: " + this.salary);
    }

    public void showNextId() {
        System.out.println("Next employee id will be: " + nextId);
    }

    protected void finalize(){
        --nextId;
    }
}


public class Final_Vs_Finally_Vs_Finalize {

    public static void main(String[] args) {

        EmployeeLinkList akshitRoot = new EmployeeLinkList("Akshit", 25, 100);
        EmployeeLinkList ankush = new EmployeeLinkList("Ankush", 24, 1000);
        EmployeeLinkList naudy = new EmployeeLinkList("Naudy", 23,10000);

        akshitRoot.nextEmployee = ankush;
        ankush.nextEmployee = naudy;

        akshitRoot.show();
        ankush.show();
        naudy.show();

        akshitRoot.showNextId();
        ankush.showNextId();
        naudy.showNextId();

        int a = akshitRoot.divideSalary(10);
        System.out.println("Divided Salary a: "+a);
        int b = ankush.divideSalary(0);
        System.out.println("Divided Salary b: "+b);
        int c = naudy.divideSalary(100);
        System.out.println("Divided Salary c: "+c);

        {
            // It is sub block to keep
            // all those interns.
            EmployeeLinkList ansh = new EmployeeLinkList("Ansh", 22, 50);
            EmployeeLinkList shivam = new EmployeeLinkList("Shivam", 21, 50);

            ansh.nextEmployee = shivam;
            shivam.nextEmployee = ansh;

            ansh.show();
            shivam.show();

            ansh.showNextId();
            shivam.showNextId();

            /*
                till now no object is eligible for garbage collector as ansh object is null but
                can still be reachable as shivam.nextEmployee = ansh
             */
            ansh = null;
            /*
                now both objects are eligible for garbage collector. As now both are unreachable.
             */
            shivam = null;

            System.gc();
            System.runFinalization();
        }
        akshitRoot.showNextId();
    }
}
