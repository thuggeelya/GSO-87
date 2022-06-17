package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.State.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppThreadTest {

    @Test
    public void checkThreadStates() throws InterruptedException {
        List<Thread.State> stateList = new ArrayList<>(6);
        stateList.add(NEW);
        stateList.add(RUNNABLE);
        stateList.add(WAITING);
        stateList.add(BLOCKED);
        stateList.add(TIMED_WAITING);
        stateList.add(TERMINATED);

        for (int i = 0; i < 200; i++) {
            tryOutMultipleState(stateList);
        }
    }

    private void tryOutMultipleState(List<Thread.State> stateList) throws InterruptedException {
        AppThread appThread = new AppThread();
        appThread.printState();

        synchronized (appThread) {
            appThread.start();
            appThread.printState();
            appThread.wait();
            appThread.printState();
            appThread.notify();
            appThread.printState();
            appThread.wait();
//            Thread.sleep(40);
            appThread.printState();
        }

        appThread.join();
        appThread.printState();
        assertEquals(stateList, appThread.getStateList());
    }
}