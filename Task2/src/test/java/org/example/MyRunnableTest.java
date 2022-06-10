package org.example;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Scanner;

public class MyRunnableTest {

    @Test
    public void checkThreadNamesGoOneByOne() {
        Object lock = 0;
        Scanner scanner = new Scanner(System.in);

        try {
            Thread firstThread = new Thread(new MyRunnable("First", lock));
            firstThread.start();
            Thread secondThread = new Thread(new MyRunnable("Second", lock));
            secondThread.start();
            Thread.sleep(1000);
            secondThread.interrupt();
        } catch (InterruptedException e) {
            for (int i = 0; i < 50; i++) {
                assertNotEquals(scanner.nextLine(), scanner.nextLine());
            }
        }
    }
}
