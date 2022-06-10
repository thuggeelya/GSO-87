package org.example;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class ThreadMessage implements Runnable {

    private final int interval;
    private final String message;
    public static AtomicInteger time = new AtomicInteger(0);

    public ThreadMessage(int n, String message) {
        this.interval = n;
        this.message = message;
        new Thread(this).start();
    }

    synchronized static int incTime() {
        return time.incrementAndGet();
    }

    @Override
    public void run() {
        while (true) {
            try {
                synchronized (ThreadMessage.class) {
                    ThreadMessage.class.wait();

                    if (time.get() % this.interval == 0) {
                        System.out.println(message);
                    }
                }
            } catch (InterruptedException e) {
                Logger.getGlobal().severe("Thread was interrupted: " + e);
            }
        }
    }
}