package com.sixtyninefourtwenty.theming.preferences

import android.os.Bundle
import com.sixtyninefourtwenty.custompreferences.PreferenceFragmentCompatAccommodateCustomDialogPreferences

class EmptyPreferenceFragment : PreferenceFragmentCompatAccommodateCustomDialogPreferences() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) = Unit
}