package com.sixtyninefourtwenty.theming.sample.inbuiltprefs

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.sixtyninefourtwenty.theming.sample.INBUILT_PREFS_JAVA_SAMPLE
import com.sixtyninefourtwenty.theming.sample.INBUILT_PREFS_KOTLIN_SAMPLE
import com.sixtyninefourtwenty.theming.sample.R
import com.sixtyninefourtwenty.theming.sample.databinding.ActivityLibraryInbuiltPreferencesBinding
import com.sixtyninefourtwenty.theming.sample.utils.ToolbarActivity
import com.sixtyninefourtwenty.theming.sample.utils.addInfoItem
import com.sixtyninefourtwenty.theming.sample.utils.myApplication
import com.sixtyninefourtwenty.theming.sample.utils.showInfoWithCodeSamplesDialog
import com.sixtyninefourtwenty.theming.sample.utils.theme

class LibraryInbuiltPreferencesActivity : ToolbarActivity() {

    private lateinit var binding: ActivityLibraryInbuiltPreferencesBinding
    private lateinit var infoMenuItem: MenuItem

    override fun createContentView(): View {
        binding = ActivityLibraryInbuiltPreferencesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val themePrefs = myApplication.defaultSharedPreferencesBackedPreferenceDataStoreBackedThemingPreferenceSupplier
        theme(preferencesSupplier = themePrefs)
        super.onCreate(savedInstanceState)

        supportFragmentManager.commit {
            add<LibraryInbuiltPreferencesFragment>(R.id.fragment_container)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        infoMenuItem = menu.addInfoItem()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item) {
            infoMenuItem -> {
                showInfoWithCodeSamplesDialog(
                    infoText = getString(R.string.inbuilt_prefs_sample_info),
                    kotlinCode = INBUILT_PREFS_KOTLIN_SAMPLE,
                    javaCode = INBUILT_PREFS_JAVA_SAMPLE
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}