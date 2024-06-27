@file:JvmName("ThemingPreferencesSuppliers")
@file:Suppress("unused")

package com.sixtyninefourtwenty.theming.preferences

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.core.content.edit
import androidx.core.util.Consumer
import androidx.preference.PreferenceDataStore
import com.sixtyninefourtwenty.theming.LightDarkMode
import com.sixtyninefourtwenty.theming.ThemeColor
import com.sixtyninefourtwenty.theming.preferences.internal.getColorInt
import com.sixtyninefourtwenty.theming.preferences.internal.prefValue

/**
 * Copy preferences from the internal preferences storage used by [ThemingPreferenceFragment].
 *
 * Can be used to migrate from [ThemingPreferenceFragment] to individual preferences added to
 * custom preference fragments.
 */
fun copyFromDefaultThemingPreferences(
    context: Context,
    copyMd3: Consumer<Boolean>,
    copyThemeColor: Consumer<ThemeColor>,
    copyLightDarkMode: Consumer<LightDarkMode>,
    copyUseM3CustomColorThemeOnAndroid12: Consumer<Boolean>
) {
    val def = DefaultThemingPreferences(context)
    copyMd3.accept(def.md3)
    copyThemeColor.accept(def.themeColor)
    copyLightDarkMode.accept(def.lightDarkMode)
    copyUseM3CustomColorThemeOnAndroid12.accept(def.useM3CustomColorThemeOnAndroid12)
}

@JvmName("create")
fun SharedPreferences.toThemingPreferencesSupplier(context: Context) = object : ThemingPreferencesSupplier {
    override var md3: Boolean
        get() = getBoolean(
            DefaultThemingPreferences.MD3_KEY,
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
        )
        set(value) {
            edit { putBoolean(DefaultThemingPreferences.MD3_KEY, value) }
        }

    override var themeColor: ThemeColor
        get() = ThemeColor.entries.first { it.getColorInt(context) == getInt(
            DefaultThemingPreferences.PRIMARY_COLOR_KEY, ThemeColor.BLUE.getColorInt(context)
        ) }
        set(value) {
            edit { putInt(DefaultThemingPreferences.PRIMARY_COLOR_KEY, value.getColorInt(context)) }
        }

    override var lightDarkMode: LightDarkMode
        get() = LightDarkMode.entries.first { it.prefValue == getString(
            DefaultThemingPreferences.LIGHT_DARK_MODE_KEY,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                LightDarkMode.SYSTEM.prefValue
            } else {
                LightDarkMode.LIGHT.prefValue
            })
        }
        set(value) {
            edit { putString(DefaultThemingPreferences.LIGHT_DARK_MODE_KEY, value.prefValue) }
        }

    override var useM3CustomColorThemeOnAndroid12: Boolean
        get() = getBoolean(DefaultThemingPreferences.USE_MD3_CUSTOM_COLORS_ON_ANDROID_12, false)
        set(value) {
            edit { putBoolean(DefaultThemingPreferences.USE_MD3_CUSTOM_COLORS_ON_ANDROID_12, value) }
        }

}

@JvmName("create")
fun PreferenceDataStore.toThemingPreferencesSupplier(context: Context) = object : ThemingPreferencesSupplier {
    override var md3: Boolean
        get() = getBoolean(DefaultThemingPreferences.MD3_KEY, Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
        set(value) = putBoolean(DefaultThemingPreferences.MD3_KEY, value)

    override var themeColor: ThemeColor
        get() = ThemeColor.entries.first { it.getColorInt(context) == getInt(
            DefaultThemingPreferences.PRIMARY_COLOR_KEY, ThemeColor.BLUE.getColorInt(context)) }
        set(value) = putInt(DefaultThemingPreferences.PRIMARY_COLOR_KEY, value.getColorInt(context))

    override var lightDarkMode: LightDarkMode
        get() = LightDarkMode.entries.first { it.prefValue == getString(
            DefaultThemingPreferences.LIGHT_DARK_MODE_KEY,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                LightDarkMode.SYSTEM.prefValue
            } else {
                LightDarkMode.LIGHT.prefValue
            })
        }
        set(value) = putString(DefaultThemingPreferences.LIGHT_DARK_MODE_KEY, value.prefValue)

    override var useM3CustomColorThemeOnAndroid12: Boolean
        get() = getBoolean(DefaultThemingPreferences.USE_MD3_CUSTOM_COLORS_ON_ANDROID_12, false)
        set(value) {
            putBoolean(DefaultThemingPreferences.USE_MD3_CUSTOM_COLORS_ON_ANDROID_12, value)
        }

}
