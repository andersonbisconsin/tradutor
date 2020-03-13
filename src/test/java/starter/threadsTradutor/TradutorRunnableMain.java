package starter.threadsTradutor;


public class TradutorRunnableMain {

    public static void main(String[] args) {
        System.out.println("Executing program...");
        
        // Create a new instance of our class that implements the Runnable interface.
        // This class can be provided as an argument to a Thread instance.
        TradutorRunnableImplementation r = new TradutorRunnableImplementation();
        
        // Create a new Thread instance, provide the task that we want to run
        // (by providing the Runnable as an argument) and give the thread a name.
        // Now we can use Thread.start() to run it!
        Thread thread1 = new Thread(r, "Thread 1");
        thread1.start();
        
        Thread thread2 = new Thread(r, "Thread 2");
        thread2.start();
        
        Thread thread3 = new Thread(r, "Thread 3");
        thread3.start();
        
        Thread thread4 = new Thread(r, "Thread 4");
        thread4.start();
     
        Thread thread5 = new Thread(r, "Thread 5");
        thread5.start();
        
        Thread thread6 = new Thread(r, "Thread 6");
        thread6.start();
        
        Thread thread7 = new Thread(r, "Thread 7");
        thread7.start();
//        
//        Thread thread8 = new Thread(r, "Thread 8");
//        thread8.start();
//     
//        Thread thread9 = new Thread(r, "Thread 9");
//        thread9.start();
    }
}