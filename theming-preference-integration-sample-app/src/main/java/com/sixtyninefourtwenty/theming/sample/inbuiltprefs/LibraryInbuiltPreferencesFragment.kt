package com.sixtyninefourtwenty.theming.sample.inbuiltprefs

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceCategory
import androidx.preference.PreferenceFragmentCompat
import com.sixtyninefourtwenty.theming.preferences.addThemingPreferences
import com.sixtyninefourtwenty.theming.sample.R
import com.sixtyninefourtwenty.theming.sample.utils.myApplication

class LibraryInbuiltPreferencesFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        val context = requireContext()
        val app = context.myApplication
        val prefs = app.defaultSharedPreferencesBackedPreferenceDataStore
        val themingPrefs = app.defaultSharedPreferencesBackedPreferenceDataStoreBackedThemingPreferenceSupplier
        preferenceManager.preferenceDataStore = prefs
        val appearanceCategory = PreferenceCategory(context).apply {
            title = getString(R.string.appearance_settings)
        }
        val someOtherCategory = PreferenceCategory(context).apply {
            title = getString(R.string.your_category)
        }
        preferenceScreen = preferenceManager.createPreferenceScreen(context).apply {
            addPreference(appearanceCategory)
            addPreference(someOtherCategory)
        }
        appearanceCategory.addThemingPreferences(
            activity = requireActivity(),
            lightDarkMode = themingPrefs.lightDarkMode,
            md3 = themingPrefs.md3,
            themeColorPreferenceAlwaysEnabledOnAndroid12 = themingPrefs.useM3CustomColorThemeOnAndroid12
        )
        someOtherCategory.addPreference(Preference(context).apply {
            title = getString(R.string.your_preference)
        })
    }

}