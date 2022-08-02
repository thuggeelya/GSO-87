package org.example;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Application {

    public static void main(String[] args) {
        int nThread = 0;
        int nThreads = Integer.parseInt(args[0]);
        Queue<MyRunnable> queue = new LinkedList<>();
        List<MyRunnable> myRunnableList = new ArrayList<>();
//      List<Thread> threadList = new ArrayList<>();

        while (nThread < nThreads) {
            MyRunnable runnable = new MyRunnable(nThread++, System.out, queue);
            myRunnableList.add(runnable);
        }

        myRunnableList.forEach(r -> {
            Thread thread = new Thread(r);
            thread.start();
//          threadList.add(thread);
        });

//      Thread.sleep(500);
//      threadList.get(0).interrupt();
    }
}