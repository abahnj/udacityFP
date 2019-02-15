package com.norvera.confession.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;

import com.norvera.confession.data.AppRepository;
import com.norvera.confession.utils.InjectorUtils;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MainViewModelFactory extends ViewModelProvider.NewInstanceFactory{

    @SuppressLint("StaticFieldLeak")
    private static volatile MainViewModelFactory INSTANCE;

    private final AppRepository mAppRepository;

    public static MainViewModelFactory getInstance(Context context) {

        if (INSTANCE == null) {
            synchronized (MainViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MainViewModelFactory(
                            InjectorUtils.provideAppRepository( InjectorUtils.provideDatabase(context), InjectorUtils.provideAppExecutor()));
                }
            }
        }
        return INSTANCE;
    }


    @VisibleForTesting
    public static void destroyInstance() {
        INSTANCE = null;
    }


    private MainViewModelFactory(AppRepository repository) {
        mAppRepository = repository;
    }

    // Note: This can be reused with minor modifications
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new MainViewModel( mAppRepository);
    }
}
