package org.example;

public class Application {

    public static void main(String[] args) throws InterruptedException {
        AppThread appThread = new AppThread();
        appThread.printState();
        Object lock = appThread.getLock();

        synchronized (lock) {
            appThread.start();
            appThread.printState();
            lock.wait();
            appThread.printState();
            lock.notify();
            appThread.printState();
            lock.wait();
            Thread.sleep(40);
            appThread.printState();
        }

        appThread.join();
        appThread.printState();
    }
}