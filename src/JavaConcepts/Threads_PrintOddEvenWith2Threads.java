package JavaConcepts;

/*
    Points To Remember:

1) Wait() method releases lock during Synchronization. Sleep() method does not release the lock on object during
   Synchronization. Wait() should be called only from Synchronized context. There is no need to call sleep()
   from Synchronized context.

2)
    a) Thread.sleep(100) -> sleep the main thread for 100 millSec.
    b) Thread.wait() -> will give compiler error -> wait is in context of running thread not in context of main thread.
*/
class PrintNum{

    private static PrintNum obj = null;
    int upTo = 20;
    int start = 1;
    int n = start;

    private PrintNum(){

    }

    public static PrintNum getInstance(){
        if(PrintNum.obj == null)
            PrintNum.obj = new PrintNum();
        return PrintNum.obj;
    }

    public void printOdd_Sleep() {
        while(n<upTo){
            if(n%2 == 0){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else{
                System.out.println("Odd: "+n);
                n++;
            }
        }
    }

    public void printEven_Sleep() {
        while(n<upTo){
            if(n%2 == 1){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else{
                System.out.println("Even: "+n);
                n++;
            }
        }
    }


    public synchronized void printOdd_Wait() {
        while(n<=upTo){
            if(n%2 == 0){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else{
                System.out.println("Odd: "+n);
                n++;
                notify();
            }
        }
    }

    public synchronized void printEven_Wait() {
        while(n<=upTo){
            if(n%2 == 1){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else{
                System.out.println("Even: "+n);
                n++;
                notify();
            }
        }
    }
}

public class Threads_PrintOddEvenWith2Threads {

    public static void main(String[] args){
        PrintNum printNum = PrintNum.getInstance();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                printNum.printOdd_Wait();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                printNum.printEven_Wait();
            }
        });

        t1.start();
        t2.start();
    }
}