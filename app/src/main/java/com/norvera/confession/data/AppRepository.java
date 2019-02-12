package com.norvera.confession.data;

import com.norvera.confession.data.models.CommandmentEntry;
import com.norvera.confession.utils.AppExecutor;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


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

    public LiveData<Integer> add(CommandmentEntry commandmentEntry) {
        MutableLiveData<Integer> liveData = new MutableLiveData<>();
        executor.diskIO().execute(() -> liveData.postValue(appDatabase.commandmentDao().addCommandment(commandmentEntry)));
        return liveData;
    }



}
