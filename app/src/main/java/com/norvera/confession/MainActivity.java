package com.norvera.confession;

import android.os.Bundle;

import com.norvera.confession.data.models.CommandmentEntry;
import com.norvera.confession.ui.commandment.CommandmentsFragment;
import com.norvera.confession.ui.main.MainViewModel;
import com.norvera.confession.ui.main.MainViewModelFactory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends AppCompatActivity implements CommandmentsFragment.CommandmentClickListener{

    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, CommandmentsFragment.newInstance(1))
                    .commitNow();
        }

        setupViewModel();
    }

    private void setupViewModel() {
        // todo move view model creation to factory method
        MainViewModelFactory factory = MainViewModelFactory.getInstance(this);
        mViewModel = ViewModelProviders.of(this, factory).get(MainViewModel.class);

    }

    @Override
    public void onListFragmentInteraction(CommandmentEntry item) {

    }
}
