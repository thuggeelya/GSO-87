package org.example;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AppThreadTest {

    @Test
    public void checkThreadState() throws InterruptedException {
        AppThread appThread = new AppThread();
        assertEquals(Thread.State.NEW, appThread.getState());
        Object lock = appThread.getLock();
        appThread.start();
        assertEquals(Thread.State.RUNNABLE, appThread.getState());

        synchronized (lock) {
            lock.wait();
            assertEquals(Thread.State.WAITING, appThread.getState());
            lock.notify();
            assertEquals(Thread.State.BLOCKED, appThread.getState());
            lock.wait();
            Thread.sleep(100);
            assertEquals(Thread.State.TIMED_WAITING, appThread.getState());
        }

        appThread.join();
        assertEquals(Thread.State.TERMINATED, appThread.getState());
    }
}
