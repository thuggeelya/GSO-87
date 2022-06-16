package org.example;

import org.junit.Test;

public class ParkingLotTest {

    @Test
    public void checkParkingSpaces() throws InterruptedException {
        int capacity = 5;
        ParkingLot parkingLot = new ParkingLot(capacity);
        new Thread(parkingLot, "Parking lot").start();

        for (int i = 0; i < 20; i++) {
            new Thread(new Car(parkingLot.getParkingQueue()), "Car-" + i).start();
            Thread.sleep(500);
        }
    }
}