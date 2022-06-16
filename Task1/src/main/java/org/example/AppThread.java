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
                System.out.println(getName() + " run: notify()ed in run()");
                System.out.println(getName() + " run: is wait()ing in run()");
                this.wait();
                this.notify();
                System.out.println(getName() + " run: notify()ed in run()");
                System.out.println(getName() + " run: is wait(80)ing in run()");
                this.wait(80);
                System.out.println(getName() + " run: wait(80)ed in run()");
            } catch (InterruptedException e) {
                System.out.println(getName() + " run: was interrupted");
            }
        }
    }
}