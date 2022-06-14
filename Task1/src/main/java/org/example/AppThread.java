package org.example;

import java.util.ArrayList;
import java.util.List;

public class AppThread extends Thread {

    private final List<Thread.State> stateList = new ArrayList<>(6);

    public AppThread() {
        setName("Task 1 Thread");
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
        synchronized (this) {
            try {
                this.notify();
                this.wait();
                Thread.sleep(80);
                this.notify();
                this.wait(80);
            } catch (InterruptedException e) {
                System.out.println(getName() + " was interrupted");
            }
        }
    }
}