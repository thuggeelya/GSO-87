package org.example;

public class AppThread extends Thread {

    private final Object lock = 0;

    public Object getLock() {
        return lock;
    }

    @Override
    public void run() {
        System.out.println(getState());

        synchronized (lock) {
            try {
                lock.notify();
                lock.wait();
                Thread.sleep(500);
                lock.notify();
                lock.wait(200);
            } catch (InterruptedException e) {
                System.out.println(getName() + " was interrupted");
            }
        }
    }
}