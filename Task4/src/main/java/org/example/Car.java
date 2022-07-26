package org.example;

public class Car extends Thread {

    private final Boolean[] parkingQueue;
    private final ParkingLot parkingLot;

    public Car(ParkingLot parkingLot) {
        if (parkingLot == null) {
            throw new IllegalArgumentException("Null parking lot");
        }

        this.parkingLot = parkingLot;
        this.parkingQueue = parkingLot.getParkingQueue();
        setName(getName().replace("Thread", "Car"));
    }

    @Override
    public void run() {
        long maxDelay = 8000;
        long minDelay = 2000;
        long delay;
        int parkingSpaceNumber;

        while (parkingLot.isOpened()) {
            try {
                synchronized (parkingQueue) {
                    parkingQueue.wait(maxDelay * parkingQueue.length);

                    if (!parkingLot.isOpened()) {
                        continue;
                    }

                    parkingSpaceNumber = parkingLot.parkCar(this);
                }

                delay = (long) (Math.floor(Math.random() * (maxDelay - minDelay)) + minDelay);
                Thread.sleep(delay);
                leaveParkingSpaceAfterDelay(parkingSpaceNumber, delay);
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