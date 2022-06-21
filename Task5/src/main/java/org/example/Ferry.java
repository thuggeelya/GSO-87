package org.example;

import java.util.Queue;

public class Ferry extends Thread {

    private final Queue<Car> sharedQueue;

    public Ferry(Queue<Car> sharedQueue) {
        this.sharedQueue = sharedQueue;
        setName("Ferry");
    }

    @Override
    public void run() {
        try {
            byte currentSize = 0;

            while (true) {
                synchronized (sharedQueue) {
                    System.out.println("waiting for cars");

                    while (currentSize < 3) {
                        sharedQueue.wait();

                        if (!sharedQueue.isEmpty()) {
                            System.out.print(sharedQueue.poll().getName() + ".. ");
                            currentSize++;
                        }
                    }

                    System.out.println("\nStart ferry");
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