@file:JvmName("ThemingPreferencesSuppliers")
@file:Suppress("unused")

package com.sixtyninefourtwenty.theming.preferences

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.core.content.edit
import androidx.core.util.Consumer
import androidx.preference.PreferenceDataStore
import androidx.preference.PreferenceGroup
import com.sixtyninefourtwenty.theming.LightDarkMode
import com.sixtyninefourtwenty.theming.ThemeColor
import com.sixtyninefourtwenty.theming.applyTheming
import com.sixtyninefourtwenty.theming.applyThemingWithoutM3CustomColors
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
    val def = DefaultThemingPreferences.getInstance(context)
    copyMd3.accept(def.md3)
    copyThemeColor.accept(def.themeColor)
    copyLightDarkMode.accept(def.lightDarkMode)
    copyUseM3CustomColorThemeOnAndroid12.accept(def.useM3CustomColorThemeOnAndroid12)
}

private fun getDefaultLightDarkModeValue(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        LightDarkMode.SYSTEM.prefValue
    } else {
        LightDarkMode.LIGHT.prefValue
    }
}

private fun getDefaultMd3Value() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

private fun Context.getDefaultThemeColorValue() = ThemeColor.BLUE.getColorInt(this)

private fun getDefaultUseM3CustomColorThemeOnAndroid12Value() = false

/**
 * Create a [ThemingPreferencesSupplierWithoutM3CustomColor] that uses the library's internal resources.
 * When this is used in [Activity.applyThemingWithoutM3CustomColors], you must use
 * [PreferenceGroup.addThemingPreferencesWithoutM3CustomColorWithDefaultSettings] on your own preferences fragment.
 */
@JvmName("createWithoutM3CustomColor")
fun SharedPreferences.toThemingPreferencesSupplierWithoutM3CustomColor(context: Context) = object : ThemingPreferencesSupplierWithoutM3CustomColor {

    init {
        if (getString(DefaultThemingPreferences.LIGHT_DARK_MODE_KEY, null) == LightDarkMode.BATTERY.prefValue) {
            edit {
                putString(
                    DefaultThemingPreferences.LIGHT_DARK_MODE_KEY,
                    getDefaultLightDarkModeValue()
                )
            }
        }
    }

    override var md3: Boolean
        get() = getBoolean(
            DefaultThemingPreferences.MD3_KEY,
            getDefaultMd3Value()
        )
        set(value) {
            edit { putBoolean(DefaultThemingPreferences.MD3_KEY, value) }
        }

    override var themeColor: ThemeColor
        get() = ThemeColor.entries.first { it.getColorInt(context) == getInt(
            DefaultThemingPreferences.PRIMARY_COLOR_KEY, context.getDefaultThemeColorValue()
        ) }
        set(value) {
            edit { putInt(DefaultThemingPreferences.PRIMARY_COLOR_KEY, value.getColorInt(context)) }
        }

    override var lightDarkMode: LightDarkMode
        get() = LightDarkMode.entries.first { it.prefValue == getString(
            DefaultThemingPreferences.LIGHT_DARK_MODE_KEY,
            getDefaultLightDarkModeValue())
        }
        set(value) {
            edit { putString(DefaultThemingPreferences.LIGHT_DARK_MODE_KEY, value.prefValue) }
        }
}

/**
 * Create a [ThemingPreferencesSupplier] that uses the library's internal keys to store preferences.
 * When this is used in [Activity.applyTheming], you must use
 * [PreferenceGroup.addThemingPreferencesWithDefaultSettings] on your own preferences fragment.
 */
@JvmName("create")
fun SharedPreferences.toThemingPreferencesSupplier(context: Context) = object : ThemingPreferencesSupplier {

    private val delegateWithoutM3CustomColor = toThemingPreferencesSupplierWithoutM3CustomColor(context)

    override var md3: Boolean by delegateWithoutM3CustomColor::md3

    override var themeColor: ThemeColor by delegateWithoutM3CustomColor::themeColor

    override var lightDarkMode: LightDarkMode by delegateWithoutM3CustomColor::lightDarkMode

    override var useM3CustomColorThemeOnAndroid12: Boolean
        get() = getBoolean(DefaultThemingPreferences.USE_MD3_CUSTOM_COLORS_ON_ANDROID_12, getDefaultUseM3CustomColorThemeOnAndroid12Value())
        set(value) {
            edit { putBoolean(DefaultThemingPreferences.USE_MD3_CUSTOM_COLORS_ON_ANDROID_12, value) }
        }

}

/**
 * @see SharedPreferences.toThemingPreferencesSupplierWithoutM3CustomColor
 */
@JvmName("createWithoutM3CustomColor")
fun PreferenceDataStore.toThemingPreferencesSupplierWithoutM3CustomColor(context: Context) = object : ThemingPreferencesSupplierWithoutM3CustomColor {

    init {
        if (getString(DefaultThemingPreferences.LIGHT_DARK_MODE_KEY, null) == LightDarkMode.BATTERY.prefValue) {
            putString(
                DefaultThemingPreferences.LIGHT_DARK_MODE_KEY,
                getDefaultLightDarkModeValue()
            )
        }
    }

    override var md3: Boolean
        get() = getBoolean(DefaultThemingPreferences.MD3_KEY, getDefaultMd3Value())
        set(value) = putBoolean(DefaultThemingPreferences.MD3_KEY, value)

    override var themeColor: ThemeColor
        get() = ThemeColor.entries.first {
            it.getColorInt(context) == getInt(
                DefaultThemingPreferences.PRIMARY_COLOR_KEY,
                context.getDefaultThemeColorValue()
            )
        }
        set(value) = putInt(DefaultThemingPreferences.PRIMARY_COLOR_KEY, value.getColorInt(context))

    override var lightDarkMode: LightDarkMode
        get() = LightDarkMode.entries.first { it.prefValue == getString(
            DefaultThemingPreferences.LIGHT_DARK_MODE_KEY,
            getDefaultLightDarkModeValue())
        }
        set(value) = putString(DefaultThemingPreferences.LIGHT_DARK_MODE_KEY, value.prefValue)

}

/**
 * @see SharedPreferences.toThemingPreferencesSupplier
 */
@JvmName("create")
fun PreferenceDataStore.toThemingPreferencesSupplier(context: Context) = object : ThemingPreferencesSupplier {

    private val delegateWithoutM3CustomColor = toThemingPreferencesSupplierWithoutM3CustomColor(context)

    override var md3: Boolean by delegateWithoutM3CustomColor::md3

    override var themeColor: ThemeColor by delegateWithoutM3CustomColor::themeColor

    override var lightDarkMode: LightDarkMode by delegateWithoutM3CustomColor::lightDarkMode

    override var useM3CustomColorThemeOnAndroid12: Boolean
        get() = getBoolean(DefaultThemingPreferences.USE_MD3_CUSTOM_COLORS_ON_ANDROID_12, getDefaultUseM3CustomColorThemeOnAndroid12Value())
        set(value) {
            putBoolean(DefaultThemingPreferences.USE_MD3_CUSTOM_COLORS_ON_ANDROID_12, value)
        }

}
