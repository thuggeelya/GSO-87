package org.example;

public class Application {

    public static void main(String[] args) {
        Object lock = new Object();
        new Thread(new MyRunnable(args[0], lock)).start();
        new Thread(new MyRunnable(args[1], lock)).start();
    }
}