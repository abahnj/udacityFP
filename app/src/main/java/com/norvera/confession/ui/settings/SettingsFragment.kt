package com.norvera.confession.ui.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.norvera.confession.R

class SettingsFragment : PreferenceFragmentCompat(), Preference.OnPreferenceClickListener {
    override fun onPreferenceClick(preference: Preference?): Boolean {
        when (preference?.key) {
            getString(R.string.pref_privacy_policy) -> {
                openWebPage("http://norvera.com/privacy")
                return true
            }
        }
        return false
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.pref_general, rootKey)
         val privacyPolicyPref = findPreference<Preference>(getString(R.string.pref_privacy_policy))
        privacyPolicyPref?.setOnPreferenceClickListener {
            openWebPage("http://norvera.com/privacy.html")
            true
        }
    }

    private fun openWebPage(url: String) {
        val webPage: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webPage)
        if (intent.resolveActivity(activity?.packageManager!!) != null) {
            startActivity(intent)
        }
    }
}