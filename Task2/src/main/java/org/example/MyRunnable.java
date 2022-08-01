package org.example;

import java.io.PrintStream;
import java.util.Queue;

public class MyRunnable implements Runnable {

    private final String name;
    private final PrintStream printStream;
    private final Queue<MyRunnable> queue;
    public boolean terminate = false;

    public MyRunnable(int number, PrintStream printStream, Queue<MyRunnable> queue) {
        this.name = "Thread-" + number;
        this.printStream = printStream;
        this.queue = queue;
        queue.add(this);
    }

    public void terminate() {
        terminate = true;
    }

    @Override
    public void run() {
        try {
            while (!terminate) {
                synchronized (queue) {
                    if ((queue.peek() != null) && (queue.peek().equals(this))) {
                        printStream.println(name);
                        queue.add(queue.poll());
                    }

                    queue.notify();
                    queue.wait(1000);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}