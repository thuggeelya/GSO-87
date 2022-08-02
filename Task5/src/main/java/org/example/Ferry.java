package org.example;

import java.io.PrintStream;
import java.util.Queue;

public class Ferry extends Thread {

    private final Queue<Car> sharedQueue;
    private final PrintStream printStream;
    private boolean terminate = false;

    public Ferry(Queue<Car> sharedQueue, PrintStream printStream) {
        this.sharedQueue = sharedQueue;
        this.printStream = printStream;
        setName("Ferry");
    }

    public void stopFerry() {
        terminate = true;
    }

    public boolean isRunning() {
        return !terminate;
    }

    @Override
    public void run() {
        try {
            while (!terminate) {
                System.out.printf("________________%nWaiting for cars%n");

                for (int i = 0; i < 3; i++) {
                    synchronized (sharedQueue) {
                        sharedQueue.wait();

                        if (sharedQueue.peek() != null) {
                            printStream.printf("%d. %s%n", i + 1, sharedQueue.poll().getName());
                        }
                    }
                }

                System.out.println("Start ferry");
                sleep(600);
                System.out.printf("Finish ferry%n------------%n");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}