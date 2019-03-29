package com.norvera.confession

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.beautycoder.pflockscreen.PFFLockScreenConfiguration
import com.beautycoder.pflockscreen.fragments.PFLockScreenFragment
import com.norvera.confession.utils.*

class SplashActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener{
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        val hg = sharedPreferences?.getString(key, "")
        if (key == Constants.CODE){
            showLockScreenFragment(true)

        }
    }

    private lateinit var mViewModel: MainViewModel

    private val biometricCallback = object : BiometricCallback {
        override fun onSdkVersionNotSupported() {
            Toast.makeText(
                this@SplashActivity,
                getString(R.string.biometric_error_sdk_not_supported),
                Toast.LENGTH_LONG
            ).show()
            startActivity(intent)
            finish()
        }

        override fun onBiometricAuthenticationNotSupported() {
            Toast.makeText(
                this@SplashActivity,
                getString(R.string.biometric_error_hardware_not_supported),
                Toast.LENGTH_LONG
            ).show()
            startActivity(mIntent)
            finish()
        }

        override fun onBiometricAuthenticationNotAvailable() {
            Toast.makeText(
                this@SplashActivity,
                getString(R.string.biometric_error_fingerprint_not_available),
                Toast.LENGTH_LONG
            ).show()
            startActivity(mIntent)
            finish()
        }

        override fun onBiometricAuthenticationPermissionNotGranted() {
            Toast.makeText(
                this@SplashActivity,
                getString(R.string.biometric_error_permission_not_granted),
                Toast.LENGTH_LONG
            ).show()
            startActivity(mIntent)
            finish()
        }

        override fun onBiometricAuthenticationInternalError(error: String) {
            Toast.makeText(this@SplashActivity, error, Toast.LENGTH_LONG).show()
            startActivity(mIntent)
            finish()
        }

        override fun onAuthenticationFailed() {
            Toast.makeText(
                this@SplashActivity,
                getString(R.string.biometric_failure),
                Toast.LENGTH_LONG
            ).show()
        }

        override fun onAuthenticationCancelled() {
            Toast.makeText(
                this@SplashActivity,
                getString(R.string.biometric_cancelled),
                Toast.LENGTH_LONG
            ).show()
        }

        override fun onAuthenticationSuccessful() {

            Handler().postDelayed({
                startActivity(mIntent)
                finish()
            }, 500)

        }

        override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence) {
            Toast.makeText(this@SplashActivity, helpString, Toast.LENGTH_LONG).show()
        }

        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
            Toast.makeText(this@SplashActivity, errString, Toast.LENGTH_LONG).show()
        }
    }

    private lateinit var mIntent : Intent

    private fun setupViewModel() {
        // todo move view model creation to factory method
        val factory = InjectorUtils.provideViewModelFactory(this)
        mViewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        SharedPreferencesHelper.registerOnSharedPreferenceChangeListener(this, this)
        setupViewModel()
        mIntent = Intent(this@SplashActivity, MainActivity::class.java)
        mIntent.flags = Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_CLEAR_TOP


        /*if (BiometricUtils.isFingerprintAvailable(this))
            displayBiometricPrompt(biometricCallback)
        else
            Toast.makeText(
                this@SplashActivity,
                getString(R.string.biometric_error_fingerprint_not_available),
                Toast.LENGTH_LONG
            ).show()
            startActivity(mIntent)*/
        showLockScreenFragment()

    }

    override fun onDestroy() {
        SharedPreferencesHelper.unregisterOnSharedPreferenceChangeListener(this, null)

        super.onDestroy()
    }

    private fun showLockScreenFragment() {
        mViewModel.isPinCodeEncryptionKeyExist().observe(
            this,
            Observer { result ->
                if (result == null) {
                    return@Observer
                }
                if (result.error != null) {
                    Toast.makeText(
                        this@SplashActivity,
                        "Can not get pin code info",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@Observer
                }
                showLockScreenFragment(result.result)
            }
        )
    }

    private fun showLockScreenFragment(isPinExist: Boolean) {
        val builder = PFFLockScreenConfiguration.Builder(this)
            .setTitle(if (isPinExist) "Unlock with your pin code or fingerprint" else "Create Code")
            .setCodeLength(4)
            .setLeftButton("Can't remember") { }
            .setUseFingerprint(true)
        val fragment = PFLockScreenFragment()

        builder.setMode(
            if (isPinExist)
                PFFLockScreenConfiguration.MODE_AUTH
            else
                PFFLockScreenConfiguration.MODE_CREATE
        )
        if (isPinExist) {
            fragment.setEncodedPinCode(SharedPreferencesHelper.getSharedPreferenceString(this, Constants.CODE, "@null" ))
            fragment.setLoginListener(mLoginListener)
        }

        fragment.setConfiguration(builder.build())
        fragment.setCodeCreateListener(mCodeCreateListener)
        supportFragmentManager.commit {
            replace(R.id.container_view, fragment)
        }

    }


    private fun displayBiometricPrompt(biometricCallback: BiometricCallback) {
        BiometricManager.BiometricBuilder(this)
            .setTitle(getString(R.string.app_name))
            .setSubtitle(getString(R.string.fingerprint_confirm))
            .setDescription("")
            .setNegativeButtonText(getString(R.string.cancel))
            .build()
            .authenticate(biometricCallback)
    }

    private val mCodeCreateListener = PFLockScreenFragment.OnPFLockScreenCodeCreateListener { encodedCode ->
            Toast.makeText(this@SplashActivity, "Code created $encodedCode", Toast.LENGTH_SHORT).show()
            SharedPreferencesHelper.setSharedPreferenceString(this@SplashActivity, Constants.CODE, encodedCode)
        }

    private val mLoginListener = object : PFLockScreenFragment.OnPFLockScreenLoginListener {

        override fun onCodeInputSuccessful() {
            Toast.makeText(this@SplashActivity, "Code successful", Toast.LENGTH_SHORT).show()
            startActivity(mIntent)
            finish()
        }

        override fun onFingerprintSuccessful() {
            Toast.makeText(this@SplashActivity, "Fingerprint successful", Toast.LENGTH_SHORT).show()
            startActivity(mIntent)
            finish()
        }

        override fun onPinLoginFailed() {
            Toast.makeText(this@SplashActivity, "Pin failed", Toast.LENGTH_SHORT).show()
        }

        override fun onFingerprintLoginFailed() {
            Toast.makeText(this@SplashActivity, "Fingerprint failed", Toast.LENGTH_SHORT).show()
        }
    }

}
