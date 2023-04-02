package com.example.characterapp;

import android.util.Log;

import java.io.IOException;

/**
 * This class is a non-static logger
 */
public class Logger {

    public void e(String tag, String message, Exception e) {
        Log.e(tag, message);
    }
}
