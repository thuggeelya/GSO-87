package org.example;

import java.util.Queue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Car extends Thread {

    private final Queue<Car> sharedQueue;
    private final CyclicBarrier cyclicBarrier;

    public Car(Queue<Car> sharedQueue, CyclicBarrier cyclicBarrier) {
        this.sharedQueue = sharedQueue;
        this.cyclicBarrier = cyclicBarrier;
        setName(getName().replace("Thread", "Car"));
    }

    @Override
    public void run() {
        try {
            synchronized (sharedQueue) {
                sharedQueue.add(this);
                sharedQueue.notify();
            }

            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
    }
}