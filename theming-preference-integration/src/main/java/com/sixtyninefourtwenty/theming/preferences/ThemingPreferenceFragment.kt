package com.sixtyninefourtwenty.theming.preferences

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceGroup
import com.sixtyninefourtwenty.custompreferences.AbstractCustomDialogPreference

/**
 * Pre-made [PreferenceFragmentCompat] that contains theming preferences added by
 * [PreferenceGroup.addThemingPreferences]. This fragment uses an internal preferences storage.
 *
 * Note that this only supports apps with all 3 themes: M2, M3 custom colors and M3 dynamic colors.
 * If your app only supports M2 and M3 dynamic colors, do *not* use this. Instead, use
 * [PreferenceGroup.addThemingPreferencesWithoutM3CustomColor] in a custom preference fragment.
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