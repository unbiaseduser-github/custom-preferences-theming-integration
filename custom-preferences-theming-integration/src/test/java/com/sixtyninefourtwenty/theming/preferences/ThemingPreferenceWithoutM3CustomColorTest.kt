package com.sixtyninefourtwenty.theming.preferences

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
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.ParameterizedRobolectricTestRunner
import org.robolectric.ParameterizedRobolectricTestRunner.Parameters

@RunWith(ParameterizedRobolectricTestRunner::class)
class ThemingPreferenceWithoutM3CustomColorTest(
    private val preferencesSupplier: ImmutableThemingPreferencesSupplierWithoutM3CustomColor,
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
            val commonLightDarkModePreferenceAssertion = Consumer<ToggleGroupPreference> {
                assertEquals(lightDarkMode.prefValue, it.value)
            }
            return listOf(
                arrayOf(
                    ImplWithoutM3CustomColor(
                        md3 = true,
                        themeColor = themeColor,
                        lightDarkMode = lightDarkMode
                    ),
                    Consumer<TwoStatePreference> { assertTrue(it.isChecked) },
                    Consumer<PredefinedColorPickerPreference> {
                        assertEquals(themeColor.getColorInt(ApplicationProvider.getApplicationContext()), it.color)
                        assertFalse(it.isEnabled)
                    },
                    commonLightDarkModePreferenceAssertion
                ),
                arrayOf(
                    ImplWithoutM3CustomColor(
                        md3 = false,
                        themeColor = themeColor,
                        lightDarkMode = lightDarkMode
                    ),
                    Consumer<TwoStatePreference> { assertFalse(it.isChecked) },
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
                    addThemingPreferencesWithoutM3CustomColorWithDefaultSettings(fragment.requireActivity(), preferencesSupplier)
                }
                assertMd3.accept(fragment.preferenceScreen.getM3PreferenceWithDefaultSettings())
                assertThrows(IllegalStateException::class.java) {
                    fragment.preferenceScreen.getUseMD3CustomColorsThemeOnAndroid12PreferenceWithDefaultSettings()
                }
                assertThemeColor.accept(fragment.preferenceScreen.getThemeColorPreferenceWithDefaultSettings())
                assertLightDarkMode.accept(fragment.preferenceScreen.getLightDarkModePreferenceWithDefaultSettings())
            }
        }
    }

}

private class ImplWithoutM3CustomColor(
    override val md3: Boolean,
    override val themeColor: ThemeColor,
    override val lightDarkMode: LightDarkMode
) : ImmutableThemingPreferencesSupplierWithoutM3CustomColor