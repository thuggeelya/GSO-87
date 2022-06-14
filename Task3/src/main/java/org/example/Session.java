package org.example;

public final class Session {

    private static final int TIMER_INTERVAL = 1;

    public void startSession(String messageFirst, String messageSecond) {
        new ThreadCounter(TIMER_INTERVAL);
        new ThreadMessage(5, messageFirst);
        new ThreadMessage(7, messageSecond);
    }
}