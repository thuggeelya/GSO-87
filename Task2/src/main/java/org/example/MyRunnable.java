package org.example;

import java.util.ArrayList;
import java.util.List;

public class MyRunnable implements Runnable {

    private final String name;
    private final Object lock;
    private int iterationCount = 1000;
    static final List<String> NAMES_LIST = new ArrayList<>();

    public MyRunnable(String name, Object lock) {
        this.name = name;
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            while (iterationCount > 0) {
                synchronized (lock) {
                    System.out.println(name);
                    NAMES_LIST.add(name);
                    lock.notify();
                    lock.wait();
                }

                iterationCount--;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}