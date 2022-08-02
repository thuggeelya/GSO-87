package org.example;

import java.util.concurrent.Semaphore;

public class ParkingLot {
    private final boolean[] parkingQueue;
    private boolean closed = false;
    private final Semaphore semaphore;

    public ParkingLot(int capacity) {
        if (capacity == 0) {
            throw new IllegalArgumentException("Error: empty parking lot");
        }

        parkingQueue = new boolean[capacity];

        for (int i = 0; i < capacity; i++) {
            parkingQueue[i] = false;
        }

        semaphore = new Semaphore(capacity, true);
    }

    public boolean isOpened() {
        return !closed;
    }

    public void setClosed() {
        closed = true;
    }

    public int parkCar() {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        synchronized (parkingQueue) {
            for (int i = 0; i < parkingQueue.length; i++) {
                if (!parkingQueue[i]) {
                    parkingQueue[i] = true;
                    return i;
                }
            }
        }

        throw new RuntimeException("Only one car can take one parking space");
    }

    public void leaveParkingSpace(int parkingSpaceNumber) {
        synchronized (parkingQueue) {
            if (!parkingQueue[parkingSpaceNumber]) {
                throw new RuntimeException("Car cannot leave the [" +
                        parkingSpaceNumber + "] space cause it's already empty");
            }

            parkingQueue[parkingSpaceNumber] = false;
            semaphore.release();
        }
    }
}