package org.example;

public class Application {

    public static void main(String[] args) {
        new Session().startSession(args[0], args[1]);
    }
}