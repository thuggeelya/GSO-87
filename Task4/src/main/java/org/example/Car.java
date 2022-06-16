package org.example;

import java.util.logging.Logger;

public class Car implements Runnable {

    private final boolean[] parkingQueue;

    public Car(boolean[] parkingQueue) {
        this.parkingQueue = parkingQueue;
    }

    @Override
    public void run() {
        int parkingSpaceNumber;
        long delay;

        //noinspection InfiniteLoopStatement
        while (true) {
            try {
                synchronized (parkingQueue) {
                    parkingQueue.wait();
                    parkingSpaceNumber = parkAndGetParkingSpaceNumber();
                }

                if (parkingSpaceNumber != -1) {
                    delay = (long) (Math.floor(Math.random() * 6000) + 2000);
                    //noinspection BusyWait
                    Thread.sleep(delay);

                    synchronized (parkingQueue) {
                        parkingQueue[parkingSpaceNumber] = false;
//                    parkingQueue.notify();
                    }

                    System.out.println("Car has gone from " + parkingSpaceNumber + " after " + (double) delay / 1000 + " sec");
                }
            } catch (InterruptedException e) {
                Logger.getGlobal().severe("Error: " + e);
            }
        }
    }

    private int parkAndGetParkingSpaceNumber() throws InterruptedException {
        for (int i = 0; i < parkingQueue.length; i++) {
            if (!parkingQueue[i]) {
                parkingQueue[i] = true;
                System.out.println("Car is parking at " + i);
                return i;
            }
        }

        System.out.println("No vacant places");
        return -1;
    }
}