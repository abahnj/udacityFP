package com.norvera.confession.ui.main;

import android.util.Log;

import com.norvera.confession.data.AppRepository;
import com.norvera.confession.data.models.CommandmentEntry;
import com.norvera.confession.data.models.ExaminationActiveEntry;
import com.norvera.confession.data.models.ExaminationEntry;
import com.norvera.confession.data.models.GuideEntry;
import com.norvera.confession.data.models.PrayersEntry;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private static final String TAG = MainViewModel.class.getSimpleName();

    private final AppRepository appRepository;



    public ObservableField<List<ExaminationEntry>> examinationEntries = new ObservableField<>();

    MainViewModel(AppRepository repository) {
        Log.d(TAG, "Actively retrieving the movies from the DataBase");
        appRepository = repository;
    }

    public LiveData<List<CommandmentEntry>> allCommandments() {
        return appRepository.loadAllCommandments();
    }

    public LiveData<List<ExaminationEntry>> allExaminationsForCommandment(long commandmentId) {
        return appRepository.loadAllExaminationsForCommandment(commandmentId);
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
}
