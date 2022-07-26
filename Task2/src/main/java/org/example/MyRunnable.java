package org.example;

import java.io.PrintStream;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class MyRunnable implements Runnable {

    private final String name;
    private final Object lock;
    private final PrintStream printStream;
    private final int number;
    private final int nThreads;
    public static AtomicInteger currentThreadNumber = new AtomicInteger(0);
    public static AtomicBoolean terminate = new AtomicBoolean(false);

    public MyRunnable(int number, int nThreads, Object lock, PrintStream printStream) {
        this.name = "Thread-" + number;
        this.number = number;
        this.lock = lock;
        this.printStream = printStream;
        this.nThreads = nThreads;
    }

    public static void terminate() {
        terminate.set(true);
    }

    @Override
    public void run() {
        try {
            while (!terminate.get()) {
                synchronized (lock) {
                    if (currentThreadNumber.get() == number) {
                        printStream.println(name);
                        currentThreadNumber.set(currentThreadNumber.incrementAndGet() % nThreads);
                    }

                    lock.notify();
                    lock.wait(1000);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}