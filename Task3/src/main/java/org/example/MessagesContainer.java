package org.example;

import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@ToString
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
}