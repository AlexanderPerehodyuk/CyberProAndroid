package com.example.screens;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
    private static final ExecutorService threadPool = Executors.newWorkStealingPool();

    public static void post(Runnable runnable) {
        threadPool.execute(runnable);
    }
}
