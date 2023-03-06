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

    public void w(String tag, String message) {
        Log.w(tag, message);
    }

    public void v(String tag, String message) {
        Log.v(tag, message);
    }

    public void d(String tag, String message) {
        Log.d(tag, message);
    }
}
