package com.sixtyninefourtwenty.theming.sample.inbuiltprefs.nom3customcolor

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceCategory
import com.sixtyninefourtwenty.custompreferences.PreferenceFragmentCompatAccommodateCustomDialogPreferences
import com.sixtyninefourtwenty.theming.preferences.addThemingPreferencesWithoutM3CustomColorWithDefaultSettings
import com.sixtyninefourtwenty.theming.sample.R
import com.sixtyninefourtwenty.theming.sample.utils.myApplication

class LibraryInbuiltPreferencesFragmentNoM3CustomColor : PreferenceFragmentCompatAccommodateCustomDialogPreferences() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        val context = requireContext()
        val app = context.myApplication
        val prefs = app.defaultSharedPreferencesBackedPreferenceDataStore2
        val themingPrefs = app.defaultSharedPreferencesBackedPreferenceDataStoreBackedThemingPreferenceSupplierWithoutM3CustomColor
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
        appearanceCategory.addThemingPreferencesWithoutM3CustomColorWithDefaultSettings(
            activity = requireActivity(),
            preferenceSupplier = themingPrefs
        )
        someOtherCategory.addPreference(Preference(context).apply {
            title = getString(R.string.your_preference)
        })
    }

}