package com.sixtyninefourtwenty.theming.preferences

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceGroup
import com.sixtyninefourtwenty.custompreferences.AbstractCustomDialogPreference

/**
 * Pre-made [PreferenceFragmentCompat] that contains theming preferences added by
 * [PreferenceGroup.addThemingPreferences]. This fragment uses an internal preferences storage.
 */
class ThemingPreferenceFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        val prefs = DefaultThemingPreferences.getInstance(requireContext())
        preferenceManager.preferenceDataStore = prefs
        preferenceScreen = preferenceManager.createPreferenceScreen(requireContext()).apply {
            addThemingPreferences(requireActivity(), prefs.lightDarkMode, prefs.md3, prefs.useM3CustomColorThemeOnAndroid12)
        }
    }

    override fun onDisplayPreferenceDialog(preference: Preference) {
        if (preference is AbstractCustomDialogPreference) {
            preference.displayDialog(this)
        } else {
            super.onDisplayPreferenceDialog(preference)
        }
    }

}