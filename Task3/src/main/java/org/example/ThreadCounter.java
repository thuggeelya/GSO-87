package org.example;

import java.util.logging.Logger;

public class ThreadCounter implements Runnable {

    private final int interval;

    public ThreadCounter(int n) {
        this.interval = n;
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(interval * 1000L);

                // ежесекундное оповещение потока, воспроизводящего сообщение
                synchronized (ThreadMessage.class) {
                    System.out.println(ThreadMessage.incTime());
                    ThreadMessage.class.notifyAll();
                }
            } catch (InterruptedException e) {
                Logger.getGlobal().severe("Thread was interrupted: " + e);
            }
        }
    }
}
