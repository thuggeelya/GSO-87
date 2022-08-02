package org.example;

import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

import static org.junit.Assert.assertTrue;

public class FerryTest {

    @Test
    public void checkOnlyThreeCarsFerryingInTime() throws InterruptedException {
        LinkedList<Car> sharedQueue = new LinkedList<>();
        String fileName = "src/test/resources/output.txt";
        List<Character> carIterationList = new ArrayList<>();

        try (PrintStream printStream = new PrintStream(fileName)) {
            Ferry ferry = new Ferry(sharedQueue, printStream);
            CyclicBarrier cyclicBarrier = new CyclicBarrier(3, ferry);

            for (int i = 0; i < 35; i++) {
                Thread.sleep(400);
                new Car(sharedQueue, cyclicBarrier).start();
            }

            ferry.stopFerry();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = br.readLine()) != null) {
                carIterationList.add(line.charAt(0));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 123 123 123 ...
        for (int i = 0; i < carIterationList.size() - 1; i++) {
            byte c1 = (byte) Character.getNumericValue(carIterationList.get(i));
            byte c2 = (byte) Character.getNumericValue(carIterationList.get(i + 1));
            assertTrue(((c2 - c1 == 1) || (c1 - c2 == 2)) && (c1 <= 3) && (c2 <= 3));
        }
    }
}