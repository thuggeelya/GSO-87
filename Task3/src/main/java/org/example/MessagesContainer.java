package org.example;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@Getter
public final class MessagesContainer {

    private final List<String> messageList = new ArrayList<>(2);

    public void addMessage(String message) {
        messageList.add(message);
        Collections.sort(messageList);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessagesContainer that = (MessagesContainer) o;
        return messageList.equals(that.messageList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass());
    }

    @Override
    public String toString() {
        return "MessagesContainer{" +
                "messageList=" + messageList +
                '}';
    }
}