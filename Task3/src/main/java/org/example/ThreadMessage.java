package org.example;

import java.io.PrintStream;
import java.util.logging.Logger;

public class ThreadMessage extends Terminator {

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

        while (!super.terminate) {
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
                Logger.getLogger(getClass().getName()).severe("Thread was interrupted: " + getName() + System.lineSeparator() + e);
            }
        }
    }
}