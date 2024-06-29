@file:JvmName("ThemingPreferenceUIWithoutM3CustomColor")
@file:Suppress("unused")

package com.sixtyninefourtwenty.theming.preferences

import android.app.Activity
import androidx.annotation.ColorInt
import androidx.preference.PreferenceGroup
import com.sixtyninefourtwenty.custompreferences.PredefinedColorPickerPreference
import com.sixtyninefourtwenty.theming.LightDarkMode
import com.sixtyninefourtwenty.theming.preferences.internal.setupCommon

/**
 * Calls [addM3PreferenceWithoutM3CustomColor], [addLightDarkModePreferenceWithoutM3CustomColor]
 * and [addThemeColorPreferenceWithoutM3CustomColor] in that exact order.
 */
@JvmOverloads
fun PreferenceGroup.addThemingPreferencesWithoutM3CustomColor(
    activity: Activity,
    lightDarkMode: LightDarkMode,
    md3: Boolean,
    m3PrefKey: String = DefaultThemingPreferences.MD3_KEY,
    lightDarkModePrefKey: String = DefaultThemingPreferences.LIGHT_DARK_MODE_KEY,
    lightDarkModePrefEntries: Array<CharSequence>? = null,
    lightDarkModePrefEntryValues: Array<CharSequence>? = null,
    themeColorPrefKey: String = DefaultThemingPreferences.PRIMARY_COLOR_KEY,
    @ColorInt themeColorPrefColors: IntArray? = null
) {
    addM3PreferenceWithoutM3CustomColor(activity, m3PrefKey)
    addLightDarkModePreferenceWithoutM3CustomColor(activity, lightDarkMode, lightDarkModePrefKey, lightDarkModePrefEntries, lightDarkModePrefEntryValues)
    addThemeColorPreferenceWithoutM3CustomColor(activity, md3, themeColorPrefKey, themeColorPrefColors)
}

/**
 * Version of [PreferenceGroup.addM3Preference] for apps that don't support M3 custom color theme.
 */
@JvmOverloads
fun PreferenceGroup.addM3PreferenceWithoutM3CustomColor(
    activity: Activity,
    prefKey: String = DefaultThemingPreferences.MD3_KEY
) = addM3Preference(activity, prefKey) //No change.

/**
 * Version of [PreferenceGroup.addLightDarkModePreference] for apps that don't support M3 custom color theme.
 */
@JvmOverloads
fun PreferenceGroup.addLightDarkModePreferenceWithoutM3CustomColor(
    activity: Activity,
    lightDarkMode: LightDarkMode,
    prefKey: String = DefaultThemingPreferences.LIGHT_DARK_MODE_KEY,
    prefEntries: Array<CharSequence>? = null,
    prefEntryValues: Array<CharSequence>? = null
) = addLightDarkModePreference(activity, lightDarkMode, prefKey, prefEntries, prefEntryValues) //No change.

/**
 * Version of [PreferenceGroup.addThemeColorPreference] for apps that don't support M3 custom color theme.
 */
@JvmOverloads
fun PreferenceGroup.addThemeColorPreferenceWithoutM3CustomColor(
    activity: Activity,
    md3: Boolean,
    prefKey: String = DefaultThemingPreferences.PRIMARY_COLOR_KEY,
    @ColorInt prefColors: IntArray? = null
) {
    addPreference(PredefinedColorPickerPreference(activity).apply {
        setupCommon(activity, prefKey, prefColors)

        if (md3) {
            isEnabled = false
            summary = activity.getString(R.string.using_md3)
        } else {
            summaryProvider = PredefinedColorPickerPreference.getSimpleSummaryProvider()
        }
    })
}
