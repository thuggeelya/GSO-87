package org.example;

import java.util.LinkedList;
import java.util.concurrent.CyclicBarrier;

public class Application {

    public static void main(String[] args) throws InterruptedException {
        LinkedList<Car> sharedQueue = new LinkedList<>();
        Ferry ferry = new Ferry(sharedQueue, System.out);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, ferry);

        while (ferry.isRunning()) {
            Thread.sleep(400);
            new Car(sharedQueue, cyclicBarrier).start();
        }
    }
}