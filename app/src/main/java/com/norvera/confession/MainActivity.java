package com.norvera.confession;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.norvera.confession.databinding.MainActivityBinding;
import com.norvera.confession.ui.main.MainViewModel;
import com.norvera.confession.ui.main.MainViewModelFactory;
import com.norvera.confession.utils.InjectorUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mViewModel;
    private NavController navController;
    private MainActivityBinding mainActivityBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        navController =  Navigation.findNavController(this, R.id.nav_host_fragment);

        setupBottomNavMenu(navController);

//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.container, CommandmentsFragment.newInstance(1))
//                    .commitNow();
//        }

        setupViewModel();
    }

    private void setupBottomNavMenu(NavController navController) {
        BottomNavigationView bottomNav = mainActivityBinding.bottomNavBar;
        NavigationUI.setupWithNavController(bottomNav, navController);
    }

    private void setupViewModel() {
        // todo move view model creation to factory method
        MainViewModelFactory factory = InjectorUtils.provideViewModelFactory(this);
        mViewModel = ViewModelProviders.of(this, factory).get(MainViewModel.class);

    }

}
