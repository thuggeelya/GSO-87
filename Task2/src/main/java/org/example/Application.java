package org.example;

import java.util.LinkedList;
import java.util.Queue;

public class Application {

    public static void main(String[] args) {
        int nThread = 0;
        int nThreads = Integer.parseInt(args[0]);
        Queue<Integer> queue = new LinkedList<>();
        queue.add(nThread);

        while (nThread < nThreads) {
            new Thread(new MyRunnable(nThread++, nThreads, System.out, queue)).start();
        }
    }
}