package com.norvera.confession.ui.main;

import com.norvera.confession.data.AppRepository;
import com.norvera.confession.data.models.CommandmentEntry;
import com.norvera.confession.data.models.ExaminationActiveEntry;
import com.norvera.confession.data.models.ExaminationEntry;
import com.norvera.confession.data.models.GuideEntry;
import com.norvera.confession.data.models.InspirationEntry;
import com.norvera.confession.data.models.PrayersEntry;
import com.norvera.confession.data.models.User;

import java.util.List;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.sqlite.db.SimpleSQLiteQuery;
import timber.log.Timber;

public class MainViewModel extends ViewModel {
    private static final String TAG = MainViewModel.class.getSimpleName();

    private final AppRepository appRepository;



    public ObservableField<List<ExaminationEntry>> examinationEntries = new ObservableField<>();

    MainViewModel(AppRepository repository) {
        Timber.d("Actively retrieving the movies from the DataBase");
        appRepository = repository;
    }

    public LiveData<List<CommandmentEntry>> allCommandmentsForLay() {
        return appRepository.loadCommandmentsForLayPerson();
    }

    public LiveData<List<CommandmentEntry>> allCommandmentsForReligious() {
        return appRepository.loadCommandmentsForReligious();
    }

    public LiveData<List<ExaminationEntry>> allExaminationsForCommandmentAndUser(SimpleSQLiteQuery query) {
        return appRepository.loadAllExaminationsForCommandmentAndUser(query);
    }

    public void updateCountForEntry(ExaminationEntry examinationEntry) {
        appRepository.updateCountForEntry(examinationEntry);
    }

    public void updateCountForEntryId(long examinationId) {
        //todo implement and test out
    }

    public LiveData<List<GuideEntry>> allGuidesForId(int guideId) {
        return appRepository.allGuidesForId(guideId);
    }

    public LiveData<List<PrayersEntry>> loadAllPrayers() {
        return appRepository.loadAllPrayers();
    }

    public LiveData<List<ExaminationActiveEntry>> allExaminationsWithCount() {
        return appRepository.loadAllExaminationsWithCount();
    }

    public LiveData<InspirationEntry> getInspirationForId(int id) {
        return appRepository.getInspirationForId(id);
    }

    public void decrementCount(ExaminationEntry examinationEntry) {
        appRepository.decrementCountForEntry(examinationEntry);
    }
}
