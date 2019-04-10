package com.norvera.confession

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.firebase.analytics.FirebaseAnalytics
import com.norvera.confession.viewmodels.MainViewModel
import com.norvera.confession.databinding.MainActivityBinding
import com.norvera.confession.utils.Constants
import com.norvera.confession.utils.InjectorUtils
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var mViewModel: MainViewModel
    private lateinit var mainActivityBinding: MainActivityBinding
    private lateinit var navController: NavController
    private lateinit var mFirebaseAnalytics: FirebaseAnalytics
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var mAdView: AdView
    private lateinit var mAdContainer: FrameLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)



        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.commandment_fragment,
                R.id.confessionFragmentOne,
                R.id.guideFragment,
                R.id.prayerFragmentList
            )
        )
        setSupportActionBar(mainActivityBinding.toolbar)

        mainActivityBinding.toolbar.setNavigationOnClickListener { navController.navigateUp() }
        // This allows NavigationUI to decide what label to show in the action bar
        // By using appBarConfig, it will also determine whether to
        // show the up arrow or drawer menu icon
        setupBottomNavMenu(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)

        setupViewModel()
        checkIntent()


        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)

        mAdContainer = mainActivityBinding.adContainer
        val adRequest = AdRequest.Builder().build()
        val adSize = AdSize(displayWidthInDps(), 50)
        mAdView = AdView(this)
        mAdContainer.addView(mAdView)
        mAdView.adSize = adSize
        mAdView.adUnitId = resources.getString(R.string.banner_ad_unit_id)
        mAdView.loadAd(adRequest)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        openSettings()
    }

    private fun checkIntent() {
        val extras = intent.extras
        if (extras != null) {
            Timber.e(extras.toString())
            if (extras.getString(Constants.WIDGET_EXTRA_KEY) == Constants.WIDGET_SETTING_VALUE) {
                openSettings()
            }
        }
    }


    private fun displayWidthInDps(): Int {
        val configuration = resources.configuration
        return configuration.screenWidthDp
    }

    private fun setupBottomNavMenu(navController: NavController) {
        val bottomNav = mainActivityBinding.bottomNavBar
        bottomNav.setupWithNavController(navController)
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        when (item.itemId) {
            R.id.action_settings -> {
                openSettings()
                return true
            }
            R.id.action_share -> {
                share()
                return true
            }
        }
        return true
    }

    private fun openSettings() {
        navController.navigate(R.id.settingsFragment)
    }

    private fun share() {
        val builder = ShareCompat.IntentBuilder.from(this)
            .setText(getString(R.string.share_text))
            .setType("text/plain")
            .createChooserIntent()
            .addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)

        startActivity(builder)
    }


    private fun setupViewModel() {
        // todo move view model creation to factory method
        val factory = InjectorUtils.provideViewModelFactory(this)
        mViewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)

    }

}
