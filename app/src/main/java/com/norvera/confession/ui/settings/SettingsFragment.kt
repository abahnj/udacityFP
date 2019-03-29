package com.norvera.confession.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.norvera.confession.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.pref_general, rootKey)
    }
}