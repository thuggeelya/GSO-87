package org.example;

import lombok.SneakyThrows;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Application {

    @SneakyThrows(InterruptedException.class)
    public static void main(String[] args) {
        int capacity = 5;
        int carsCount = 17;
        ExecutorService cars = Executors.newFixedThreadPool(carsCount);
        ParkingLot parkingLot = new ParkingLot(capacity, carsCount);
        parkingLot.start();

        for (int i = 0; i < carsCount; i++) {
            cars.submit(new Car(parkingLot));
            Thread.sleep(500);
        }

        cars.shutdown();
    }
}