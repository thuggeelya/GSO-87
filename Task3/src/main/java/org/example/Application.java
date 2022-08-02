package org.example;

public class Application {

    public static void main(String[] args) {
        new ThreadCounter(1, System.out).start();
        new ThreadMessage(5, args[0], System.out).start();
        new ThreadMessage(7, args[1], System.out).start();
    }
}