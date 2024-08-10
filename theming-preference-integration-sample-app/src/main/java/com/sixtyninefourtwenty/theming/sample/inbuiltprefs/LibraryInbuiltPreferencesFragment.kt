package com.sixtyninefourtwenty.theming.sample.inbuiltprefs

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceCategory
import com.sixtyninefourtwenty.custompreferences.PreferenceFragmentCompatAccommodateCustomDialogPreferences
import com.sixtyninefourtwenty.theming.preferences.addThemingPreferencesWithDefaultSettings
import com.sixtyninefourtwenty.theming.sample.R
import com.sixtyninefourtwenty.theming.sample.utils.myApplication

class LibraryInbuiltPreferencesFragment : PreferenceFragmentCompatAccommodateCustomDialogPreferences() {

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
        appearanceCategory.addThemingPreferencesWithDefaultSettings(
            activity = requireActivity(),
            preferenceSupplier = themingPrefs
        )
        someOtherCategory.addPreference(Preference(context).apply {
            title = getString(R.string.your_preference)
        })
    }

}