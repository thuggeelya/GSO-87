package org.example;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MyRunnable implements Runnable {

    private final String name;
    private final Object lock;

    @Override
    public void run() {
        //noinspection InfiniteLoopStatement
        while (true) {
            synchronized (lock) {
                try {
                    lock.notify();
                    lock.wait();
                    System.out.println(this.name);
                } catch (InterruptedException e) {
                    System.out.println(this.name + " was interrupted");
                }
            }
        }
    }
}
