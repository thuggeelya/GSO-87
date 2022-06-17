package org.example;

import lombok.SneakyThrows;

public class Car extends Thread {

    /**
     * Particular car parks at the {@link ParkingLot} the certain value of times and then stops doing it.
     */
    public static final long MAX_ATTEMPTS_TO_PARK = 5;

    private final Boolean[] parkingQueue;
    private final ParkingLot parkingLot;

    @SneakyThrows(NullPointerException.class)
    public Car(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
        this.parkingQueue = parkingLot.getParkingQueue();
        setName(getName().replace("Thread", "Car"));
    }

    @Override
    public void run() {
        long maxDelay = 8000;
        long minDelay = 2000;
        long attempts = 0;
        long delay;
        int parkingSpaceNumber;

        while (attempts < MAX_ATTEMPTS_TO_PARK) {
            try {
                synchronized (parkingQueue) {
                    parkingQueue.wait();
                    parkingSpaceNumber = parkingLot.parkCar(this);
                }

                delay = (long) (Math.floor(Math.random() * (maxDelay - minDelay)) + minDelay);
                Thread.sleep(delay);
                leaveParkingSpaceAfterDelay(parkingSpaceNumber, delay);
                attempts++;
            } catch (InterruptedException e) {
                throw new RuntimeException();
            }
        }

        System.out.println("-----------\n" + getName() + " left\n-----------");
    }

    public void leaveParkingSpaceAfterDelay(int parkingSpaceNumber, long delay) {
        synchronized (parkingQueue) {
            if (!parkingQueue[parkingSpaceNumber]) {
                throw new ArrayIndexOutOfBoundsException(getName() + " cannot leave the " + parkingSpaceNumber + " space cause it's already empty");
            }

            parkingQueue[parkingSpaceNumber] = false;
            System.out.println("<- " + getName() + " has gone from " + parkingSpaceNumber + " after " + (double) delay / 1000 + " sec");
        }
    }
}