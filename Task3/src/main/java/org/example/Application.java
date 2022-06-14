package org.example;

public class Application {

    public static void main(String[] args) {
        new ThreadCounter(1);
        new ThreadMessage(5, args[0]);
        new ThreadMessage(7, args[1]);
    }
}