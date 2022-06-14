package org.example;

import java.util.logging.Logger;

public class ThreadCounter implements Runnable {

    private final int interval;

    public ThreadCounter(int interval) {
        this.interval = interval;
        Thread thread = new Thread(this);
        thread.setName("Thread: interval " + interval);
        thread.start();
    }

    @Override
    public void run() {
        //noinspection InfiniteLoopStatement
        while (true) {
            try {
                //noinspection BusyWait
                Thread.sleep(interval * 1000L);

                // для ежесекундного оповещения потока, воспроизводящего сообщение, потоком, отсчитывающим время
                synchronized (ThreadMessage.class) {
                    System.out.println(ThreadMessage.incrementTime());
                    ThreadMessage.class.notifyAll();
                }
            } catch (InterruptedException e) {
                Logger.getGlobal().severe("Thread was interrupted: " + e);
            }
        }
    }
}