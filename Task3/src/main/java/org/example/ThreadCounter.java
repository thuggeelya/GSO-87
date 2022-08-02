package org.example;

import java.io.PrintStream;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class ThreadCounter extends Terminator {

    private final int pauseInSeconds;
    private final AtomicInteger time = new AtomicInteger(0);
    private final PrintStream printStream;

    public ThreadCounter(int pauseInSeconds, PrintStream printStream) {
        this.pauseInSeconds = pauseInSeconds;
        setName("Thread-interval-" + pauseInSeconds);
        this.printStream = printStream;
    }

    @Override
    public void run() {
        while (!super.terminate) {
            try {
                sleep(pauseInSeconds * 1000L);

                // для ежесекундного оповещения потока, воспроизводящего сообщение, потоком, отсчитывающим время
                synchronized (ThreadMessage.class) {
                    printStream.println(time.incrementAndGet());
                    ThreadMessage.class.notifyAll();
                }
            } catch (InterruptedException e) {
                Logger.getLogger(getClass().getName()).severe("Thread was interrupted: " + getName() + System.lineSeparator() + e);
            }
        }
    }
}