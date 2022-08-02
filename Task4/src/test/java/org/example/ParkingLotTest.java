package org.example;

import org.junit.Test;
import org.junit.function.ThrowingRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.Assert.assertThrows;

public class ParkingLotTest {

    private ParkingLot openCloseAndGetParkingLot(int capacity, int carsCount) throws InterruptedException, ExecutionException {
        ExecutorService cars = Executors.newFixedThreadPool(carsCount);
        List<Future<?>> compilationList = new ArrayList<>();
        ParkingLot parkingLot = new ParkingLot(capacity);

        for (int i = 0; i < carsCount; i++) {
            compilationList.add(cars.submit(new Car(parkingLot)));
            Thread.sleep(500);
        }

        parkingLot.setClosed();

        for (Future<?> future : compilationList) {
            future.get();
        }

        cars.shutdown();
        return parkingLot;
    }

    @Test
    public void checkNoLeavingEmptyParkingLot() throws InterruptedException, ExecutionException {
        int capacity = 10;
        int carsCount = 15;
        ParkingLot parkingLot = openCloseAndGetParkingLot(capacity, carsCount);

        for (int i = 0; i < capacity; i++) {
            int parkingSpaceNumber = i;
            ThrowingRunnable leaveEmptyParkingLotFunction =
                    () -> {
                        new Car(parkingLot).start();
                        parkingLot.leaveParkingSpace(parkingSpaceNumber);
                    };
            assertThrows("You cannot leave one parking space twice", RuntimeException.class, leaveEmptyParkingLotFunction);
        }
    }
}