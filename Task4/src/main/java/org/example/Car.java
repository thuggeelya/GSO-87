package org.example;

public class Car extends Thread {

    private final ParkingLot parkingLot;

    public Car(ParkingLot parkingLot) {
        if (parkingLot == null) {
            throw new IllegalArgumentException("Error: null parking lot");
        }

        this.parkingLot = parkingLot;
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
                parkingSpaceNumber = parkingLot.parkCar();
                System.out.printf("-> %s is parking at space [%d]%n", getName(), parkingSpaceNumber);
                delay = (long) (Math.floor(Math.random() * (maxDelay - minDelay)) + minDelay);
                Thread.sleep(delay);
                parkingLot.leaveParkingSpace(parkingSpaceNumber);
                System.out.printf("<- %s has gone from [%d] after %.3f sec%n", getName(), parkingSpaceNumber, (double) delay / 1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("-----------\n" + getName() + " left\n-----------");
    }
}