package org.example;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Ferry extends Thread {

    private final Queue<Car> sharedQueue;
    public static final AtomicBoolean terminate = new AtomicBoolean(false);

    public Ferry(Queue<Car> sharedQueue) {
        this.sharedQueue = sharedQueue;
        setName("Ferry");
    }

    public void stopFerry() {
        terminate.set(true);
    }

    @Override
    public void run() {
        try {
            byte currentSize = 0;

            while (!terminate.get()) {
                synchronized (sharedQueue) {
                    System.out.println("waiting for cars");

                    while (currentSize < 3) {
                        sharedQueue.wait();

                        if (!sharedQueue.isEmpty()) {
                            Car car = sharedQueue.poll();
                            System.out.println(car.getName());
                            currentSize++;
                        }
                    }

                    System.out.println("Start ferry");
                    sleep(600);
                    System.out.println("Finish ferry\n");
                    currentSize = 0;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}