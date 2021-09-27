package com.example.screens;

import android.util.Log;

public final class Time {
    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            Log.e("App", e.toString());
        }
    }
}
