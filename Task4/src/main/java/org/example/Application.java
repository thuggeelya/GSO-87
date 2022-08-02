package org.example;

public class Application {

    public static void main(String[] args) {
        int capacity = 5;
        int carsCount = Integer.parseInt(args[0]);
        ParkingLot parkingLot = new ParkingLot(capacity);

        try {
            for (int i = 0; i < carsCount; i++) {
                new Car(parkingLot).start();
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}