package com.norvera.confession.data;

import com.norvera.confession.data.models.CommandmentEntry;
import com.norvera.confession.utils.AppExecutor;

import java.util.List;

import androidx.lifecycle.LiveData;


public class AppRepository {
    private final AppExecutor executor;
    private final AppDatabase appDatabase;

    public AppRepository(AppDatabase appDatabase, AppExecutor executor) {
        this.executor = executor;
        this.appDatabase = appDatabase;
    }

    public LiveData<List<CommandmentEntry>> loadAllCommandments() {
        return appDatabase.commandmentDao().selectAll();
    }

    public LiveData<CommandmentEntry> loadCommandmentById(long id) {
        return appDatabase.commandmentDao().selectCommandmentById(id);
    }

    public void addCommandment(CommandmentEntry commandmentEntry) {
        executor.diskIO().execute(() -> appDatabase.commandmentDao().addCommandment(commandmentEntry));
    }



}
