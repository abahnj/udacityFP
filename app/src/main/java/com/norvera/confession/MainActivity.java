package com.norvera.confession;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.norvera.confession.databinding.MainActivityBinding;
import com.norvera.confession.ui.main.MainViewModel;
import com.norvera.confession.ui.main.MainViewModelFactory;
import com.norvera.confession.utils.InjectorUtils;

import java.util.HashSet;
import java.util.Set;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
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
    private FirebaseAnalytics mFirebaseAnalytics;

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

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                openSettings();
                return true;
            case R.id.action_share:
                share();
                return true;
        }
        return true;
    }

    private void openSettings() {
        navController.navigate(R.id.settingsFragment);
    }

    private void share() {
        Intent builder = ShareCompat.IntentBuilder.from(this)
                .setText(getString(R.string.share_text))
                .setType("text/plain")
                .createChooserIntent()
                .addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

        startActivity(builder);
    }


    private void setupViewModel() {
        // todo move view model creation to factory method
        MainViewModelFactory factory = InjectorUtils.provideViewModelFactory(this);
        mViewModel = ViewModelProviders.of(this, factory).get(MainViewModel.class);

    }

}
