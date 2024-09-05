package com.sixtyninefourtwenty.theming.preferences

import android.content.Context
import android.os.Build
import androidx.fragment.app.testing.launchFragment
import androidx.preference.PreferenceFragmentCompat
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sixtyninefourtwenty.theming.LightDarkMode
import com.sixtyninefourtwenty.theming.ThemeColor
import org.junit.Assert.assertArrayEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import kotlin.random.Random

@RunWith(AndroidJUnit4::class)
class LightDarkModePreferenceEntryValueTest {

    @Before
    fun setUp() {
        registerActivity("androidx.fragment.app.testing.EmptyFragmentActivity")
    }

    private fun testCommon(
        expectedEntries: Array<out CharSequence>,
        expectedEntryValues: Array<out CharSequence>
    ) {
        launchFragment<PreferenceFragmentCompat> { EmptyPreferenceFragment() }.use { scenario ->
            scenario.onFragment { fragment ->
                fragment.preferenceScreen = fragment.preferenceManager.createPreferenceScreen(fragment.requireContext()).apply {
                    addLightDarkModePreferenceWithDefaultSettings(
                        fragment.requireActivity(),
                        object : ImmutableThemingPreferencesSupplierWithoutM3CustomColor {
                            override val lightDarkMode: LightDarkMode
                                get() = LightDarkMode.entries.random()
                            override val md3: Boolean
                                get() = Random.nextBoolean()
                            override val themeColor: ThemeColor
                                get() = ThemeColor.entries.random()
                        }
                    )
                }
                val pref = fragment.preferenceScreen.getLightDarkModePreferenceWithDefaultSettings()
                assertArrayEquals(expectedEntries, pref.entries.toTypedArray())
                assertArrayEquals(expectedEntryValues, pref.entryValues.toTypedArray())
            }
        }
    }

    @Test
    @Config(maxSdk = Build.VERSION_CODES.P)
    fun onAndroid9ThenNoSystemValue() {
        testCommon(
            expectedEntries = ApplicationProvider.getApplicationContext<Context>().resources.getStringArray(R.array.themes_preference_entries_p),
            expectedEntryValues = ApplicationProvider.getApplicationContext<Context>().resources.getStringArray(R.array.themes_preference_entry_values_p)
        )
    }

    @Test
    @Config(minSdk = Build.VERSION_CODES.Q)
    fun onAndroid10ThenHasSystemValue() {
        testCommon(
            expectedEntries = ApplicationProvider.getApplicationContext<Context>().resources.getStringArray(R.array.themes_preference_entries),
            expectedEntryValues = ApplicationProvider.getApplicationContext<Context>().resources.getStringArray(R.array.themes_preference_entry_values)
        )
    }

}