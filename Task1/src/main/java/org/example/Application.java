package org.example;

public class Application {

    public static void main(String[] args) throws InterruptedException {
        AppThread appThread = new AppThread();
        appThread.printState();

        synchronized (appThread) {
            appThread.start();
            appThread.printState();
            appThread.wait();
            appThread.printState();
            appThread.notify();
            appThread.printState();
            appThread.wait();
            Thread.sleep(40);
            appThread.printState();
        }

        appThread.join();
        appThread.printState();
    }
}