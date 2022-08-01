package org.example;

import java.io.PrintStream;
import java.util.logging.Logger;

public class ThreadMessage extends Thread {

    private final int pauseInSeconds;
    private final String message;
    private final PrintStream printStream;
    private final ThreadCounter counter;

    public ThreadMessage(int pauseInSeconds, String message, PrintStream printStream, ThreadCounter counter) {
        this.pauseInSeconds = pauseInSeconds;
        this.message = message;
        setName("Thread-" + message);
        this.printStream = printStream;
        this.counter = counter;
    }

    @Override
    public void run() {
        int timeTick = 0;

        while (!counter.isTerminate()) {
            try {
                synchronized (ThreadMessage.class) {
                    ThreadMessage.class.wait();
                    timeTick++;

                    if (timeTick == pauseInSeconds) {
                        printStream.println(message);
                        timeTick = 0;
                    }
                }
            } catch (InterruptedException e) {
                Logger.getGlobal().severe("Thread was interrupted: " + getName() + System.lineSeparator() + e);
            }
        }
    }
}