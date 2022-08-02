package org.example;

import java.util.LinkedList;

public class Application {

    public static void main(String[] args) throws InterruptedException {
        LinkedList<Car> sharedQueue = new LinkedList<>();
        int carsCount = 30;
        Ferry ferry = new Ferry(sharedQueue);
        ferry.start();

        for (int i = 0; i < carsCount; i++) {
            Thread.sleep(400);
            new Car(sharedQueue).start();
        }
    }
}