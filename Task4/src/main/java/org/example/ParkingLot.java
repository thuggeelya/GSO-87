package org.example;

import lombok.Getter;

import java.util.logging.Logger;

public class ParkingLot implements Runnable {

    @Getter
    private final boolean[] parkingQueue;

    public ParkingLot(int capacity) {
        this.parkingQueue = new boolean[capacity];

        for (int i = 0; i < capacity; i++) {
            parkingQueue[i] = false;
        }
    }

    @Override
    public void run() {
//        long currentTime = LocalTime

        //noinspection InfiniteLoopStatement
        while (true) {
            try {
                //noinspection BusyWait
                Thread.sleep(500);
                synchronized (parkingQueue) {
                    parkingQueue.notify();
                }
            } catch (InterruptedException e) {
                Logger.getGlobal().severe("Error: " + e);
            }
        }
    }
}