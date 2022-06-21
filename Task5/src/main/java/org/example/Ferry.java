package org.example;

import lombok.Getter;

import java.util.LinkedList;
import java.util.Queue;

public class Ferry extends Thread {

    private final Queue<Car> sharedQueue;
    private final int carsCount;
    @Getter
    private final Queue<Car> currentFerry = new LinkedList<>();
    @Getter
    private boolean isAlwaysTripleCapacious;

    public Ferry(Queue<Car> sharedQueue, int carsCount) {
        this.sharedQueue = sharedQueue;
        this.carsCount = carsCount;
        setName("Ferry");
        isAlwaysTripleCapacious = true;
    }

    @Override
    public void run() {
        try {
            byte currentSize = 0;
            byte currentFerryCarsCount;
            int ferries = 0;

            while (ferries < carsCount / 3) {
                synchronized (sharedQueue) {
                    System.out.println("waiting for cars");
                    currentFerryCarsCount = 0;

                    while (currentSize < 3) {
                        sharedQueue.wait();

                        if (!sharedQueue.isEmpty()) {
                            Car car = sharedQueue.poll();
                            currentFerryCarsCount++;
                            System.out.print(car.getName() + ".. ");
                            currentSize++;
                        }
                    }

                    isAlwaysTripleCapacious = isAlwaysTripleCapacious && (currentFerryCarsCount == 3);
                    System.out.println("\nStart ferry");
                    sleep(600);
                    System.out.println("Finish ferry\n");
                    currentSize = 0;
                    ferries++;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}