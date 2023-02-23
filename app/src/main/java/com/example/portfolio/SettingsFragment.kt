package com.example.portfolio

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        val themePreference = preferenceManager.findPreference<SwitchPreference>("theme_preference")
        themePreference?.setOnPreferenceChangeListener{ _, value ->
            setThemeMode(value as Boolean)
            true
        }
    }
}

private fun setThemeMode(isLight: Boolean){
    if (isLight) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
}