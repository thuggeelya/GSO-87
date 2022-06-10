package org.example;

public class MyThread implements Runnable {

    private final String name;
    private final Object objectForLock;

    public MyThread(String name, Object lock) {
        this.objectForLock = lock;
        this.name = name;
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (true) {
            synchronized (objectForLock) {
                try {
                    System.out.println(this.name);
                    objectForLock.notify();
                    objectForLock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
