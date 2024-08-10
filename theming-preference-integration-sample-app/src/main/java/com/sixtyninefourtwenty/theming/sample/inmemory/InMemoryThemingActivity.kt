package com.sixtyninefourtwenty.theming.sample.inmemory

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.graphics.toColorInt
import androidx.core.view.isVisible
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sixtyninefourtwenty.theming.LightDarkMode
import com.sixtyninefourtwenty.theming.ThemeColor
import com.sixtyninefourtwenty.theming.preferences.ThemingPreferencesSupplier
import com.sixtyninefourtwenty.theming.sample.CUSTOM_PREFERENCE_SUPPLIER_JAVA_SAMPLE
import com.sixtyninefourtwenty.theming.sample.CUSTOM_PREFERENCE_SUPPLIER_KOTLIN_SAMPLE
import com.sixtyninefourtwenty.theming.sample.R
import com.sixtyninefourtwenty.theming.sample.databinding.ActivityInMemoryThemingBinding
import com.sixtyninefourtwenty.theming.sample.utils.ToolbarActivity
import com.sixtyninefourtwenty.theming.sample.utils.addInfoItem
import com.sixtyninefourtwenty.theming.sample.utils.showInfoWithCodeSamplesDialog
import com.sixtyninefourtwenty.theming.sample.utils.theme

class InMemoryThemingActivity : ToolbarActivity() {

    private lateinit var binding: ActivityInMemoryThemingBinding
    private val themePref: ThemingPreferencesSupplier = InMemoryThemingPreferenceSupplier
    private lateinit var infoMenuItem: MenuItem

    override fun createContentView(): View {
        binding = ActivityInMemoryThemingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        theme(preferencesSupplier = themePref)
        super.onCreate(savedInstanceState)
        with(binding) {
            md3Card.setOnClickListener {
                md3Cb.toggle()
            }
            with(md3Cb) {
                isChecked = themePref.md3
                setOnCheckedChangeListener { _, isChecked ->
                    themePref.md3 = isChecked
                    recreate()
                }
            }
            themeCard.setOnClickListener {
                fun determineChoice(lightDarkMode: LightDarkMode): Int {
                    return when (lightDarkMode) {
                        LightDarkMode.LIGHT -> 0
                        LightDarkMode.DARK -> 1
                        LightDarkMode.BATTERY -> 2
                        LightDarkMode.SYSTEM -> 3
                    }
                }

                var choice = determineChoice(themePref.lightDarkMode)
                MaterialAlertDialogBuilder(this@InMemoryThemingActivity)
                    .setSingleChoiceItems(arrayOf(
                        getString(R.string.light),
                        getString(R.string.dark),
                        getString(R.string.battery_saver),
                        getString(R.string.system)
                    ), determineChoice(themePref.lightDarkMode)) { _, which ->
                        choice = which
                    }
                    .setNegativeButton(android.R.string.cancel, null)
                    .setPositiveButton(android.R.string.ok) { _, _ ->
                        themePref.lightDarkMode = when (choice) {
                            0 -> LightDarkMode.LIGHT
                            1 -> LightDarkMode.DARK
                            2 -> LightDarkMode.BATTERY
                            3 -> LightDarkMode.SYSTEM
                            else -> error("Unknown choice: $choice")
                        }
                        recreate()
                    }
                    .show()
            }
            themeIcon.setImageResource(when (themePref.lightDarkMode) {
                LightDarkMode.LIGHT -> R.drawable.light_mode
                LightDarkMode.DARK -> R.drawable.dark_mode
                LightDarkMode.SYSTEM -> R.drawable.android
                LightDarkMode.BATTERY -> R.drawable.battery_saver
            })
            with(md3CustomColorsA12Card) {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.R || !themePref.md3) {
                    isVisible = false
                }
                setOnClickListener {
                    md3CustomColorsA12Cb.toggle()
                }
            }
            with(md3CustomColorsA12Cb) {
                isChecked = themePref.useM3CustomColorThemeOnAndroid12
                setOnCheckedChangeListener { _, isChecked ->
                    themePref.useM3CustomColorThemeOnAndroid12 = isChecked
                    recreate()
                }
            }
            with(themeColorCard) {
                if (!themePref.useM3CustomColorThemeOnAndroid12 && themePref.md3 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    isVisible = false
                }
                setOnClickListener {
                    fun determineChoice(themeColor: ThemeColor): Int {
                        return when (themeColor) {
                            ThemeColor.BLUE -> 0
                            ThemeColor.RED -> 1
                            ThemeColor.GREEN -> 2
                            ThemeColor.PURPLE -> 3
                            ThemeColor.ORANGE -> 4
                            ThemeColor.PINK -> 5
                        }
                    }

                    var choice = determineChoice(themePref.themeColor)
                    MaterialAlertDialogBuilder(this@InMemoryThemingActivity)
                        .setSingleChoiceItems(arrayOf(
                            getString(R.string.blue),
                            getString(R.string.red),
                            getString(R.string.green),
                            getString(R.string.purple),
                            getString(R.string.orange),
                            getString(R.string.pink)
                        ), determineChoice(themePref.themeColor)) { _, which ->
                            choice = which
                        }
                        .setNegativeButton(android.R.string.cancel, null)
                        .setPositiveButton(android.R.string.ok) { _, _ ->
                            themePref.themeColor = when (choice) {
                                0 -> ThemeColor.BLUE
                                1 -> ThemeColor.RED
                                2 -> ThemeColor.GREEN
                                3 -> ThemeColor.PURPLE
                                4 -> ThemeColor.ORANGE
                                5 -> ThemeColor.PINK
                                else -> error("Unknown choice: $choice")
                            }
                            recreate()
                        }
                        .show()
                }
            }
            themeColorIcon.setBackgroundColor(when (themePref.themeColor) {
                ThemeColor.BLUE -> "#3385ff".toColorInt()
                ThemeColor.RED -> "#d8260e".toColorInt()
                ThemeColor.GREEN -> "#00c71e".toColorInt()
                ThemeColor.PURPLE -> "#7700f6".toColorInt()
                ThemeColor.ORANGE -> "#ff8800".toColorInt()
                ThemeColor.PINK -> "#e700ef".toColorInt()
            })
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
                    infoText = getString(R.string.custom_prefs_sample_info),
                    kotlinCode = CUSTOM_PREFERENCE_SUPPLIER_KOTLIN_SAMPLE,
                    javaCode = CUSTOM_PREFERENCE_SUPPLIER_JAVA_SAMPLE
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}