package org.example;

import java.util.Queue;

public class Ferry extends Thread {

    private final Queue<Car> sharedQueue;
    private boolean terminate = false;

    public Ferry(Queue<Car> sharedQueue) {
        this.sharedQueue = sharedQueue;
        setName("Ferry");
    }

    public void stopFerry() {
        terminate = true;
    }

    @Override
    public void run() {
        try {
            byte currentSize = 0;

            while (!terminate) {
                System.out.println("waiting for cars");

                synchronized (sharedQueue) {
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
                    currentSize = 0;
                    System.out.println("Finish ferry\n");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}