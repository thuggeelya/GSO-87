package org.example;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class ThreadMessage implements Runnable {

    private final int interval;
    private final String message;
    public static AtomicInteger time = new AtomicInteger(0);
    public static final Map<Integer, MessagesContainer> MESSAGES_MAP = new ConcurrentHashMap<>();

    public ThreadMessage(int interval, String message) {
        this.interval = interval;
        this.message = message;
        Thread thread = new Thread(this);
        thread.setName("Thread for " + message);
        thread.start();
    }

    synchronized static int incrementTime() {
        return time.incrementAndGet();
    }

    @Override
    public void run() {
        //noinspection InfiniteLoopStatement
        while (true) {
            try {
                synchronized (ThreadMessage.class) {
                    ThreadMessage.class.wait();
                    int currentTimeTick = time.get();

                    if (currentTimeTick % interval == 0) {
                        System.out.println(message);
                        MessagesContainer currentContainer = MESSAGES_MAP.get(currentTimeTick);

                        if (currentContainer == null) {
                            MessagesContainer messagesContainer = new MessagesContainer();
                            currentContainer = MESSAGES_MAP.putIfAbsent(currentTimeTick, messagesContainer);
                            currentContainer = (currentContainer == null) ? messagesContainer : currentContainer;
                        }

                        currentContainer.addMessage(message);
                    }
                }
            } catch (InterruptedException e) {
                Logger.getGlobal().severe("Thread was interrupted: " + e);
            }
        }
    }
}