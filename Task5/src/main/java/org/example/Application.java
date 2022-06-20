package org.example;

import java.util.LinkedList;

public class Application {

    public static void main(String[] args) {
        LinkedList<Car> sharedQueue = new LinkedList<>();
        Ferry ferry = new Ferry(sharedQueue);
        ferry.setName("Ferry");
        ferry.start();

        for (int i = 0; i < 500; i++) {
            new Car(sharedQueue).start();

            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}