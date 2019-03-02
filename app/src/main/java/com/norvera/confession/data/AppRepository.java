package com.norvera.confession.data;

import com.norvera.confession.data.models.CommandmentEntry;
import com.norvera.confession.data.models.ExaminationActiveEntry;
import com.norvera.confession.data.models.ExaminationEntry;
import com.norvera.confession.data.models.GuideEntry;
import com.norvera.confession.data.models.PrayersEntry;
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


    public LiveData<List<ExaminationEntry>> loadAllExaminationsForCommandment(long commandmentId) {
        return appDatabase.examinationDao().loadAllExaminationsForCommandment(commandmentId);
    }

    public void updateCountForEntry(ExaminationEntry examinationEntry) {
        executor.diskIO().execute(() -> appDatabase.examinationDao().updateEntry(examinationEntry));
    }

    public LiveData<List<GuideEntry>> allGuidesForId(int guideId) {
        return appDatabase.guideDao().allGuidesForId(guideId);
    }

    public LiveData<List<PrayersEntry>> loadAllPrayers() {
        return appDatabase.prayersDao().loadAllPrayers();
    }

    public LiveData<List<ExaminationActiveEntry>> loadAllExaminationsWithCount() {
        return appDatabase.examinationDao().loadAllExaminationsWithCount();
    }
}
