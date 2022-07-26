package org.example;

import java.util.ArrayList;
import java.util.List;

import static org.example.MyRunnable.terminate;

public class Application {

    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();
        int nThread = 0;
        int nThreads = Integer.parseInt(args[0]);
        int nMaxThreadsRunnable = 2;
        int nThreadsRunning = 0;
        List<Thread> threadList = new ArrayList<>(nThreads);

        while (nThread < nThreads) {
            Thread thread = new Thread(new MyRunnable(nThread++, nThreads, lock, System.out));
            threadList.add(thread);

            do {
                for (Thread t : threadList) {
                    nThreadsRunning += (t.getState() == Thread.State.RUNNABLE) ? 1 : 0;
                }
            } while (nThreadsRunning > nMaxThreadsRunnable);

            nThreadsRunning = 0;
            thread.start();
        }

        Thread.sleep(20000);
        terminate();
    }
}