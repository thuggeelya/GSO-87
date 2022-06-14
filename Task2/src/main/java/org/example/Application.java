package org.example;

public class Application {

    public static void main(String[] args) {
        Object lock = new Object();
        new Thread(new FirstRunnable(args[0], lock)).start();
        new Thread(new SecondRunnable(args[1], lock)).start();
    }
}