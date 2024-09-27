@file:JvmName("ThemingPreferenceUI")
@file:Suppress("unused")

package com.sixtyninefourtwenty.theming.preferences

import android.app.Activity
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceGroup
import androidx.preference.SwitchPreferenceCompat
import com.sixtyninefourtwenty.custompreferences.PredefinedColorPickerPreference
import com.sixtyninefourtwenty.custompreferences.ToggleGroupPreference
import com.sixtyninefourtwenty.theming.LightDarkMode
import com.sixtyninefourtwenty.theming.ThemeColor
import com.sixtyninefourtwenty.theming.applyTheming
import com.sixtyninefourtwenty.theming.preferences.internal.ThemeColorPreferenceSummaryProvider
import com.sixtyninefourtwenty.theming.preferences.internal.getColorInt
import com.sixtyninefourtwenty.theming.preferences.internal.getLightDarkModeEntries
import com.sixtyninefourtwenty.theming.preferences.internal.getLightDarkModeEntryValues
import com.sixtyninefourtwenty.theming.preferences.internal.getLightDarkModeIcons
import com.sixtyninefourtwenty.theming.preferences.internal.getThemeColorColors
import com.sixtyninefourtwenty.theming.preferences.internal.prefValue
import com.sixtyninefourtwenty.theming.preferences.internal.setupCommon

/**
 * Calls [addM3PreferenceWithDefaultSettings], [addLightDarkModePreferenceWithDefaultSettings],
 * [addUseMD3CustomColorsThemeOnAndroid12PreferenceWithDefaultSettings] and
 * [addThemeColorPreferenceWithDefaultSettings] in that exact order.
 */
fun PreferenceGroup.addThemingPreferencesWithDefaultSettings(
    activity: Activity,
    preferenceSupplier: ImmutableThemingPreferencesSupplier
) {
    addM3PreferenceWithDefaultSettings(activity, preferenceSupplier)
    addLightDarkModePreferenceWithDefaultSettings(activity, preferenceSupplier)
    addUseMD3CustomColorsThemeOnAndroid12PreferenceWithDefaultSettings(activity, preferenceSupplier)
    addThemeColorPreferenceWithDefaultSettings(activity, preferenceSupplier)
}

/**
 * Calls [addM3Preference], [addLightDarkModePreference], [addUseMD3CustomColorsThemeOnAndroid12Preference]
 * and [addThemeColorPreference] in that exact order.
 *
 * Note that this only supports apps supporting all 3 themes: M2, M3 custom colors and M3 dynamic colors.
 * If your app only supports M2 and M3 dynamic colors, do *not* use this - use [addThemingPreferencesWithoutM3CustomColor]
 * instead. Calling this in the above scenario results in undefined behavior.
 */
@JvmOverloads
@Deprecated(message = "Call addThemingPreferencesWithDefaultSettings if default arguments are supplied," +
        " else call each add...Preference method separately.")
fun PreferenceGroup.addThemingPreferences(
    activity: Activity,
    preferenceSupplier: ImmutableThemingPreferencesSupplier,
    m3PrefKey: String = DefaultThemingPreferences.MD3_KEY,
    lightDarkModeValueFunction: (LightDarkMode) -> String = { it.prefValue },
    lightDarkModePrefKey: String = DefaultThemingPreferences.LIGHT_DARK_MODE_KEY,
    lightDarkModePrefEntries: Array<out CharSequence> = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        activity.resources.getStringArray(R.array.themes_preference_entries)
    } else {
        activity.resources.getStringArray(R.array.themes_preference_entries_p)
    },
    lightDarkModePrefEntryValues: Array<out CharSequence> = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        activity.resources.getStringArray(R.array.themes_preference_entry_values)
    } else {
        activity.resources.getStringArray(R.array.themes_preference_entry_values_p)
    },
    useMD3CustomColorsThemeOnAndroid12PrefKey: String = DefaultThemingPreferences.USE_MD3_CUSTOM_COLORS_ON_ANDROID_12,
    themeColorsValueFunction: (ThemeColor) -> Int = { it.getColorInt(context) },
    themeColorPrefKey: String = DefaultThemingPreferences.PRIMARY_COLOR_KEY,
    @ColorInt themeColorPrefColors: IntArray = activity.resources.getIntArray(R.array.theme_color_preference_available_colors)
) {
    addM3Preference(activity, preferenceSupplier, m3PrefKey)
    addLightDarkModePreference(activity, preferenceSupplier, lightDarkModeValueFunction, lightDarkModePrefKey, lightDarkModePrefEntries, lightDarkModePrefEntryValues)
    addUseMD3CustomColorsThemeOnAndroid12Preference(activity, preferenceSupplier, useMD3CustomColorsThemeOnAndroid12PrefKey)
    addThemeColorPreference(activity, preferenceSupplier, themeColorsValueFunction, themeColorPrefKey, themeColorPrefColors)
}

/**
 * Adds a [SwitchPreferenceCompat].
 *
 * Note that this only supports apps supporting all 3 themes: M2, M3 custom colors and M3 dynamic colors.
 * If your app only supports M2 and M3 dynamic colors, do *not* use this - use [addM3PreferenceWithoutM3CustomColor]
 * instead. Calling this in the above scenario results in undefined behavior.
 * @param prefKey Key to use for the preference.
 * @see addM3PreferenceWithDefaultSettings
 */
fun PreferenceGroup.addM3Preference(
    activity: Activity,
    preferenceSupplier: ImmutableThemingPreferencesSupplierWithoutM3CustomColor,
    prefKey: String
) {
    addPreference(SwitchPreferenceCompat(activity).apply {
        key = prefKey
        title = activity.getString(R.string.md3)
        summary = activity.getString(R.string.md3_pref_summary)
        setDefaultValue(preferenceSupplier.md3)
        setOnPreferenceChangeListener { _, _ ->
            activity.recreate()
            true
        }
    })
}

/**
 * Version of [addM3Preference] that uses library internal resources, which is used by the library
 * implementations of [ThemingPreferencesSupplier]. Only call this when [Activity.applyTheming]
 * is called with one such implementation.
 */
fun PreferenceGroup.addM3PreferenceWithDefaultSettings(
    activity: Activity,
    preferenceSupplier: ImmutableThemingPreferencesSupplierWithoutM3CustomColor
) = addM3Preference(
    activity,
    preferenceSupplier,
    prefKey = DefaultThemingPreferences.MD3_KEY
)

/**
 * Gets the preference that has been added with [addM3PreferenceWithDefaultSettings] and
 * [its withoutM3CustomColor counterpart][addM3PreferenceWithoutM3CustomColorWithDefaultSettings].
 * @throws IllegalStateException if the preference was not added
 */
fun PreferenceGroup.getM3PreferenceWithDefaultSettings(): SwitchPreferenceCompat {
    return findPreference(DefaultThemingPreferences.MD3_KEY) ?: error("addM3PreferenceWithDefaultSettings was not called on $this")
}

/**
 * Adds a [ToggleGroupPreference].
 *
 * Note that this only supports apps supporting all 3 themes: M2, M3 custom colors and M3 dynamic colors.
 * If your app only supports M2 and M3 dynamic colors, do *not* use this - use [addLightDarkModePreferenceWithoutM3CustomColor]
 * instead. Calling this in the above scenario results in undefined behavior.
 * @param valueFunction Function that maps the current [LightDarkMode] to an entry value.
 * @param prefKey Key to use for the preference.
 * @param prefEntries Custom entries used by the preference. This array must have one value
 * for each [LightDarkMode] value.
 * @param prefEntryValues Custom entry values used by the preference. This array must have one value
 * for each [LightDarkMode] value, in order.
 * @see addLightDarkModePreferenceWithDefaultSettings
 */
@JvmOverloads
fun PreferenceGroup.addLightDarkModePreference(
    activity: Activity,
    preferenceSupplier: ImmutableThemingPreferencesSupplierWithoutM3CustomColor,
    valueFunction: (LightDarkMode) -> String,
    prefKey: String,
    prefEntries: Array<out CharSequence>,
    prefEntryValues: Array<out CharSequence>,
    prefIcons: List<Drawable?>? = null
) {
    addPreference(ToggleGroupPreference(activity).apply {
        key = prefKey
        title = activity.getString(R.string.theme)
        setEntries(
            entries = prefEntries.toList(),
            entryValues = prefEntryValues.toList(),
            icons = prefIcons
        )
        icon = when (preferenceSupplier.lightDarkMode) {
            LightDarkMode.LIGHT -> ContextCompat.getDrawable(activity, R.drawable.light_mode)
            LightDarkMode.DARK -> ContextCompat.getDrawable(activity, R.drawable.dark_mode)
            LightDarkMode.SYSTEM -> ContextCompat.getDrawable(activity, R.drawable.android)
            else -> null
        }
        setDefaultValue(valueFunction(preferenceSupplier.lightDarkMode))
        setOnPreferenceChangeListener { _, _ ->
            activity.recreate()
            true
        }
    })
}

/**
 * Version of [addLightDarkModePreference] that uses library internal resources, which is used by the library
 * implementations of [ThemingPreferencesSupplier]. Only call this when [Activity.applyTheming]
 * is called with one such implementation.
 */
fun PreferenceGroup.addLightDarkModePreferenceWithDefaultSettings(
    activity: Activity,
    preferenceSupplier: ImmutableThemingPreferencesSupplierWithoutM3CustomColor
) = addLightDarkModePreference(
    activity,
    preferenceSupplier,
    valueFunction = { it.prefValue },
    prefKey = DefaultThemingPreferences.LIGHT_DARK_MODE_KEY,
    prefEntries = activity.getLightDarkModeEntries(),
    prefEntryValues = activity.getLightDarkModeEntryValues(),
    prefIcons = activity.getLightDarkModeIcons()
)

/**
 * Gets the preference that has been added with [addLightDarkModePreferenceWithDefaultSettings] and
 * [its withoutM3CustomColor counterpart][addLightDarkModePreferenceWithoutM3CustomColorWithDefaultSettings].
 * @throws IllegalStateException if the preference was not added
 */
fun PreferenceGroup.getLightDarkModePreferenceWithDefaultSettings(): ToggleGroupPreference {
    return findPreference(DefaultThemingPreferences.LIGHT_DARK_MODE_KEY) ?: error("addLightDarkModePreferenceWithDefaultSettings was not called on $this")
}

/**
 * Adds a [SwitchPreferenceCompat].
 * @param prefKey Key to use for the preference.
 * @see addUseMD3CustomColorsThemeOnAndroid12PreferenceWithDefaultSettings
 */
fun PreferenceGroup.addUseMD3CustomColorsThemeOnAndroid12Preference(
    activity: Activity,
    preferenceSupplier: ImmutableThemingPreferencesSupplier,
    prefKey: String
) {
    addPreference(SwitchPreferenceCompat(activity).apply {
        key = prefKey
        title = activity.getString(R.string.custom_colors_m3)
        setDefaultValue(preferenceSupplier.useM3CustomColorThemeOnAndroid12)
        setOnPreferenceChangeListener { _, _ ->
            activity.recreate()
            true
        }

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.R) {
            isVisible = false
        } else if (!preferenceSupplier.md3) {
            isEnabled = false
            summary = activity.getString(R.string.md3_not_applied)
        }
    })
}

/**
 * Version of [addUseMD3CustomColorsThemeOnAndroid12Preference] that uses library internal resources, which is used by the library
 * implementations of [ThemingPreferencesSupplier]. Only call this when [Activity.applyTheming]
 * is called with one such implementation.
 */
fun PreferenceGroup.addUseMD3CustomColorsThemeOnAndroid12PreferenceWithDefaultSettings(
    activity: Activity,
    preferenceSupplier: ImmutableThemingPreferencesSupplier
) = addUseMD3CustomColorsThemeOnAndroid12Preference(
    activity,
    preferenceSupplier,
    prefKey = DefaultThemingPreferences.USE_MD3_CUSTOM_COLORS_ON_ANDROID_12
)

/**
 * Gets the preference that has been added with [addUseMD3CustomColorsThemeOnAndroid12PreferenceWithDefaultSettings].
 * @throws IllegalStateException if the preference was not added
 */
fun PreferenceGroup.getUseMD3CustomColorsThemeOnAndroid12PreferenceWithDefaultSettings(): SwitchPreferenceCompat {
    return findPreference(DefaultThemingPreferences.USE_MD3_CUSTOM_COLORS_ON_ANDROID_12) ?: error("addUseMD3CustomColorsThemeOnAndroid12PreferenceWithDefaultSettings was not called on $this")
}

/**
 * Adds a [PredefinedColorPickerPreference].
 *
 * Note that this only supports apps supporting all 3 themes: M2, M3 custom colors and M3 dynamic colors.
 * If your app only supports M2 and M3 dynamic colors, do *not* use this - use [addThemeColorPreferenceWithoutM3CustomColor]
 * instead. Calling this in the above scenario results in undefined behavior.
 * @param valueFunction Function that maps the current [ThemeColor] to one of the [prefColors].
 * @param prefKey Key to use for the preference.
 * @param prefColors Custom color values used by the preference. This array must have one color
 * int for each [ThemeColor] value.
 * @see addThemeColorPreferenceWithDefaultSettings
 */
fun PreferenceGroup.addThemeColorPreference(
    activity: Activity,
    preferenceSupplier: ImmutableThemingPreferencesSupplier,
    valueFunction: (ThemeColor) -> Int,
    prefKey: String,
    @ColorInt prefColors: IntArray
) {
    addPreference(PredefinedColorPickerPreference(activity).apply {
        setupCommon(valueFunction(preferenceSupplier.themeColor), activity, prefKey, prefColors)

        if (!preferenceSupplier.useM3CustomColorThemeOnAndroid12) {
            if (preferenceSupplier.md3 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                isEnabled = false
                summary = activity.getString(R.string.using_android_12_dynamic_colors)
            } else {
                summaryProvider = ThemeColorPreferenceSummaryProvider
            }
        } else {
            summaryProvider = ThemeColorPreferenceSummaryProvider
        }
    })
}

/**
 * Version of [addThemeColorPreference] that uses library internal resources, which is used by the library
 * implementations of [ThemingPreferencesSupplier]. Only call this when [Activity.applyTheming]
 * is called with one such implementation.
 */
fun PreferenceGroup.addThemeColorPreferenceWithDefaultSettings(
    activity: Activity,
    preferenceSupplier: ImmutableThemingPreferencesSupplier
) = addThemeColorPreference(
    activity,
    preferenceSupplier,
    valueFunction = { it.getColorInt(context) },
    prefKey = DefaultThemingPreferences.PRIMARY_COLOR_KEY,
    prefColors = activity.getThemeColorColors()
)

/**
 * Gets the preference that has been added with [addThemeColorPreferenceWithDefaultSettings] and
 * [its `withoutM3CustomColor` counterpart][addThemeColorPreferenceWithoutM3CustomColorWithDefaultSettings].
 * @throws IllegalStateException if the preference was not added
 */
fun PreferenceGroup.getThemeColorPreferenceWithDefaultSettings(): PredefinedColorPickerPreference {
    return findPreference(DefaultThemingPreferences.PRIMARY_COLOR_KEY) ?: error("addThemeColorPreferenceWithDefaultSettings was not called on $this")
}
