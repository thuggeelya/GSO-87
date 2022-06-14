package org.example;

import org.junit.Test;

import static org.example.ThreadMessage.MESSAGES_MAP;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class MessagesTest {

    @Test
    public void checkMessagesPerSecond() {
        String firstMessage = "First message";
        String secondMessage = "Second message";
        MessagesContainer containerFor5 = new MessagesContainer();
        containerFor5.addMessage(firstMessage);
        MessagesContainer containerFor7 = new MessagesContainer();
        containerFor7.addMessage(secondMessage);
        MessagesContainer containerFor5And7 = new MessagesContainer();
        containerFor5And7.addMessage(firstMessage);
        containerFor5And7.addMessage(secondMessage);
        new Session().startSession(firstMessage, secondMessage);

        while (MESSAGES_MAP.size() < 11) {
            try {
                //noinspection BusyWait
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println(MESSAGES_MAP);

        for (int i = 1; i < 36; i++) {
            MessagesContainer value = MESSAGES_MAP.get(i);

            if (i % 5 == 0) {
                if (i % 7 == 0) {
                    assertEquals(containerFor5And7, value);
                } else {
                    assertEquals(containerFor5, value);
                }
            } else if (i % 7 == 0) {
                assertEquals(containerFor7, value);
            } else {
                assertNull(value);
            }
        }
    }
}