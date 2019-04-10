package com.norvera.confession.utils

import android.content.Context

import com.norvera.confession.data.AppDatabase
import com.norvera.confession.data.AppRepository
import com.norvera.confession.data.dao.ExaminationDao
import com.norvera.confession.ui.main.MainViewModelFactory

import java.util.concurrent.Executors


object InjectorUtils {


    //  REPOSITORY
    fun provideAppRepository(appDatabase: AppDatabase, executor: AppExecutor): AppRepository {
        return AppRepository(appDatabase, executor)
    }

    // EXECUTOR
    fun provideAppExecutor(): AppExecutor {
        return AppExecutor(Executors.newSingleThreadExecutor(), MainThreadExecutor())
    }

    // Database
    fun provideDatabase(context: Context): AppDatabase {
        return AppDatabase.getInstance(context)!!
    }

    fun provideExaminationDao(context: Context): ExaminationDao {
        return provideDatabase(context).examinationDao()
    }

    fun provideCommandmentDao(context: Context): ExaminationDao {
        return provideDatabase(context).examinationDao()
    }


    fun provideViewModelFactory(context: Context): MainViewModelFactory {
        return MainViewModelFactory.getInstance(context)!!
    }
}