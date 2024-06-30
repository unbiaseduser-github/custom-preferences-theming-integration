package com.sixtyninefourtwenty.theming.sample.inmemory

import com.sixtyninefourtwenty.theming.LightDarkMode
import com.sixtyninefourtwenty.theming.ThemeColor
import com.sixtyninefourtwenty.theming.preferences.ThemingPreferencesSupplier

object InMemoryThemingPreferenceSupplier : ThemingPreferencesSupplier {
    override var md3: Boolean = false
    override var lightDarkMode: LightDarkMode = LightDarkMode.SYSTEM
    override var themeColor: ThemeColor = ThemeColor.BLUE
    override var useM3CustomColorThemeOnAndroid12: Boolean = false
}