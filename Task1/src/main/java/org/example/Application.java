package org.example;

public class Application {

    public static void main(String[] args) throws InterruptedException {
        AppThread appThread = new AppThread();
        System.out.println(appThread.getState());
        Object lock = appThread.getLock();
        appThread.start();
        System.out.println(appThread.getState());

        synchronized (lock) {
            lock.wait();
            System.out.println(appThread.getState());
            lock.notify();
            System.out.println(appThread.getState());
            lock.wait();
            Thread.sleep(100);
            System.out.println(appThread.getState());
        }

        appThread.join();
        System.out.println(appThread.getState());
    }
}