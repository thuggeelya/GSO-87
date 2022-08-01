package org.example;

import java.io.PrintStream;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class ThreadCounter extends Thread {

    private final int pauseInSeconds;
    private final AtomicInteger time = new AtomicInteger(0);

    private boolean terminate = false;
    private final PrintStream printStream;

    public ThreadCounter(int pauseInSeconds, PrintStream printStream) {
        this.pauseInSeconds = pauseInSeconds;
        setName("Thread-interval-" + pauseInSeconds);
        this.printStream = printStream;
    }

    public void terminate() {
        terminate = true;
    }

    public boolean isTerminate() {
        return terminate;
    }

    @Override
    public void run() {
        while (!terminate) {
            try {
                Thread.sleep(pauseInSeconds * 1000L);

                // для ежесекундного оповещения потока, воспроизводящего сообщение, потоком, отсчитывающим время
                synchronized (ThreadMessage.class) {
                    printStream.println(time.incrementAndGet());
                    ThreadMessage.class.notifyAll();
                }
            } catch (InterruptedException e) {
                Logger.getGlobal().severe("Thread was interrupted: " + getName() + System.lineSeparator() + e);
            }
        }
    }
}