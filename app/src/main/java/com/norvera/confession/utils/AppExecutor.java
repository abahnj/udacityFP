package com.norvera.confession.utils;

import java.util.concurrent.Executor;

public class AppExecutor {

    // TODO Make class a singleton

    private final Executor diskIO;
    private final Executor mainThread;

    public AppExecutor(Executor diskIO, Executor mainThread) {
        this.diskIO = diskIO;
        this.mainThread = mainThread;
    }

    public Executor diskIO() {
        return diskIO;
    }

    public Executor mainThread() {
        return mainThread;
    }
}
