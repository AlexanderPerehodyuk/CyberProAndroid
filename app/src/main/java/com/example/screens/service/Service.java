package com.example.screens.service;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class Service {
    private static final ExecutorService threadPool = Executors.newWorkStealingPool();
    private static final StringBuilder stringBuilder = new StringBuilder();
    public static BaseActivity activity;

    public static void init(BaseActivity baseActivity) {
        activity = baseActivity;
    }

    public static void post(Runnable runnable) {
        threadPool.execute(runnable);
    }

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            print("Error while sleep");
            sleep(millis);
        }
    }

    public static void print(Object... objects) {
        try {
            for (Object o : objects) {
                stringBuilder.append(o).append(" ");
            }
            Log.e("Глаз Бога", stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString());
            stringBuilder.setLength(0);
        } catch (Exception exception) {
            print("Error When Log:", exception);
        }
    }

    public static void writeToFile(String fileName, String content) {
        try {
            OutputStreamWriter writer_str = new OutputStreamWriter(
                    activity.openFileOutput(fileName + ".txt", Context.MODE_PRIVATE));

            writer_str.write(content);
            writer_str.close();
        } catch (Exception e) {
            print("Can't save", fileName, e);
        }
    }

    public static String readFromFile(String fileName) {
        try {
            InputStreamReader reader_cooler = new InputStreamReader(activity.openFileInput(fileName + ".txt"));

            String string = new BufferedReader(reader_cooler).readLine();

            reader_cooler.close();

            return string;
        } catch (Exception e) {
            print("Can't recovery", fileName, e);
            print("Creating new file...");
            writeToFile(fileName, "");
            print("Successful");
            return null;
        }
    }
}
