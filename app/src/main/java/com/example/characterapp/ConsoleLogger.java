package com.example.characterapp;

public class ConsoleLogger extends Logger{

    @Override
    public void e(String tag, String message, Exception e) {
        System.out.println("E " + tag + ": " + message);
    }

    // similar for other methods
}
