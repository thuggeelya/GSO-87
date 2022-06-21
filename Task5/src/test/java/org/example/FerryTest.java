package org.example;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.Assert.assertTrue;

public class FerryTest {

    @Test
    public void isTripleCapaciousFerry() throws InterruptedException {
        LinkedList<Car> sharedQueue = new LinkedList<>();
        int carsCount = 30;
        ExecutorService cars = Executors.newFixedThreadPool(carsCount);
        List<Future<?>> compilationList = new ArrayList<>();
        Ferry ferry = new Ferry(sharedQueue, carsCount);
        ferry.start();

        for (int i = 0; i < carsCount; i++) {
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                System.out.println("No more cars to ferry");
            }

            compilationList.add(cars.submit(new Car(sharedQueue)));
        }

        while (compilationList.stream().anyMatch(f -> !f.isDone())) {
            Thread.sleep(0);
        }

        cars.shutdown();
        assertTrue(ferry.isAlwaysTripleCapacious());
    }
}