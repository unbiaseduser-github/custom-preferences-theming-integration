package com.sixtyninefourtwenty.theming.preferences.internal

import android.app.Activity
import android.content.Context
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.sixtyninefourtwenty.custompreferences.PredefinedColorPickerPreference
import com.sixtyninefourtwenty.theming.LightDarkMode
import com.sixtyninefourtwenty.theming.ThemeColor
import com.sixtyninefourtwenty.theming.preferences.R

internal fun ThemeColor.getColorInt(context: Context): Int {
    return when (this) {
        ThemeColor.BLUE -> ContextCompat.getColor(context, R.color.blue_fixed)
        ThemeColor.RED -> ContextCompat.getColor(context, R.color.red_fixed)
        ThemeColor.GREEN -> ContextCompat.getColor(context, R.color.green_fixed)
        ThemeColor.PURPLE -> ContextCompat.getColor(context, R.color.purple_fixed)
        ThemeColor.ORANGE -> ContextCompat.getColor(context, R.color.orange_fixed)
        ThemeColor.PINK -> ContextCompat.getColor(context, R.color.pink_fixed)
    }
}

internal val LightDarkMode.prefValue: String
    get() {
        return when (this) {
            LightDarkMode.LIGHT -> "light"
            LightDarkMode.DARK -> "dark"
            LightDarkMode.BATTERY -> "battery_saver"
            LightDarkMode.SYSTEM -> "system"
        }
    }

internal fun PredefinedColorPickerPreference.setupCommon(
    color: Int,
    activity: Activity,
    prefKey: String,
    @ColorInt prefColors: IntArray
) {
    key = prefKey
    title = activity.getString(R.string.primary_color)
    setIcon(R.drawable.palette)
    setAvailableColors(prefColors)
    setDefaultValue("#${Integer.toHexString(color)}")
    setOnPreferenceChangeListener { _, _ ->
        activity.recreate()
        true
    }
}