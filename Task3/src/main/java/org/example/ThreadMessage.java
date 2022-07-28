package org.example;

import java.io.PrintStream;
import java.util.logging.Logger;

import static org.example.ThreadCounter.terminate;

public class ThreadMessage extends Thread {

    private final int pauseInSeconds;
    private final String message;
    private final PrintStream printStream;

    public ThreadMessage(int pauseInSeconds, String message, PrintStream printStream) {
        this.pauseInSeconds = pauseInSeconds;
        this.message = message;
        setName("Thread-" + message);
        this.printStream = printStream;
    }

    @Override
    public void run() {
        int timeTick = 0;

        while (!terminate) {
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
                Logger.getGlobal().severe("Thread was interrupted: " + e);
            }
        }
    }
}