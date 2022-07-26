package org.example;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class ParkingLot extends Thread {

    private long parkingDelay = 500;
    private final Boolean[] parkingQueue;

    public final AtomicBoolean closed = new AtomicBoolean(false);

    public ParkingLot(int capacity) {
        this.parkingQueue = new Boolean[capacity];
        setName("Parking Lot");

        for (int i = 0; i < capacity; i++) {
            parkingQueue[i] = false;
        }
    }

    public void setParkingDelay(long parkingDelay) {
        this.parkingDelay = parkingDelay;
    }

    public Boolean[] getParkingQueue() {
        return parkingQueue;
    }

    public boolean isOpened() {
        return !closed.get();
    }

    public void setClosed() {
        closed.set(true);
    }

    @Override
    public void run() {
        while (isOpened()) {
            try {
                Thread.sleep(parkingDelay);

                synchronized (parkingQueue) {
                    if (Arrays.stream(parkingQueue).anyMatch(b -> !b)) {
                        parkingQueue.notify();
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException();
            }
        }

        System.out.println("\nparking lot is closing .. drive away, please\n-------------------");

        while (Arrays.stream(parkingQueue).anyMatch(b -> b)) {
            try {
                sleep(0);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("\nparking lot is closed\n");
    }

    public int parkCar(Car car) {
        if (car == null) {
            throw new IllegalArgumentException("Cannot park the null car");
        }

        for (int i = 0; i < parkingQueue.length; i++) {
            if (!parkingQueue[i]) {
                parkingQueue[i] = true;
                System.out.println("-> " + car.getName() + " is parking at space " + i);
                return i;
            }
        }

        throw new RuntimeException("Only one car can take one parking space");
    }
}