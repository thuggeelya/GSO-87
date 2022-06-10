package org.example;

public class App {

    public static void main( String[] args ) {
        Object lock = new Object();

        new MyThread("first", lock);
        new MyThread("second", lock);
    }
}
