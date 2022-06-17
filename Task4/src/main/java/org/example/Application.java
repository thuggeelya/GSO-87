package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Application {

    public static void main(String[] args) {
        int capacity = 5;
        int carsCount = 17;
        ExecutorService cars = Executors.newFixedThreadPool(carsCount);
        ParkingLot parkingLot = new ParkingLot(capacity, carsCount);
        parkingLot.start();

        try {
            for (int i = 0; i < carsCount; i++) {
                cars.submit(new Car(parkingLot));
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        cars.shutdown();
    }
}