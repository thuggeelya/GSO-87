package org.example;

import java.util.concurrent.Semaphore;

public class ParkingLot {
    private final Boolean[] parkingQueue;
    private boolean closed = false;
    private final Semaphore semaphore;

    public ParkingLot(int capacity) {
        if (capacity == 0) {
            throw new IllegalArgumentException("Error: empty parking lot");
        }

        parkingQueue = new Boolean[capacity];

        for (int i = 0; i < capacity; i++) {
            parkingQueue[i] = false;
        }

        semaphore = new Semaphore(capacity, true);
    }

    public Boolean[] getParkingQueue() {
        return parkingQueue;
    }

    public boolean isOpened() {
        return !closed;
    }

    public void setClosed() {
        closed = true;
    }

    public int parkCar(Car car) {
        if (car == null) {
            throw new IllegalArgumentException("Cannot park the null car");
        }

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
                throw new ArrayIndexOutOfBoundsException("Car cannot leave the [" +
                        parkingSpaceNumber + "] space cause it's already empty");
            }

            parkingQueue[parkingSpaceNumber] = false;
            semaphore.release();
        }
    }
}