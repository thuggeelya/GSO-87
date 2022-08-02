package org.example;

public class Terminator extends Thread {

    protected boolean terminate = false;

    protected void terminate() {
        terminate = true;
    }
}