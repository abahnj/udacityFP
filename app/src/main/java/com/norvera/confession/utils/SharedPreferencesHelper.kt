package com.norvera.confession.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object SharedPreferencesHelper {
    private const val PREF_FILE = "com.norvera.confession.debug_preferences"

    /**
     * Set a string shared preference
     * @param key - Key to set shared preference
     * @param value - Value for the key
     */
    fun setSharedPreferenceString(context: Context, key: String, value: String) {
        val settings = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        settings.edit{
            commit()
            putString(key, value)
        }
    }

    /**
     * Set a string shared preference
     * @param key - Key to set shared preference
     * @param value - Value for the key
     */
    fun registerOnSharedPreferenceChangeListener(context: Context, listener: SharedPreferences.OnSharedPreferenceChangeListener)  {
        return context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE).registerOnSharedPreferenceChangeListener(listener)
    }

    fun unregisterOnSharedPreferenceChangeListener(context: Context, listener: SharedPreferences.OnSharedPreferenceChangeListener?)  {
        return context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE).unregisterOnSharedPreferenceChangeListener(listener)
    }
    /**
     * Set a integer shared preference
     * @param key - Key to set shared preference
     * @param value - Value for the key
     */
    fun setSharedPreferenceInt(context: Context, key: String, value: Int) {
        val settings = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        settings.edit{
            putInt(key, value)
        }
    }

    /**
     * Set a Boolean shared preference
     * @param key - Key to set shared preference
     * @param value - Value for the key
     */
    fun setSharedPreferenceBoolean(context: Context, key: String, value: Boolean) {
        val settings = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        settings.edit{
            putBoolean(key, value)
        }
    }

    /**
     * Get a string shared preference
     * @param key - Key to look up in shared preferences.
     * @param defValue - Default value to be returned if shared preference isn't found.
     * @return value - String containing value of the shared preference if found.
     */
    fun getSharedPreferenceString(context: Context, key: String, defValue: String): String? {
        val settings = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        return settings.getString(key, defValue)
    }

    /**
     * Get a string shared preference
     * @param key - Key to look up in shared preferences.
     * @param defValue - Default value to be returned if shared preference isn't found.
     * @return value - Long containing value of the shared preference if found.
     */
    fun getSharedPreferenceLong(context: Context, key: String, defValue: Long): Long? {
        val settings = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        return settings.getLong(key, defValue)
    }
    /**
     * Set a long shared preference
     * @param key - Key to set shared preference
     * @param value - Value for the key
     */
    fun setSharedPreferenceLong(context: Context, key: String, value: Long) {
        val settings = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        settings.edit{
            putLong(key, value)
        }
    }
    /**
     * Get a integer shared preference
     * @param key - Key to look up in shared preferences.
     * @param defValue - Default value to be returned if shared preference isn't found.
     * @return value - String containing value of the shared preference if found.
     */
    fun getSharedPreferenceInt(context: Context, key: String, defValue: Int): Int {
        val settings = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        return settings.getInt(key, defValue)
    }

    /**
     * Get a boolean shared preference
     * @param key - Key to look up in shared preferences.
     * @param defValue - Default value to be returned if shared preference isn't found.
     * @return value - String containing value of the shared preference if found.
     */
    fun getSharedPreferenceBoolean(context: Context, key: String, defValue: Boolean): Boolean {
        val settings = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        return settings.getBoolean(key, defValue)
    }
}