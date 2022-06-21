package org.example;

import java.util.LinkedList;

public class Application {

    public static void main(String[] args) {
        LinkedList<Car> sharedQueue = new LinkedList<>();
        new Ferry(sharedQueue).start();

        for (int i = 0; i < 300; i++) {
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                System.out.println("No more cars to ferry");
            }

            new Car(sharedQueue).start();
        }
    }
}