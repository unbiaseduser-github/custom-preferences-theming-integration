package com.sixtyninefourtwenty.theming.preferences

import android.os.Build
import androidx.core.util.Consumer
import androidx.fragment.app.testing.launchFragment
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.TwoStatePreference
import androidx.test.core.app.ApplicationProvider
import com.sixtyninefourtwenty.custompreferences.PredefinedColorPickerPreference
import com.sixtyninefourtwenty.custompreferences.ToggleGroupPreference
import com.sixtyninefourtwenty.theming.LightDarkMode
import com.sixtyninefourtwenty.theming.ThemeColor
import com.sixtyninefourtwenty.theming.preferences.internal.getColorInt
import com.sixtyninefourtwenty.theming.preferences.internal.prefValue
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.ParameterizedRobolectricTestRunner
import org.robolectric.ParameterizedRobolectricTestRunner.Parameters
import org.robolectric.annotation.Config

@RunWith(ParameterizedRobolectricTestRunner::class)
@Config(maxSdk = Build.VERSION_CODES.R)
class Android11ThemingPreferenceUITest(
    private val preferencesSupplier: ImmutableThemingPreferencesSupplier,
    private val assertMd3: Consumer<TwoStatePreference>,
    private val assertThemeColor: Consumer<PredefinedColorPickerPreference>,
    private val assertLightDarkMode: Consumer<ToggleGroupPreference>
) {
    companion object {
        @JvmStatic
        @Parameters
        @Suppress("unused")
        fun args(): Iterable<Array<Any>> {
            val themeColor = ThemeColor.entries.random()
            val lightDarkMode = LightDarkMode.entries.random()
            val commonColorPickerPreferenceAssertion = Consumer<PredefinedColorPickerPreference> {
                assertEquals(themeColor.getColorInt(ApplicationProvider.getApplicationContext()), it.color)
                assertTrue(it.isEnabled)
            }
            val commonLightDarkModePreferenceAssertion = Consumer<ToggleGroupPreference> {
                assertEquals(lightDarkMode.prefValue, it.value)
            }
            return listOf(
                arrayOf(
                    Impl(
                        md3 = true,
                        useM3CustomColorThemeOnAndroid12 = true,
                        themeColor = themeColor,
                        lightDarkMode = lightDarkMode
                    ),
                    Consumer<TwoStatePreference> { assertTrue(it.isChecked) },
                    commonColorPickerPreferenceAssertion,
                    commonLightDarkModePreferenceAssertion
                ),
                arrayOf(
                    Impl(
                        md3 = true,
                        useM3CustomColorThemeOnAndroid12 = false,
                        themeColor = themeColor,
                        lightDarkMode = lightDarkMode
                    ),
                    Consumer<TwoStatePreference> { assertTrue(it.isChecked) },
                    commonColorPickerPreferenceAssertion,
                    commonLightDarkModePreferenceAssertion
                ),
                arrayOf(
                    Impl(
                        md3 = false,
                        useM3CustomColorThemeOnAndroid12 = true,
                        themeColor = themeColor,
                        lightDarkMode = lightDarkMode
                    ),
                    Consumer<TwoStatePreference> { assertFalse(it.isChecked) },
                    commonColorPickerPreferenceAssertion,
                    commonLightDarkModePreferenceAssertion
                ),
                arrayOf(
                    Impl(
                        md3 = false,
                        useM3CustomColorThemeOnAndroid12 = false,
                        themeColor = themeColor,
                        lightDarkMode = lightDarkMode
                    ),
                    Consumer<TwoStatePreference> { assertFalse(it.isChecked) },
                    commonColorPickerPreferenceAssertion,
                    commonLightDarkModePreferenceAssertion
                )
            )
        }
    }

    @Before
    fun setUp() {
        registerActivity("androidx.fragment.app.testing.EmptyFragmentActivity")
    }

    @Test
    fun test() {
        launchFragment<PreferenceFragmentCompat> { EmptyPreferenceFragment() }.use { scenario ->
            scenario.onFragment { fragment ->
                fragment.preferenceScreen = fragment.preferenceManager.createPreferenceScreen(fragment.requireContext()).apply {
                    addThemingPreferencesWithDefaultSettings(fragment.requireActivity(), preferencesSupplier)
                }
                assertMd3.accept(fragment.findPreference(DefaultThemingPreferences.MD3_KEY)!!)
                assertFalse(fragment.findPreference<TwoStatePreference>(DefaultThemingPreferences.USE_MD3_CUSTOM_COLORS_ON_ANDROID_12)!!.isVisible)
                assertThemeColor.accept(fragment.findPreference(DefaultThemingPreferences.PRIMARY_COLOR_KEY)!!)
                assertLightDarkMode.accept(fragment.findPreference(DefaultThemingPreferences.LIGHT_DARK_MODE_KEY)!!)
            }
        }
    }

}

@RunWith(ParameterizedRobolectricTestRunner::class)
@Config(minSdk = Build.VERSION_CODES.S)
class Android12ThemingPreferenceUITest(
    private val preferencesSupplier: ImmutableThemingPreferencesSupplier,
    private val assertMd3: Consumer<TwoStatePreference>,
    private val assertUseM3CustomColorOnAndroid12: Consumer<TwoStatePreference>,
    private val assertThemeColor: Consumer<PredefinedColorPickerPreference>,
    private val assertLightDarkMode: Consumer<ToggleGroupPreference>
) {
    companion object {
        @JvmStatic
        @Parameters
        @Suppress("unused")
        fun args(): Iterable<Array<Any>> {
            val themeColor = ThemeColor.entries.random()
            val lightDarkMode = LightDarkMode.entries.random()
            val commonLightDarkModePreferenceAssertion = Consumer<ToggleGroupPreference> {
                assertEquals(lightDarkMode.prefValue, it.value)
            }
            return listOf(
                arrayOf(
                    Impl(
                        md3 = true,
                        useM3CustomColorThemeOnAndroid12 = true,
                        themeColor = themeColor,
                        lightDarkMode = lightDarkMode
                    ),
                    Consumer<TwoStatePreference> { assertTrue(it.isChecked) },
                    Consumer<TwoStatePreference> { assertTrue(it.isChecked) },
                    Consumer<PredefinedColorPickerPreference> {
                        assertEquals(themeColor.getColorInt(ApplicationProvider.getApplicationContext()), it.color)
                        assertTrue(it.isEnabled)
                    },
                    commonLightDarkModePreferenceAssertion
                ),
                arrayOf(
                    Impl(
                        md3 = true,
                        useM3CustomColorThemeOnAndroid12 = false,
                        themeColor = themeColor,
                        lightDarkMode = lightDarkMode
                    ),
                    Consumer<TwoStatePreference> { assertTrue(it.isChecked) },
                    Consumer<TwoStatePreference> { assertFalse(it.isChecked) },
                    Consumer<PredefinedColorPickerPreference> {
                        assertEquals(themeColor.getColorInt(ApplicationProvider.getApplicationContext()), it.color)
                        assertFalse(it.isEnabled)
                    },
                    commonLightDarkModePreferenceAssertion
                ),
                arrayOf(
                    Impl(
                        md3 = false,
                        useM3CustomColorThemeOnAndroid12 = true,
                        themeColor = themeColor,
                        lightDarkMode = lightDarkMode
                    ),
                    Consumer<TwoStatePreference> { assertFalse(it.isChecked) },
                    Consumer<TwoStatePreference> {
                        assertTrue(it.isChecked)
                        assertFalse(it.isEnabled)
                    },
                    Consumer<PredefinedColorPickerPreference> {
                        assertEquals(themeColor.getColorInt(ApplicationProvider.getApplicationContext()), it.color)
                        assertTrue(it.isEnabled)
                    },
                    commonLightDarkModePreferenceAssertion
                ),
                arrayOf(
                    Impl(
                        md3 = false,
                        useM3CustomColorThemeOnAndroid12 = false,
                        themeColor = themeColor,
                        lightDarkMode = lightDarkMode
                    ),
                    Consumer<TwoStatePreference> { assertFalse(it.isChecked) },
                    Consumer<TwoStatePreference> {
                        assertFalse(it.isChecked)
                        assertFalse(it.isEnabled)
                    },
                    Consumer<PredefinedColorPickerPreference> {
                        assertEquals(themeColor.getColorInt(ApplicationProvider.getApplicationContext()), it.color)
                        assertTrue(it.isEnabled)
                    },
                    commonLightDarkModePreferenceAssertion
                )
            )
        }
    }

    @Before
    fun setUp() {
        registerActivity("androidx.fragment.app.testing.EmptyFragmentActivity")
    }

    @Test
    fun test() {
        launchFragment<PreferenceFragmentCompat> { EmptyPreferenceFragment() }.use { scenario ->
            scenario.onFragment { fragment ->
                fragment.preferenceScreen = fragment.preferenceManager.createPreferenceScreen(fragment.requireContext()).apply {
                    addThemingPreferencesWithDefaultSettings(fragment.requireActivity(), preferencesSupplier)
                }
                assertMd3.accept(fragment.findPreference(DefaultThemingPreferences.MD3_KEY)!!)
                assertUseM3CustomColorOnAndroid12.accept(fragment.findPreference(DefaultThemingPreferences.USE_MD3_CUSTOM_COLORS_ON_ANDROID_12)!!)
                assertThemeColor.accept(fragment.findPreference(DefaultThemingPreferences.PRIMARY_COLOR_KEY)!!)
                assertLightDarkMode.accept(fragment.findPreference(DefaultThemingPreferences.LIGHT_DARK_MODE_KEY)!!)
            }
        }
    }

}

private class Impl(
    override val md3: Boolean,
    override val useM3CustomColorThemeOnAndroid12: Boolean,
    override val themeColor: ThemeColor = ThemeColor.BLUE,
    override val lightDarkMode: LightDarkMode = LightDarkMode.SYSTEM
) : ImmutableThemingPreferencesSupplier