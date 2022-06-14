package org.example;

import lombok.AllArgsConstructor;

import static org.example.FirstRunnable.CAPACITY;
import static org.example.FirstRunnable.THREAD_NAMES_LIST;

@AllArgsConstructor
public class SecondRunnable implements Runnable {

    private final String name;
    private final Object lock;

    @Override
    public void run() {
        while (THREAD_NAMES_LIST.size() < CAPACITY) {
            synchronized (lock) {
                try {
                    while (THREAD_NAMES_LIST.size() % 2 == 0) {
                        lock.wait();
                    }

                    System.out.println(name);
                    THREAD_NAMES_LIST.add(name);
                    lock.notify();
                } catch (InterruptedException e) {
                    System.out.println(name + " was interrupted");
                }
            }
        }
    }
}