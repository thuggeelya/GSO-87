package org.example;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MyRunnableTest {

    @Test
    public void checkThreadNamesGoOneByOne() {
        Object lock = new Object();
        List<String> threadNamesList = new ArrayList<>(2000);

        for (int i = 0; i < 1000; i++) {
            threadNamesList.add("First");
            threadNamesList.add("Second");
        }

        for (int i = 0; i < 500; i++) {
            FirstRunnable.THREAD_NAMES_LIST.clear();
            Thread firstThread = new Thread(new FirstRunnable("First", lock));
            firstThread.start();
            Thread secondThread = new Thread(new SecondRunnable("Second", lock));
            secondThread.start();

            while (secondThread.getState() != Thread.State.TERMINATED) {
                try {
                    //noinspection BusyWait
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            assertEquals(threadNamesList, FirstRunnable.THREAD_NAMES_LIST);
        }
    }
}