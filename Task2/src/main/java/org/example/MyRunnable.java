package org.example;

import java.io.PrintStream;
import java.util.Queue;

public class MyRunnable implements Runnable {

    private final String name;
    private final PrintStream printStream;
    private final int number;
    private final int nThreads;
    private final Queue<Integer> queue;
    public static boolean terminate = false;

    public MyRunnable(int number, int nThreads, PrintStream printStream, Queue<Integer> queue) {
        this.name = "Thread-" + number;
        this.number = number;
        this.printStream = printStream;
        this.nThreads = nThreads;
        this.queue = queue;
    }

    public static void terminate() {
        terminate = true;
    }

    @Override
    public void run() {
        try {
            while (!terminate) {
                synchronized (queue) {
                    int el = 0;

                    if (queue.peek() != null) {
                        el = queue.poll();
                    }

                    if (el == number) {
                        printStream.println(name);
                        el++;
                    }

                    queue.add(el % nThreads);
                    queue.notify();
                    queue.wait(1000);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}