package org.example;

import org.junit.Test;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

import static org.example.MyRunnable.terminate;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class MyRunnableTest {

    private final File file = new File("src/test/resources/task2.txt");

    @Test
    public void checkTwoNThreadNamesGoOneByOne() throws InterruptedException {
        Queue<Integer> queue = new LinkedList<>();
        List<String> result = new ArrayList<>();

        try (PrintStream printStream = new PrintStream(file)) {
            new Thread(new MyRunnable(0, 2, printStream, queue));
            new Thread(new MyRunnable(1, 2, printStream, queue));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Thread.sleep(5000);
        terminate();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                result.add(line);
            }

            file.delete();
        } catch (IOException e) {
            Logger.getGlobal().severe(e.getMessage());
        }

        int resultSize = result.size();

        for (int i = 0; i < resultSize - 1; i++) {
            for (int j = i + 1; j < resultSize; j++) {
                assertNotEquals(result.get(i), result.get(j));
            }
        }
    }

    @Test
    public void checkRandomNThreadNamesGoOneByOne() throws InterruptedException {
        int nThreads = new Random().nextInt();
        Queue<Integer> queue = new LinkedList<>();
        List<String> result = new ArrayList<>();

        try (PrintStream printStream = new PrintStream(file)) {
            for (int i = 0; i < nThreads; i++) {
                new Thread(new MyRunnable(i, nThreads, printStream, queue));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Thread.sleep(5000);
        terminate();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                result.add(line);
            }

            file.delete();
        } catch (IOException e) {
            Logger.getGlobal().severe(e.getMessage());
        }

        int resultSize = result.size();

        for (int i = 0; i < resultSize - 1; i++) {
            for (int j = i + 1; j < resultSize; j++) {
                assertEquals(getNumber(result.get(i)) + 1, getNumber(result.get(j)));
            }
        }
    }

    private static int getNumber(String threadName) {
        if (threadName.contains("\\d")) {
            return Integer.parseInt(threadName.replaceAll("\\D", ""));
        }

        throw new IllegalArgumentException("Cannot get thread number by its name ..");
    }
}