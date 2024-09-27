package com.sixtyninefourtwenty.theming.preferences

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.core.content.edit
import androidx.preference.PreferenceDataStore
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sixtyninefourtwenty.theming.LightDarkMode
import com.sixtyninefourtwenty.theming.preferences.internal.prefValue
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
class DefaultThemingPreferenceSuppliersMigrationTest {

    private lateinit var prefs: SharedPreferences
    private lateinit var pds: PreferenceDataStore

    @Before
    fun setUp() {
        prefs = ApplicationProvider.getApplicationContext<Context>().getSharedPreferences("test", Context.MODE_PRIVATE)
        pds = SharedPreferencesBackedPreferenceDataStore(prefs)
    }

    private fun testSharedPrefs(prefs: SharedPreferences, expectedLightDarkMode: LightDarkMode) {
        prefs.edit { putString(DefaultThemingPreferences.LIGHT_DARK_MODE_KEY, LightDarkMode.BATTERY.prefValue) }
        prefs.toThemingPreferencesSupplier(ApplicationProvider.getApplicationContext())
        assertEquals(expectedLightDarkMode.prefValue, prefs.getString(DefaultThemingPreferences.LIGHT_DARK_MODE_KEY, null))
    }

    @Test
    @Config(minSdk = Build.VERSION_CODES.Q)
    fun migrateSharedPrefOnAndroid10() {
        testSharedPrefs(prefs, LightDarkMode.SYSTEM)
    }

    @Test
    @Config(maxSdk = Build.VERSION_CODES.P)
    fun migrateSharedPrefOnAndroid9() {
        testSharedPrefs(prefs, LightDarkMode.LIGHT)
    }

    private fun testPrefDataStore(pds: PreferenceDataStore, expectedLightDarkMode: LightDarkMode) {
        pds.putString(DefaultThemingPreferences.LIGHT_DARK_MODE_KEY, LightDarkMode.BATTERY.prefValue)
        pds.toThemingPreferencesSupplier(ApplicationProvider.getApplicationContext())
        assertEquals(expectedLightDarkMode.prefValue, pds.getString(DefaultThemingPreferences.LIGHT_DARK_MODE_KEY, null))
    }

    @Test
    @Config(minSdk = Build.VERSION_CODES.Q)
    fun migratePrefDataStoreOnAndroid10() {
        testPrefDataStore(pds, LightDarkMode.SYSTEM)
    }

    @Test
    @Config(maxSdk = Build.VERSION_CODES.P)
    fun migratePrefDataStoreOnAndroid9() {
        testPrefDataStore(pds, LightDarkMode.LIGHT)
    }

}

private class SharedPreferencesBackedPreferenceDataStore(
    private val prefs: SharedPreferences
) : PreferenceDataStore() {

    override fun putString(key: String, value: String?) {
        prefs.edit { putString(key, value) }
    }

    override fun putStringSet(key: String, values: MutableSet<String>?) {
        prefs.edit { putStringSet(key, values) }
    }

    override fun putInt(key: String, value: Int) {
        prefs.edit { putInt(key, value) }
    }

    override fun putLong(key: String, value: Long) {
        prefs.edit { putLong(key, value) }
    }

    override fun putFloat(key: String, value: Float) {
        prefs.edit { putFloat(key, value) }
    }

    override fun putBoolean(key: String, value: Boolean) {
        prefs.edit { putBoolean(key, value) }
    }

    override fun getString(key: String, defValue: String?): String? {
        return prefs.getString(key, defValue)
    }

    override fun getStringSet(key: String, defValues: MutableSet<String>?): MutableSet<String>? {
        return prefs.getStringSet(key, defValues)
    }

    override fun getInt(key: String, defValue: Int): Int {
        return prefs.getInt(key, defValue)
    }

    override fun getLong(key: String, defValue: Long): Long {
        return prefs.getLong(key, defValue)
    }

    override fun getFloat(key: String, defValue: Float): Float {
        return prefs.getFloat(key, defValue)
    }

    override fun getBoolean(key: String, defValue: Boolean): Boolean {
        return prefs.getBoolean(key, defValue)
    }

}