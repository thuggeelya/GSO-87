package org.example;

import lombok.SneakyThrows;

public class Application {

    @SneakyThrows(value = InterruptedException.class)
    public static void main(String[] args) {
        int capacity = 5;
        ParkingLot parkingLot = new ParkingLot(capacity);
        new Thread(parkingLot, "Parking lot").start();

        for (int i = 0; i < 20; i++) {
            new Thread(new Car(parkingLot.getParkingQueue()), "Car-" + i).start();
            Thread.sleep(500);
        }
    }
}