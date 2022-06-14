package org.example;

import java.util.ArrayList;
import java.util.List;

public class AppThread extends Thread {

    private final Object lock = new Object();
    private final List<Thread.State> stateList = new ArrayList<>(6);

    public AppThread() {
        setName("Task 1 Thread");
    }

    public Object getLock() {
        return lock;
    }

    public List<State> getStateList() {
        return stateList;
    }

    public void clearStateList() {
        stateList.clear();
    }

    public void printState() {
        State state = getState();
        stateList.add(state);
        System.out.println(state);
    }

    @Override
    public void run() {
        synchronized (lock) {
            try {
                lock.notify();
                lock.wait();
                Thread.sleep(80);
                lock.notify();
                lock.wait(80);
            } catch (InterruptedException e) {
                System.out.println(getName() + " was interrupted");
            }
        }
    }
}