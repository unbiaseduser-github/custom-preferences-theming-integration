package com.sixtyninefourtwenty.theming.sample.inbuiltprefs.nom3customcolor

import android.os.Bundle
import android.view.View
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.sixtyninefourtwenty.theming.sample.R
import com.sixtyninefourtwenty.theming.sample.databinding.ActivityLibraryInbuiltPreferencesBinding
import com.sixtyninefourtwenty.theming.sample.utils.ToolbarActivity
import com.sixtyninefourtwenty.theming.sample.utils.myApplication
import com.sixtyninefourtwenty.theming.sample.utils.themeWithoutM3CustomColor

class LibraryInbuiltPreferencesActivityNoM3CustomColor : ToolbarActivity() {

    private lateinit var binding: ActivityLibraryInbuiltPreferencesBinding

    override fun createContentView(): View {
        binding = ActivityLibraryInbuiltPreferencesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val themePrefs = myApplication.defaultSharedPreferencesBackedPreferenceDataStoreBackedThemingPreferenceSupplierWithoutM3CustomColor
        themeWithoutM3CustomColor(preferencesSupplier = themePrefs)
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add<LibraryInbuiltPreferencesFragmentNoM3CustomColor>(R.id.fragment_container)
            }
        }
    }

}