package com.norvera.confession;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.norvera.confession.utils.BiometricCallback;
import com.norvera.confession.utils.BiometricManager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        intent = new Intent(SplashActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Toast.makeText(SplashActivity.this, getString(R.string.biometric_success), Toast.LENGTH_LONG).show();

        displayBiometricPrompt(biometricCallback);

    }


    private final BiometricCallback biometricCallback = new BiometricCallback() {
        @Override
        public void onSdkVersionNotSupported() {
            Toast.makeText(SplashActivity.this, getString(R.string.biometric_error_sdk_not_supported), Toast.LENGTH_LONG).show();
            startActivity(intent);
            finish();
        }

        @Override
        public void onBiometricAuthenticationNotSupported() {
            Toast.makeText(SplashActivity.this, getString(R.string.biometric_error_hardware_not_supported), Toast.LENGTH_LONG).show();
            startActivity(intent);
            finish();
        }

        @Override
        public void onBiometricAuthenticationNotAvailable() {
            Toast.makeText(SplashActivity.this, getString(R.string.biometric_error_fingerprint_not_available), Toast.LENGTH_LONG).show();
            startActivity(intent);
            finish();
        }

        @Override
        public void onBiometricAuthenticationPermissionNotGranted() {
            Toast.makeText(SplashActivity.this, getString(R.string.biometric_error_permission_not_granted), Toast.LENGTH_LONG).show();
            startActivity(intent);
            finish();
        }

        @Override
        public void onBiometricAuthenticationInternalError(String error) {
            Toast.makeText(SplashActivity.this, error, Toast.LENGTH_LONG).show();
            startActivity(intent);
            finish();
        }

        @Override
        public void onAuthenticationFailed() {
            Toast.makeText(SplashActivity.this, getString(R.string.biometric_failure), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onAuthenticationCancelled() {
            Toast.makeText(SplashActivity.this, getString(R.string.biometric_cancelled), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onAuthenticationSuccessful() {

            new Handler().postDelayed(() -> {
                startActivity(intent);
                finish();
            }, 500);

        }

        @Override
        public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
            Toast.makeText(SplashActivity.this, helpString, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onAuthenticationError(int errorCode, CharSequence errString) {
            Toast.makeText(SplashActivity.this, errString, Toast.LENGTH_LONG).show();
        }
    };


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void displayBiometricPrompt(final BiometricCallback biometricCallback) {
        new BiometricManager.BiometricBuilder(this)
                .setTitle(getString(R.string.app_name))
                .setSubtitle(getString(R.string.fingerprint_confirm))
                .setDescription("")
                .setNegativeButtonText(getString(R.string.cancel))
                .build()
                .authenticate(biometricCallback);
    }
}
