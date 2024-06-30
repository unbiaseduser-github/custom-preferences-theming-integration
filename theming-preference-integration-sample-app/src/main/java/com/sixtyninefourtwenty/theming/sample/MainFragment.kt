package com.sixtyninefourtwenty.theming.sample

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.sixtyninefourtwenty.theming.sample.inbuiltprefs.LibraryInbuiltPreferencesActivity
import com.sixtyninefourtwenty.theming.sample.inmemory.InMemoryThemingActivity
import com.sixtyninefourtwenty.theming.sample.utils.startActivity

class MainFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        val context = requireContext()
        preferenceScreen = preferenceManager.createPreferenceScreen(context).apply {
            addPreference(Preference(context).apply {
                title = getString(R.string.activity_with_library_inbuilt_fragment)
                isIconSpaceReserved = false
                setOnPreferenceClickListener {
                    context.startActivity<LibraryInbuiltFragmentActivity>()
                    true
                }
            })
            addPreference(Preference(context).apply {
                title = getString(R.string.activity_with_library_individual_inbuilt_preferences)
                isIconSpaceReserved = false
                setOnPreferenceClickListener {
                    context.startActivity<LibraryInbuiltPreferencesActivity>()
                    true
                }
            })
            addPreference(Preference(context).apply {
                title = getString(R.string.activity_with_non_persistent_theming)
                isIconSpaceReserved = false
                setOnPreferenceClickListener {
                    context.startActivity<InMemoryThemingActivity>()
                    true
                }
            })
        }
    }

}