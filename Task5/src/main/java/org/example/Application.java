package org.example;

import java.util.LinkedList;

public class Application {

    public static void main(String[] args) {
        LinkedList<Car> sharedQueue = new LinkedList<>();
        int carsCount = 30;
        Ferry ferry = new Ferry(sharedQueue);
        ferry.start();

        for (int i = 0; i < carsCount; i++) {
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                System.out.println("No more cars to ferry");
            }

            new Car(sharedQueue).start();
        }
    }
}