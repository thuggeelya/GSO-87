package org.example;

import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.example.ThreadCounter.terminate;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MessagesTest {

    @Test
    public void checkThreadMessagesPerSecond() throws InterruptedException {
        String firstMessage = "Message-5";
        String secondMessage = "Message-7";
        File file = new File("src/test/resources/task3.txt");
        List<String> result = new ArrayList<>();

        try (PrintStream printStream = new PrintStream(file)) {
            new ThreadCounter(1, printStream);
            new ThreadMessage(5, firstMessage, printStream);
            new ThreadMessage(7, secondMessage, printStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Thread.sleep(60_000);
        terminate();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                result.add(line);
            }

            file.delete();
        } catch (IOException e) {
            Logger.getGlobal().severe(e.getMessage());
        }

        for (int i = 0; i < result.size() - 2; i++) {
            String line = result.get(i);

            if ((!line.contains("\\D"))) {
                int nTick = Integer.parseInt(line);

                if (nTick % 35 == 0) {
                    assertTrue((result.get(i + 1).equals(firstMessage) && result.get(i + 2).equals(secondMessage)) ||
                            (result.get(i + 1).equals(secondMessage) && result.get(i + 2).equals(firstMessage)));
                }

                if (nTick % 5 == 0) {
                    assertEquals(result.get(i + 1), firstMessage);
                }

                if (nTick % 7 == 0) {
                    assertEquals(result.get(i + 1), secondMessage);
                }
            }
        }
    }
}