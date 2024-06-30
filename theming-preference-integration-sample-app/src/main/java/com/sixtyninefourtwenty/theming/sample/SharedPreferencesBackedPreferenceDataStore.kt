package com.sixtyninefourtwenty.theming.sample

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceDataStore

class SharedPreferencesBackedPreferenceDataStore(
    private val preferences: SharedPreferences
) : PreferenceDataStore() {

    constructor(
        context: Context,
        prefName: String = "something"
    ): this(context.getSharedPreferences(prefName, Context.MODE_PRIVATE))

    override fun putString(key: String, value: String?) {
        preferences.edit { putString(key, value) }
    }

    override fun putStringSet(key: String, values: MutableSet<String>?) {
        preferences.edit { putStringSet(key, values) }
    }

    override fun putInt(key: String, value: Int) {
        preferences.edit { putInt(key, value) }
    }

    override fun putLong(key: String, value: Long) {
        preferences.edit { putLong(key, value) }
    }

    override fun putFloat(key: String, value: Float) {
        preferences.edit { putFloat(key, value) }
    }

    override fun putBoolean(key: String, value: Boolean) {
        preferences.edit { putBoolean(key, value) }
    }

    override fun getString(key: String, defValue: String?): String? {
        return preferences.getString(key, defValue)
    }

    override fun getStringSet(key: String, defValues: MutableSet<String>?): MutableSet<String>? {
        return preferences.getStringSet(key, defValues)
    }

    override fun getInt(key: String, defValue: Int): Int {
        return preferences.getInt(key, defValue)
    }

    override fun getLong(key: String, defValue: Long): Long {
        return preferences.getLong(key, defValue)
    }

    override fun getFloat(key: String, defValue: Float): Float {
        return preferences.getFloat(key, defValue)
    }

    override fun getBoolean(key: String, defValue: Boolean): Boolean {
        return preferences.getBoolean(key, defValue)
    }

}
