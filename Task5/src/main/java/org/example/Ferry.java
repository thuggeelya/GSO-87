package org.example;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Ferry extends Thread {

    private final AtomicInteger carCount = new AtomicInteger(0);
    private final Semaphore semaphore = new Semaphore(3);

    public void inc() {
        carCount.incrementAndGet();
    }

    public void acquire() throws InterruptedException {
        semaphore.acquire();
    }

    private synchronized void refresh() {
        semaphore.release(3);
    }

    @Override
    public void run() {
        try {
            while (true) {
                while (semaphore.availablePermits() != 0) {
                    sleep(0);
                }

                synchronized (this) {
                    notifyAll();
                }
                System.out.println("\nStart ferry");
                sleep(600);
                System.out.println("Finish ferry\n");
                refresh();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}