package com.norvera.confession;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.norvera.confession.databinding.MainActivityBinding;
import com.norvera.confession.ui.main.MainViewModel;
import com.norvera.confession.ui.main.MainViewModelFactory;
import com.norvera.confession.utils.InjectorUtils;

import java.util.HashSet;
import java.util.Set;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mViewModel;
    private MainActivityBinding mainActivityBinding;
    private NavController navController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        setSupportActionBar(mainActivityBinding.toolbar);
        Set<Integer> menuId = new HashSet<>();

        menuId.add(R.id.commandment_fragment);
        menuId.add(R.id.confessionFragmentOne);
        menuId.add(R.id.guideFragment);
        menuId.add(R.id.prayerFragmentList);


        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(menuId).build();

        setupActionBar(navController, appBarConfiguration);
        setupBottomNavMenu(navController);

        setupViewModel();
    }

    private void setupBottomNavMenu(NavController navController) {
        BottomNavigationView bottomNav = mainActivityBinding.bottomNavBar;
        NavigationUI.setupWithNavController(bottomNav, navController);
    }

    private void setupActionBar(NavController navController, AppBarConfiguration appBarConfig) {
        // This allows NavigationUI to decide what label to show in the action bar
        // By using appBarConfig, it will also determine whether to
        // show the up arrow or drawer menu icon
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfig);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }


    private void setupViewModel() {
        // todo move view model creation to factory method
        MainViewModelFactory factory = InjectorUtils.provideViewModelFactory(this);
        mViewModel = ViewModelProviders.of(this, factory).get(MainViewModel.class);

    }

}
