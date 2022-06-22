package org.example;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MyRunnableTest {

    @Test
    public void checkThreadNamesGoOneByOne() {
        Object lock = new Object();
        String nameFirst = "First";
        String nameSecond = "Second";
        List<String> threadNamesList = new ArrayList<>(2000);

        for (int i = 0; i < 1000; i++) {
            threadNamesList.add(nameFirst);
            threadNamesList.add(nameSecond);
        }

        for (int i = 0; i < 50; i++) {
            MyRunnable.NAMES_LIST.clear();
            Thread firstThread = new Thread(new MyRunnable(nameFirst, lock));
            Thread secondThread = new Thread(new MyRunnable(nameSecond, lock));
            firstThread.start();
            secondThread.start();

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            assertEquals(threadNamesList, MyRunnable.NAMES_LIST);
        }
    }
}