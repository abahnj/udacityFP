package com.norvera.confession.ui.main;

import android.util.Log;

import com.norvera.confession.data.AppRepository;
import com.norvera.confession.data.models.CommandmentEntry;
import com.norvera.confession.data.models.ExaminationEntry;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private static final String TAG = MainViewModel.class.getSimpleName();

    private final AppRepository appRepository;

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
}
