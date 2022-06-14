package org.example;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class FirstRunnable implements Runnable {

    private final String name;
    private final Object lock;

    final static int CAPACITY = 2000;
    static final List<String> THREAD_NAMES_LIST = new ArrayList<>(CAPACITY);

    @Override
    public void run() {
        while (THREAD_NAMES_LIST.size() < CAPACITY - 1) {
            synchronized (lock) {
                try {
                    while (THREAD_NAMES_LIST.size() % 2 == 1) {
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