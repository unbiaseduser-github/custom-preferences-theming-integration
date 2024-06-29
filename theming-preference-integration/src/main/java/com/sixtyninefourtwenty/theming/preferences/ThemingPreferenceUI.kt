@file:JvmName("ThemingPreferenceUI")
@file:Suppress("unused")

package com.sixtyninefourtwenty.theming.preferences

import android.app.Activity
import android.os.Build
import androidx.annotation.ColorInt
import androidx.preference.ListPreference
import androidx.preference.PreferenceGroup
import androidx.preference.SwitchPreferenceCompat
import com.sixtyninefourtwenty.custompreferences.PredefinedColorPickerPreference
import com.sixtyninefourtwenty.theming.LightDarkMode
import com.sixtyninefourtwenty.theming.ThemeColor
import com.sixtyninefourtwenty.theming.applyTheming
import com.sixtyninefourtwenty.theming.preferences.internal.setupCommon

/**
 * Calls [addM3Preference], [addLightDarkModePreference], [addUseMD3CustomColorsThemeOnAndroid12Preference]
 * and [addThemeColorPreference] in that exact order.
 *
 * Note that this only supports apps supporting all 3 themes: M2, M3 custom colors and M3 dynamic colors.
 * If your app only supports M2 and M3 dynamic colors, do *not* use this - use [addThemingPreferencesWithoutM3CustomColor]
 * instead. Calling this in the above scenario results in undefined behavior.
 */
@JvmOverloads
fun PreferenceGroup.addThemingPreferences(
    activity: Activity,
    lightDarkMode: LightDarkMode,
    md3: Boolean,
    themeColorPreferenceAlwaysEnabledOnAndroid12: Boolean,
    m3PrefKey: String = DefaultThemingPreferences.MD3_KEY,
    lightDarkModePrefKey: String = DefaultThemingPreferences.LIGHT_DARK_MODE_KEY,
    lightDarkModePrefEntries: Array<CharSequence>? = null,
    lightDarkModePrefEntryValues: Array<CharSequence>? = null,
    useMD3CustomColorsThemeOnAndroid12PrefKey: String = DefaultThemingPreferences.USE_MD3_CUSTOM_COLORS_ON_ANDROID_12,
    themeColorPrefKey: String = DefaultThemingPreferences.PRIMARY_COLOR_KEY,
    @ColorInt themeColorPrefColors: IntArray? = null
) {
    addM3Preference(activity, m3PrefKey)
    addLightDarkModePreference(activity, lightDarkMode, lightDarkModePrefKey, lightDarkModePrefEntries, lightDarkModePrefEntryValues)
    addUseMD3CustomColorsThemeOnAndroid12Preference(activity, md3, useMD3CustomColorsThemeOnAndroid12PrefKey)
    addThemeColorPreference(activity, md3, themeColorPreferenceAlwaysEnabledOnAndroid12, themeColorPrefKey, themeColorPrefColors)
}

/**
 * Adds a [SwitchPreferenceCompat].
 *
 * Note that this only supports apps supporting all 3 themes: M2, M3 custom colors and M3 dynamic colors.
 * If your app only supports M2 and M3 dynamic colors, do *not* use this - use [addM3PreferenceWithoutM3CustomColor]
 * instead. Calling this in the above scenario results in undefined behavior.
 * @param prefKey Key to use for the preference. Default is an internal key the library
 * implementations of [ThemingPreferencesSupplier] use - if you call [Activity.applyTheming]
 * with one such implementation, you don't need this parameter.
 */
@JvmOverloads
fun PreferenceGroup.addM3Preference(
    activity: Activity,
    prefKey: String = DefaultThemingPreferences.MD3_KEY
) {
    addPreference(SwitchPreferenceCompat(activity).apply {
        key = prefKey
        title = activity.getString(R.string.md3)
        summary = activity.getString(R.string.md3_pref_summary)
        setOnPreferenceChangeListener { _, _ ->
            activity.recreate()
            true
        }
    })
}

/**
 * Adds a [ListPreference].
 *
 * Note that this only supports apps supporting all 3 themes: M2, M3 custom colors and M3 dynamic colors.
 * If your app only supports M2 and M3 dynamic colors, do *not* use this - use [addLightDarkModePreferenceWithoutM3CustomColor]
 * instead. Calling this in the above scenario results in undefined behavior.
 * @param prefKey Key to use for the preference. Default is an internal key the library
 * implementations of [ThemingPreferencesSupplier] use - if you call [Activity.applyTheming]
 * with one such implementation, you don't need this parameter.
 * @param lightDarkMode obtained from [ThemingPreferencesSupplier], will be used to set the preference's icon.
 * @param prefEntries Custom entries used by the preference. This array must have one value
 * for each [LightDarkMode] value. If null, the library's internal string array will be used.
 * @param prefEntryValues Custom entry values used by the preference. This array must have one value
 * for each [LightDarkMode] value, in order. If null, the library's internal string array will be used,
 * which is used by the library implementations of [ThemingPreferencesSupplier]. if you call [Activity.applyTheming]
 * with one such implementation, you don't need this parameter.
 */
@JvmOverloads
fun PreferenceGroup.addLightDarkModePreference(
    activity: Activity,
    lightDarkMode: LightDarkMode,
    prefKey: String = DefaultThemingPreferences.LIGHT_DARK_MODE_KEY,
    prefEntries: Array<CharSequence>? = null,
    prefEntryValues: Array<CharSequence>? = null
) {
    addPreference(ListPreference(activity).apply {
        key = prefKey
        title = activity.getString(R.string.theme)
        dialogTitle = activity.getString(R.string.theme)
        entries = prefEntries ?: if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            activity.resources.getStringArray(R.array.themes_preference_entries)
        } else {
            activity.resources.getStringArray(R.array.themes_preference_entries_p)
        }
        entryValues = prefEntryValues ?: if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            activity.resources.getStringArray(R.array.themes_preference_entry_values)
        } else {
            activity.resources.getStringArray(R.array.themes_preference_entry_values_p)
        }
        setIcon(when (lightDarkMode) {
            LightDarkMode.LIGHT -> R.drawable.light_mode
            LightDarkMode.DARK -> R.drawable.dark_mode
            LightDarkMode.BATTERY -> R.drawable.battery_saver
            LightDarkMode.SYSTEM -> R.drawable.android
        })
        setOnPreferenceChangeListener { _, _ ->
            activity.recreate()
            true
        }
        summaryProvider = ListPreference.SimpleSummaryProvider.getInstance()
    })
}

/**
 * Adds a [SwitchPreferenceCompat].
 * @param prefKey Key to use for the preference. Default is an internal key the library
 * implementations of [ThemingPreferencesSupplier] use - if you call [Activity.applyTheming]
 * with one such implementation, you don't need this parameter.
 * @param md3 obtained from [ThemingPreferencesSupplier], will be used to disable the preference if
 * it's `false` (Material 3 theme isn't active).
 */
@JvmOverloads
fun PreferenceGroup.addUseMD3CustomColorsThemeOnAndroid12Preference(
    activity: Activity,
    md3: Boolean,
    prefKey: String = DefaultThemingPreferences.USE_MD3_CUSTOM_COLORS_ON_ANDROID_12
) {
    addPreference(SwitchPreferenceCompat(activity).apply {
        key = prefKey
        title = activity.getString(R.string.custom_colors_m3)
        setOnPreferenceChangeListener { _, _ ->
            activity.recreate()
            true
        }

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.R) {
            isVisible = false
        } else if (!md3) {
            isEnabled = false
            summary = activity.getString(R.string.md3_not_applied)
        }
    })
}

/**
 * Adds a [PredefinedColorPickerPreference].
 *
 * Note that this only supports apps supporting all 3 themes: M2, M3 custom colors and M3 dynamic colors.
 * If your app only supports M2 and M3 dynamic colors, do *not* use this - use [addThemeColorPreferenceWithoutM3CustomColor]
 * instead. Calling this in the above scenario results in undefined behavior.
 * @param prefKey Key to use for the preference. Default is an internal key the library
 * implementations of [ThemingPreferencesSupplier] use - if you call [Activity.applyTheming]
 * with one such implementation, you don't need this parameter.
 * @param md3 obtained from [ThemingPreferencesSupplier], will be used to disable the preference if
 * Material 3 dynamic colors is active.
 * @param alwaysEnabledOnAndroid12 obtained from [ThemingPreferencesSupplier.useM3CustomColorThemeOnAndroid12],
 * determines whether this preference will always be enabled on Android 12 or later. Nullifies [md3]
 * if set to `true`.
 * @param prefColors Custom color values used by the preference. This array must have one color
 * int for each [ThemeColor] value. If null, the library's internal color array will be used, which
 * is used by the library implementations of [ThemingPreferencesSupplier]. if you call [Activity.applyTheming]
 * with one such implementation, you don't need this parameter.
 */
@JvmOverloads
fun PreferenceGroup.addThemeColorPreference(
    activity: Activity,
    md3: Boolean,
    alwaysEnabledOnAndroid12: Boolean,
    prefKey: String = DefaultThemingPreferences.PRIMARY_COLOR_KEY,
    @ColorInt prefColors: IntArray? = null
) {
    addPreference(PredefinedColorPickerPreference(activity).apply {
        setupCommon(activity, prefKey, prefColors)

        if (!alwaysEnabledOnAndroid12) {
            if (md3 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                isEnabled = false
                summary = activity.getString(R.string.using_android_12_dynamic_colors)
            } else {
                summaryProvider = PredefinedColorPickerPreference.getSimpleSummaryProvider()
            }
        } else {
            summaryProvider = PredefinedColorPickerPreference.getSimpleSummaryProvider()
        }
    })
}
