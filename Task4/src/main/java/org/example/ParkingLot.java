package org.example;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.util.Arrays;

public class ParkingLot extends Thread {

    @Setter
    private long parkingDelay = 500;
    @Getter
    private final Boolean[] parkingQueue;
    private final int carsCount;

    public ParkingLot(int capacity, int carsCount) {
        this.parkingQueue = new Boolean[capacity];
        this.carsCount = carsCount;
        setName("Parking Lot");

        for (int i = 0; i < capacity; i++) {
            parkingQueue[i] = false;
        }
    }

    @Override
    public void run() {
        long workTime = Car.MAX_ATTEMPTS_TO_PARK * carsCount;

        while ((workTime >= 0) || (Arrays.stream(parkingQueue).anyMatch(b -> b))) {
            try {
                Thread.sleep(parkingDelay);

                synchronized (parkingQueue) {
                    if (Arrays.stream(parkingQueue).anyMatch(b -> !b)) {
                        parkingQueue.notify();
                        workTime--;
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException();
            }
        }

        System.out.println("closing parking lot\n-------------------");
    }

    @SneakyThrows(NullPointerException.class)
    public int parkCar(Car car) {
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