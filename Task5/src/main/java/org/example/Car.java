package org.example;

public class Car extends Thread {

    private final Ferry ferry;

    public Car(Ferry ferry) {
        this.ferry = ferry;
        setName(getName().replace("Thread", "Car"));
    }

    @Override
    public void run() {
        try {
            sleep(400);
            ferry.acquire();

            synchronized (ferry) {
                System.out.println(getName() + ".. ");
                ferry.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}