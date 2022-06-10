package org.example;

import java.util.logging.Logger;

public class Car implements Runnable {

    private final Boolean[] parkingQueue;

    public Car(Boolean[] parkingQueue) {
        this.parkingQueue = parkingQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                synchronized (parkingQueue) {
                    if ()
                }
            } catch (InterruptedException e) {
                Logger.getGlobal().severe("Error: " + e);
            }
        }
    }

    //
}