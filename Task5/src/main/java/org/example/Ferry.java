package org.example;

import lombok.AllArgsConstructor;

import java.util.Objects;
import java.util.Queue;

@AllArgsConstructor
public class Ferry extends Thread {

    private final Queue<Car> sharedQueue;

    @Override
    public void run() {
        try {
            int currentSize = 0;

            while (true) {
                synchronized (sharedQueue) {
                    System.out.println("waiting for cars");
                    while (currentSize < 3) {
                        sharedQueue.wait();

                        if (!sharedQueue.isEmpty()) {
                            System.out.print(Objects.requireNonNull(sharedQueue.poll()).getName() + ".. ");
                            currentSize++;
                        }
                    }

                    System.out.println("\nStart ferry");
                    sleep(600);
                    System.out.println("Finish ferry\n");
                    currentSize = 0;
                    sharedQueue.notifyAll();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}