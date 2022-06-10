package org.example;

public final class Session {

    private static final String MESSAGE_FIRST = "Hello";
    private static final String MESSAGE_SECOND = "Are you here?";
    private static final int TIMER_INTERVAL = 1;
    private static final int MESSAGE_FIRST_INTERVAL = 5;
    private static final int MESSAGE_SECOND_INTERVAL = 7;

    public void startSession() {
        new ThreadCounter(TIMER_INTERVAL);
        new ThreadMessage(MESSAGE_FIRST_INTERVAL, MESSAGE_FIRST);
        new ThreadMessage(MESSAGE_SECOND_INTERVAL, MESSAGE_SECOND);
    }
}