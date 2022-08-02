package org.example;

import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MessagesTest {

    @Test
    public void checkThreadMessagesPerSecond() throws InterruptedException {
        String firstMessage = "Message-1";
        String secondMessage = "Message-2";
        int firstMessageInterval = 5;
        int secondMessageInterval = 7;
        File file = new File("src/test/resources/task3.txt");
        List<String> result = new ArrayList<>();

        try (PrintStream printStream = new PrintStream(file)) {
            ThreadCounter counter = new ThreadCounter(1, printStream);
            ThreadMessage threadMessage1 = new ThreadMessage(firstMessageInterval, firstMessage, printStream);
            ThreadMessage threadMessage2 = new ThreadMessage(secondMessageInterval, secondMessage, printStream);

            counter.start();
            threadMessage1.start();
            threadMessage2.start();

            Thread.sleep(36_000);

            counter.terminate();
            threadMessage1.terminate();
            threadMessage2.terminate();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                result.add(line);
            }

            file.delete();
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).severe(e.getMessage());
        }

        String line, errorMessage, resultNext, resultAfterNext;

        for (int i = 0; i < result.size() - 2; i++) {
            line = result.get(i);

            if ((line.matches("^\\d*$"))) {
                int nTick = Integer.parseInt(line);
                errorMessage = nTick + " - error";
                resultNext = result.get(i + 1);
                resultAfterNext = result.get(i + 2);

                if (nTick % (firstMessageInterval * secondMessageInterval) == 0) {

                    assertTrue(errorMessage,
                            (resultNext.equals(firstMessage) && resultAfterNext.equals(secondMessage)) ||
                            (resultNext.equals(secondMessage) && resultAfterNext.equals(firstMessage)));

                } else if (nTick % firstMessageInterval == 0) {

                    assertEquals(errorMessage, firstMessage, resultNext);

                } else if (nTick % secondMessageInterval == 0) {

                    assertEquals(errorMessage, secondMessage, resultNext);

                }
            }
        }
    }
}