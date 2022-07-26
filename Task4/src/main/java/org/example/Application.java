package org.example;

public class Application {

    public static void main(String[] args) throws InterruptedException {
        int capacity = 5;
        int carsCount = 16;
        ParkingLot parkingLot = new ParkingLot(capacity);
        parkingLot.start();

        try {
            for (int i = 0; i < carsCount; i++) {
                new Car(parkingLot).start();
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Thread.sleep(15_000);
        parkingLot.setClosed();
    }
}