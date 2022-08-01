package org.example;

import org.junit.Test;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class MyRunnableTest {

    private final File file = new File("src/test/resources/task2.txt");

    @Test
    public void checkTwoNThreadNamesGoOneByOne() throws InterruptedException {
        Queue<MyRunnable> queue = new LinkedList<>();
        List<String> result = new ArrayList<>();
        List<MyRunnable> myRunnableList = new ArrayList<>();

        try (PrintStream printStream = new PrintStream(file)) {
            MyRunnable myRunnable0 = new MyRunnable(0, printStream, queue);
            MyRunnable myRunnable1 = new MyRunnable(1, printStream, queue);
            myRunnableList.add(myRunnable0);
            myRunnableList.add(myRunnable1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Thread.sleep(5000);
        myRunnableList.forEach(MyRunnable::terminate);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                result.add(line);
            }

            file.delete();
        } catch (IOException e) {
            Logger.getGlobal().severe(e.getMessage());
        }

        for (int i = 0; i < result.size() - 1; i++) {
            assertNotEquals(result.get(i), result.get(i + 1));
        }
    }

    @Test
    public void checkRandomNThreadNamesGoOneByOne() throws InterruptedException {
        int nThreads = new Random().nextInt(10) + 3;
        Queue<MyRunnable> queue = new LinkedList<>();
        List<String> result = new ArrayList<>();
        List<MyRunnable> myRunnableList = new ArrayList<>();

        try (PrintStream printStream = new PrintStream(file)) {
            for (int i = 0; i < nThreads; i++) {
                MyRunnable runnable = new MyRunnable(i, printStream, queue);
                myRunnableList.add(runnable);
            }

            myRunnableList.forEach(r -> new Thread(r).start());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Thread.sleep(5000);
        myRunnableList.forEach(MyRunnable::terminate);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                result.add(line);
            }

            file.delete();
        } catch (IOException e) {
            Logger.getGlobal().severe(e.getMessage());
        }

        for (int i = 0; i < result.size() - 1; i++) {
            int iNumber = getNumber(result.get(i));
            assertTrue((iNumber + 1 == getNumber(result.get(i + 1))) || (iNumber + 1 == nThreads));
        }
    }

    private static int getNumber(String threadName) {
        if (threadName.matches(".*\\d*.*")) {
            return Integer.parseInt(threadName.replaceAll("\\D", ""));
        }

        throw new IllegalArgumentException("Cannot get thread number by its name: " + threadName);
    }
}