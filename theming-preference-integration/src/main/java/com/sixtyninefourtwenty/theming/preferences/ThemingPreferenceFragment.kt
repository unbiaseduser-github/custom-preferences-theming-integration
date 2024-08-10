package com.sixtyninefourtwenty.theming.preferences

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceGroup
import com.sixtyninefourtwenty.custompreferences.PreferenceFragmentCompatAccommodateCustomDialogPreferences

/**
 * Pre-made [PreferenceFragmentCompat] that contains theming preferences added by
 * [PreferenceGroup.addThemingPreferencesWithDefaultSettings].
 *
 * Note that this only supports apps with all 3 themes: M2, M3 custom colors and M3 dynamic colors.
 * If your app only supports M2 and M3 dynamic colors, do *not* use this. Instead, use
 * the `add...PreferenceWithoutM3CustomColor` methods or [PreferenceGroup.addThemingPreferencesWithoutM3CustomColorWithDefaultSettings]
 * in a custom preference fragment.
 */
class ThemingPreferenceFragment : PreferenceFragmentCompatAccommodateCustomDialogPreferences() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        val prefs = DefaultThemingPreferences.getInstance(requireContext())
        preferenceManager.preferenceDataStore = prefs
        preferenceScreen = preferenceManager.createPreferenceScreen(requireContext()).apply {
            addThemingPreferencesWithDefaultSettings(requireActivity(), prefs)
        }
    }

}