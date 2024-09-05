package com.sixtyninefourtwenty.theming.sample

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.sixtyninefourtwenty.theming.preferences.ThemingPreferenceFragment
import com.sixtyninefourtwenty.theming.sample.databinding.ActivityLibraryInbuiltFragmentBinding
import com.sixtyninefourtwenty.theming.sample.utils.ToolbarActivity
import com.sixtyninefourtwenty.theming.sample.utils.addInfoItem
import com.sixtyninefourtwenty.theming.sample.utils.showInfoWithCodeSamplesDialog
import com.sixtyninefourtwenty.theming.sample.utils.theme

class LibraryInbuiltFragmentActivity : ToolbarActivity() {

    private lateinit var binding: ActivityLibraryInbuiltFragmentBinding
    private lateinit var infoMenuItem: MenuItem

    override fun createContentView(): View {
        binding = ActivityLibraryInbuiltFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        theme(preferencesSupplier = null)
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add<ThemingPreferenceFragment>(R.id.fragment_container)
            }
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
                    infoText = getString(R.string.inbuilt_fragment_sample_info),
                    kotlinCode = INBUILT_FRAGMENT_KOTLIN_SAMPLE,
                    javaCode = INBUILT_FRAGMENT_JAVA_SAMPLE
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}