package org.example;

public class Application {

    public static void main(String[] args) {
        Ferry ferry = new Ferry();
        ferry.setName("Ferry");

        for (int i = 0; i < 500; i++) {
            new Car(ferry).start();
        }

        ferry.start();
    }
}