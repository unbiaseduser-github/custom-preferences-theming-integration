@file:JvmName("ActivityThemingWithPreferences")
@file:Suppress("unused")

package com.sixtyninefourtwenty.theming.preferences

import android.app.Activity
import android.content.SharedPreferences
import androidx.annotation.StyleRes
import androidx.preference.PreferenceDataStore
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceGroup
import com.sixtyninefourtwenty.theming.applyTheming

/**
 * @param preferencesSupplier Preference storage to use if available. If
 * you're using [PreferenceGroup.addThemingPreferences] on a [PreferenceFragmentCompat],
 * make sure that the supplier is linked to whatever [SharedPreferences] or [PreferenceDataStore]
 * the fragment is using. If null, the default storage used by [ThemingPreferenceFragment] will be used.
 * @see [Activity.applyTheming]
 */
@JvmOverloads
@Deprecated(
    message = "This method's name is confusing, and the functionality that isn't already provided by" +
            " Activity.applyTheming is better expressed by a method that doesn't take a preferences" +
            " supplier. Instead, call applyThemingWithDefaultPreferences if the default preference" +
            " supplier used by ThemingPreferenceFragment is desired or applyTheming if a custom preference" +
            " supplier is available.",
    replaceWith = ReplaceWith("applyThemingWithDefaultPreferences or applyTheming")
)
fun Activity.applyThemingWithPreferences(
    @StyleRes material2ThemeStyleRes: Int,
    @StyleRes material3CustomColorsThemeStyleRes: Int,
    @StyleRes material3DynamicColorsThemeStyleRes: Int,
    preferencesSupplier: ImmutableThemingPreferencesSupplier? = null
) = applyTheming(
    material2ThemeStyleRes,
    material3CustomColorsThemeStyleRes,
    material3DynamicColorsThemeStyleRes,
    preferencesSupplier ?: DefaultThemingPreferences.getInstance(this)
)

/**
 * Call [Activity.applyTheming] with the default storage used by [ThemingPreferenceFragment].
 */
fun Activity.applyThemingWithDefaultPreferences(
    @StyleRes material2ThemeStyleRes: Int,
    @StyleRes material3CustomColorsThemeStyleRes: Int,
    @StyleRes material3DynamicColorsThemeStyleRes: Int
) = applyTheming(
    material2ThemeStyleRes,
    material3CustomColorsThemeStyleRes,
    material3DynamicColorsThemeStyleRes,
    DefaultThemingPreferences.getInstance(this)
)
