package org.example;

public class Application {

    public static void main(String[] args) {
        ThreadCounter counter = new ThreadCounter(1, System.out);
        counter.start();
        new ThreadMessage(5, args[0], System.out, counter).start();
        new ThreadMessage(7, args[1], System.out, counter).start();
    }
}