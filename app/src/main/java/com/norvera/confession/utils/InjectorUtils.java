package com.norvera.confession.utils;

import android.content.Context;

import com.norvera.confession.data.AppDatabase;
import com.norvera.confession.data.AppRepository;
import com.norvera.confession.data.dao.ExaminationDao;
import com.norvera.confession.ui.main.MainViewModelFactory;

import java.util.concurrent.Executors;


public class InjectorUtils {

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

    public static ExaminationDao provideExaminationDao(Context context) {
        return provideDatabase(context).examinationDao();
    }

    public static ExaminationDao provideCommandmentDao(Context context) {
        return provideDatabase(context).examinationDao();
    }


    public static MainViewModelFactory provideViewModelFactory(Context context) {
        return MainViewModelFactory.getInstance(context);
    }
}