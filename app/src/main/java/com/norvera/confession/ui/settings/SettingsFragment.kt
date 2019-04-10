package com.norvera.confession.ui.settings

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.format.DateUtils
import androidx.fragment.app.DialogFragment
import androidx.preference.Preference
import androidx.preference.PreferenceCategory
import androidx.preference.PreferenceFragmentCompat
import com.norvera.confession.R
import com.norvera.confession.utils.Constants
import timber.log.Timber


class SettingsFragment : PreferenceFragmentCompat(), Preference.OnPreferenceClickListener {

    companion object {
        const val TAG = "SETTINGS_FRAGMENT"
    }


    private var lastConfessionPref: Preference? = null
    private lateinit var removeAdsPref: Preference

    override fun onPreferenceClick(preference: Preference?): Boolean {
        when (preference?.key) {
            getString(com.norvera.confession.R.string.pref_privacy_policy) -> {
                openWebPage("http://norvera.com/privacy.html")
                return true
            }
        }
        return false
    }

    override fun onDisplayPreferenceDialog(preference: Preference) {
        // Try if the preference is one of our custom Preferences
        var dialogFragment: DialogFragment? = null
        if (preference is DatePreference) {
            // Create a new instance of DatePreferenceDialogFragment with the key of the related
            // Preference
            dialogFragment = DatePreferenceDialogFragmentCompat
                .newInstance(preference.key)
        }

        // If it was one of our custom Preferences, show its dialog
        if (dialogFragment != null) {
            dialogFragment.setTargetFragment(this, 0)
            dialogFragment.show(
                this.fragmentManager!!,
                "androidx.preference" + ".PreferenceFragment.DIALOG"
            )
        } else {
            super.onDisplayPreferenceDialog(preference)
        }// Could not be handled here. Try with the super method.
    }




    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.pref_general, rootKey)



        val helpPreference = findPreference<Preference>("help")
        val removeAdsPrefCat = findPreference<PreferenceCategory>(getString(R.string.pref_cat_remove_ads))


        removeAdsPref = findPreference(getString(R.string.pref_remove_ads))!!

        helpPreference?.setOnPreferenceClickListener {
            sendFeedback(context!!)
            true
        }

        lastConfessionPref = findPreference("last_confession")
        lastConfessionPref?.summaryProvider =
            Preference.SummaryProvider<Preference> { preference ->
                val lastConfessionLong =
                    preference.sharedPreferences.getLong(Constants.LAST_CONFESSION, 0L)
                val date = DateUtils.formatDateTime(
                    context,
                    lastConfessionLong,
                    DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR
                )
                date
            }

    }


    private fun openWebPage(url: String) {
        val webPage: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webPage)
        if (intent.resolveActivity(activity?.packageManager!!) != null) {
            startActivity(intent)
        }
    }

    /**
     * Email client intent to send support mail
     * Appends the necessary device information to email body
     * useful when providing support
     */
    private fun sendFeedback(context: Context) {
        var body: String? = null
        try {
            body =
                context.packageManager.getPackageInfo(context.packageName, 0).versionName
            body =
                "\n\n-----------------------------" +
                        "\nPlease don't remove this information" +
                        "\n Device OS: Android " +
                        "\n Device OS version: " + Build.VERSION.RELEASE +
                        "\n App Version: " + body +
                        "\n Device Brand: " + Build.BRAND +
                        "\n Device Model: " + Build.MODEL +
                        "\n Device Manufacturer: " + Build.MANUFACTURER
        } catch (e: PackageManager.NameNotFoundException) {
            Timber.e(e)
        }

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "message/rfc822"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("appsupport@norvera.com"))
        intent.putExtra(Intent.EXTRA_SUBJECT, "Query from android app")
        intent.putExtra(Intent.EXTRA_TEXT, body)
        context.startActivity(
            Intent.createChooser(
                intent,
                context.getString(com.norvera.confession.R.string.choose_email_client)
            )
        )
    }


}

