package org.example;

public class Application {

    public static void main(String[] args) throws InterruptedException {
        AppThread appThread = new AppThread();
        appThread.printState();

        synchronized (appThread) {
            appThread.start();
            appThread.printState();
            System.out.println(appThread.getName() + " is wait()ing in main");
            appThread.wait();
            appThread.printState();
            appThread.notify();
            System.out.println(appThread.getName() + " notify()ed in main");
            appThread.printState();
            System.out.println(appThread.getName() + " is wait()ing in main");
            appThread.wait();
            appThread.printState();
            System.out.println(appThread.getName() + " wait()ed in main");
        }

        appThread.join();
        appThread.printState();
    }
}