package com.norvera.confession.utils;

import android.content.Context;

import com.norvera.confession.data.AppDatabase;
import com.norvera.confession.data.AppRepository;
import com.norvera.confession.data.dao.SinDao;

import java.util.concurrent.Executors;


public class Factory {

    //  REPOSITORY
    public static AppRepository provideAppRepository(AppDatabase appDatabase, AppExecutor executor) {
        return new AppRepository(appDatabase, executor);
    }

    // EXECUTOR
    public static AppExecutor provideAppExecutor() {
        return new AppExecutor(Executors.newSingleThreadExecutor(), new MainThreadExecutor());
    }

    // Database
    public static AppDatabase provideDatabase(Context context) {
       return AppDatabase.getInstance(context);
    }

    public static SinDao provideSinDao(Context context) {
        return provideDatabase(context).sinDao();
    }



}