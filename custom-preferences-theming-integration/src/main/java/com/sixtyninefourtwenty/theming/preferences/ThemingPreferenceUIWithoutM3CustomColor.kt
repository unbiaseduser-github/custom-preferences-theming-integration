@file:JvmName("ThemingPreferenceUIWithoutM3CustomColor")
@file:Suppress("unused")

package com.sixtyninefourtwenty.theming.preferences

import android.app.Activity
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceGroup
import com.sixtyninefourtwenty.custompreferences.PredefinedColorPickerPreference
import com.sixtyninefourtwenty.theming.LightDarkMode
import com.sixtyninefourtwenty.theming.ThemeColor
import com.sixtyninefourtwenty.theming.applyThemingWithoutM3CustomColors
import com.sixtyninefourtwenty.theming.preferences.internal.ThemeColorPreferenceSummaryProvider
import com.sixtyninefourtwenty.theming.preferences.internal.getColorInt
import com.sixtyninefourtwenty.theming.preferences.internal.prefValue
import com.sixtyninefourtwenty.theming.preferences.internal.setupCommon

/**
 * Calls [addM3PreferenceWithoutM3CustomColorWithDefaultSettings], [addLightDarkModePreferenceWithoutM3CustomColorWithDefaultSettings]
 * and [addThemeColorPreferenceWithoutM3CustomColorWithDefaultSettings] in that exact order.
 */
fun PreferenceGroup.addThemingPreferencesWithoutM3CustomColorWithDefaultSettings(
    activity: Activity,
    preferenceSupplier: ImmutableThemingPreferencesSupplierWithoutM3CustomColor
) {
    addM3PreferenceWithoutM3CustomColorWithDefaultSettings(activity, preferenceSupplier)
    addLightDarkModePreferenceWithoutM3CustomColorWithDefaultSettings(activity, preferenceSupplier)
    addThemeColorPreferenceWithoutM3CustomColorWithDefaultSettings(activity, preferenceSupplier)
}

/**
 * Calls [addM3PreferenceWithoutM3CustomColor], [addLightDarkModePreferenceWithoutM3CustomColor]
 * and [addThemeColorPreferenceWithoutM3CustomColor] in that exact order.
 */
@Deprecated(message = "Call addThemingPreferencesWithoutM3CustomColorWithDefaultSettings if default arguments are supplied," +
        " else call each add...PreferenceWithoutM3CustomColor method separately.")
@JvmOverloads
fun PreferenceGroup.addThemingPreferencesWithoutM3CustomColor(
    activity: Activity,
    preferenceSupplier: ImmutableThemingPreferencesSupplierWithoutM3CustomColor,
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
    themeColorsValueFunction: (ThemeColor) -> Int = { it.getColorInt(context) },
    themeColorPrefKey: String = DefaultThemingPreferences.PRIMARY_COLOR_KEY,
    @ColorInt themeColorPrefColors: IntArray = activity.resources.getIntArray(R.array.theme_color_preference_available_colors)
) {
    addM3PreferenceWithoutM3CustomColor(activity, preferenceSupplier, m3PrefKey)
    addLightDarkModePreferenceWithoutM3CustomColor(activity, preferenceSupplier, lightDarkModeValueFunction, lightDarkModePrefKey, lightDarkModePrefEntries, lightDarkModePrefEntryValues)
    addThemeColorPreferenceWithoutM3CustomColor(activity, preferenceSupplier, themeColorsValueFunction, themeColorPrefKey, themeColorPrefColors)
}

/**
 * Version of [PreferenceGroup.addM3Preference] for apps that don't support M3 custom color theme.
 * @see addM3PreferenceWithoutM3CustomColorWithDefaultSettings
 */
fun PreferenceGroup.addM3PreferenceWithoutM3CustomColor(
    activity: Activity,
    preferenceSupplier: ImmutableThemingPreferencesSupplierWithoutM3CustomColor,
    prefKey: String
) = addM3Preference(activity, preferenceSupplier, prefKey) //No change.

/**
 * Version of [addM3PreferenceWithoutM3CustomColor] that uses library internal resources, which is used by the library
 * implementations of [ThemingPreferencesSupplierWithoutM3CustomColor]. Only call this when [Activity.applyThemingWithoutM3CustomColors]
 * is called with one such implementation.
 */
fun PreferenceGroup.addM3PreferenceWithoutM3CustomColorWithDefaultSettings(
    activity: Activity,
    preferenceSupplier: ImmutableThemingPreferencesSupplierWithoutM3CustomColor
) = addM3PreferenceWithoutM3CustomColor(
    activity,
    preferenceSupplier,
    prefKey = DefaultThemingPreferences.MD3_KEY
)

/**
 * Version of [PreferenceGroup.addLightDarkModePreference] for apps that don't support M3 custom color theme.
 * @see addLightDarkModePreferenceWithoutM3CustomColorWithDefaultSettings
 */
@JvmOverloads
fun PreferenceGroup.addLightDarkModePreferenceWithoutM3CustomColor(
    activity: Activity,
    preferenceSupplier: ImmutableThemingPreferencesSupplierWithoutM3CustomColor,
    valueFunction: (LightDarkMode) -> String,
    prefKey: String,
    prefEntries: Array<out CharSequence>,
    prefEntryValues: Array<out CharSequence>,
    prefIcons: List<Drawable?>? = null
) = addLightDarkModePreference(activity, preferenceSupplier, valueFunction, prefKey, prefEntries, prefEntryValues, prefIcons) //No change.

/**
 * Version of [addLightDarkModePreferenceWithoutM3CustomColor] that uses library internal resources, which is used by the library
 * implementations of [ThemingPreferencesSupplierWithoutM3CustomColor]. Only call this when [Activity.applyThemingWithoutM3CustomColors]
 * is called with one such implementation.
 */
fun PreferenceGroup.addLightDarkModePreferenceWithoutM3CustomColorWithDefaultSettings(
    activity: Activity,
    preferenceSupplier: ImmutableThemingPreferencesSupplierWithoutM3CustomColor
) = addLightDarkModePreferenceWithoutM3CustomColor(
    activity,
    preferenceSupplier,
    valueFunction = { it.prefValue },
    prefKey = DefaultThemingPreferences.LIGHT_DARK_MODE_KEY,
    prefEntries = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        activity.resources.getStringArray(R.array.themes_preference_entries)
    } else {
        activity.resources.getStringArray(R.array.themes_preference_entries_p)
    },
    prefEntryValues = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        activity.resources.getStringArray(R.array.themes_preference_entry_values)
    } else {
        activity.resources.getStringArray(R.array.themes_preference_entry_values_p)
    },
    prefIcons = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        listOf(
            ContextCompat.getDrawable(activity, R.drawable.light_mode),
            ContextCompat.getDrawable(activity, R.drawable.dark_mode),
            ContextCompat.getDrawable(activity, R.drawable.battery_saver),
            ContextCompat.getDrawable(activity, R.drawable.android)
        )
    } else {
        listOf(
            ContextCompat.getDrawable(activity, R.drawable.light_mode),
            ContextCompat.getDrawable(activity, R.drawable.dark_mode),
            ContextCompat.getDrawable(activity, R.drawable.battery_saver)
        )
    }
)

/**
 * Version of [PreferenceGroup.addThemeColorPreference] for apps that don't support M3 custom color theme.
 */
fun PreferenceGroup.addThemeColorPreferenceWithoutM3CustomColor(
    activity: Activity,
    preferenceSupplier: ImmutableThemingPreferencesSupplierWithoutM3CustomColor,
    valueFunction: (ThemeColor) -> Int,
    prefKey: String,
    @ColorInt prefColors: IntArray
) {
    addPreference(PredefinedColorPickerPreference(activity).apply {
        setupCommon(valueFunction(preferenceSupplier.themeColor), activity, prefKey, prefColors)

        if (preferenceSupplier.md3) {
            isEnabled = false
            summary = activity.getString(R.string.using_md3)
        } else {
            summaryProvider = ThemeColorPreferenceSummaryProvider
        }
    })
}

/**
 * Version of [addThemeColorPreferenceWithoutM3CustomColor] that uses library internal resources, which is used by the library
 * implementations of [ThemingPreferencesSupplierWithoutM3CustomColor]. Only call this when [Activity.applyThemingWithoutM3CustomColors]
 * is called with one such implementation.
 */
fun PreferenceGroup.addThemeColorPreferenceWithoutM3CustomColorWithDefaultSettings(
    activity: Activity,
    preferenceSupplier: ImmutableThemingPreferencesSupplierWithoutM3CustomColor
) = addThemeColorPreferenceWithoutM3CustomColor(
    activity,
    preferenceSupplier,
    valueFunction = { it.getColorInt(context) },
    prefKey = DefaultThemingPreferences.PRIMARY_COLOR_KEY,
    prefColors = activity.resources.getIntArray(R.array.theme_color_preference_available_colors)
)
