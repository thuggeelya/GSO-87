package org.example;

import java.util.Queue;

public class Car extends Thread {

    private final Queue<Car> sharedQueue;

    public Car(Queue<Car> sharedQueue) {
        this.sharedQueue = sharedQueue;
        setName(getName().replace("Thread", "Car"));
    }

    @Override
    public void run() {
        synchronized (sharedQueue) {
            sharedQueue.add(this);
            sharedQueue.notify();
        }
    }
}