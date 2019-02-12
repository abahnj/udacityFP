package com.norvera.confession;

import android.os.Bundle;

import com.norvera.confession.ui.main.MainFragment;
import com.norvera.confession.ui.main.MainViewModel;
import com.norvera.confession.ui.main.MainViewModelFactory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }
    }

    private void setupViewModel() {
        MainViewModelFactory factory = MainViewModelFactory.getInstance(this);

        mViewModel = ViewModelProviders.of(this, factory).get(MainViewModel.class);

    }
}
