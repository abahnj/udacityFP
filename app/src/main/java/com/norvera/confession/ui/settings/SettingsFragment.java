package com.norvera.confession.ui.settings;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.norvera.confession.R;

import androidx.preference.PreferenceFragmentCompat;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.pref_general, rootKey);
    }
}