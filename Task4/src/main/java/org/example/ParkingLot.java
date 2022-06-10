package org.example;

import java.util.Arrays;
import java.util.logging.Logger;

/**
 * producer class
 */
public class ParkingLot implements Runnable {

    private final Boolean[] parkingQueue;
    private final int capacity;

    public ParkingLot(Boolean[] parkingQueue, int capacity) {
        this.parkingQueue = parkingQueue;
        this.capacity = capacity;
    }

    @Override
    public void run() {
        while (true) {
            try {
                synchronized (parkingQueue) {
                    if (Arrays.stream(parkingQueue).noneMatch(b -> b)) {
                        parkingQueue.wait();
                    }

                    for (int i = 0; i < parkingQueue.length; i++) {
                        if (!parkingQueue[i]) {
                            parkingQueue[i] = true;
                            break;
                        }
                    }

                    Thread.sleep((long) (Math.floor(Math.random() * 8000) + 2000));
                    parkingQueue.notify();
                }
            } catch (InterruptedException e) {
                Logger.getGlobal().severe("Error: " + e);
            }
        }
    }
}