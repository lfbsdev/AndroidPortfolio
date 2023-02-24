package com.example.portfolio

import android.content.res.Configuration.*
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceScreen
import androidx.preference.SwitchPreference

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        val themePreference = preferenceManager.findPreference<SwitchPreference>("theme_preference")
        themePreference?.setOnPreferenceChangeListener{ _, _ ->
            toggleThemeMode(preferenceScreen)
            true
        }
    }
}

private fun toggleThemeMode(preferenceScreen: PreferenceScreen){
    val nightModeFlags: Int = preferenceScreen.context.resources.configuration.uiMode and UI_MODE_NIGHT_MASK
    val oppositeNightMode: Int = if (nightModeFlags == UI_MODE_NIGHT_NO) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
    AppCompatDelegate.setDefaultNightMode(oppositeNightMode)
}