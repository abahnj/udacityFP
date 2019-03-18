package com.norvera.confession.data;

import com.norvera.confession.data.models.CommandmentEntry;
import com.norvera.confession.data.models.ExaminationActiveEntry;
import com.norvera.confession.data.models.ExaminationEntry;
import com.norvera.confession.data.models.GuideEntry;
import com.norvera.confession.data.models.InspirationEntry;
import com.norvera.confession.data.models.PrayersEntry;
import com.norvera.confession.utils.AppExecutor;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.sqlite.db.SimpleSQLiteQuery;


public class AppRepository {
    private final AppExecutor executor;
    private final AppDatabase appDatabase;

    public AppRepository(AppDatabase appDatabase, AppExecutor executor) {
        this.executor = executor;
        this.appDatabase = appDatabase;
    }

    public LiveData<List<CommandmentEntry>> loadCommandmentsForReligious() {
        return appDatabase.commandmentDao().loadCommandmentsForReligious();
    }

    public LiveData<List<CommandmentEntry>> loadCommandmentsForLayPerson() {
        return appDatabase.commandmentDao().loadCommandmentsForLayPerson();
    }

    public LiveData<CommandmentEntry> loadCommandmentById(long id) {
        return appDatabase.commandmentDao().selectCommandmentById(id);
    }

    public void addCommandment(CommandmentEntry commandmentEntry) {
        executor.diskIO().execute(() -> appDatabase.commandmentDao().addCommandment(commandmentEntry));
    }


    public LiveData<List<ExaminationEntry>> loadAllExaminationsForCommandmentAndUser(SimpleSQLiteQuery query) {
        return appDatabase.examinationDao().loadAllExaminationsForCommandmentAndUser(query);
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

    public LiveData<InspirationEntry> getInspirationForId(int id) {
        return appDatabase.inspirationDao().getInspirationForId(id);
    }

    public void decrementCountForEntry(ExaminationEntry examinationEntry) {
        executor.diskIO().execute(() -> appDatabase.examinationDao().decrementCount(examinationEntry));
    }
}
