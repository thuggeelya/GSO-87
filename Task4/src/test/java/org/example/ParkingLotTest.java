package org.example;

import org.junit.Test;
import org.junit.function.ThrowingRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;

public class ParkingLotTest {

    private ParkingLot openCloseAndGetParkingLot(int capacity, int carsCount) throws InterruptedException {
        ExecutorService cars = Executors.newFixedThreadPool(carsCount);
        List<Future<?>> compilationList = new ArrayList<>();
        ParkingLot parkingLot = new ParkingLot(capacity, carsCount);
        parkingLot.setParkingDelay(1);
        parkingLot.start();

        for (int i = 0; i < carsCount; i++) {
            compilationList.add(cars.submit(new Car(parkingLot)));
            Thread.sleep(500);
        }

        while (compilationList.stream().anyMatch(f -> !f.isDone())) {
            Thread.sleep(1);
        }

        cars.shutdown();
        return parkingLot;
    }

    @Test
    public void checkNoLeavingEmptyParkingLot() throws InterruptedException {
        int capacity = 3;
        int carsCount = 3;
        ParkingLot parkingLot = openCloseAndGetParkingLot(capacity, carsCount);
        assertFalse("Parking lot is not empty", Arrays.stream(parkingLot.getParkingQueue()).anyMatch(b -> b));

        for (int i = 0; i < capacity; i++) {
            int parkingSpaceNumber = i;
            ThrowingRunnable leaveEmptyParkingLotFunction =
                    () -> new Car(parkingLot).leaveParkingSpaceAfterDelay(parkingSpaceNumber, 0);
            assertThrows("You cannot leave one parking space twice", ArrayIndexOutOfBoundsException.class, leaveEmptyParkingLotFunction);
        }
    }
}