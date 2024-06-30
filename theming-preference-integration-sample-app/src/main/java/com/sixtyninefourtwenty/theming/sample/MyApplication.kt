package com.sixtyninefourtwenty.theming.sample

import android.app.Application
import com.sixtyninefourtwenty.theming.preferences.toThemingPreferencesSupplier

class MyApplication : Application() {

    val defaultSharedPreferencesBackedPreferenceDataStore by lazy {
        SharedPreferencesBackedPreferenceDataStore(this)
    }

    val defaultSharedPreferencesBackedPreferenceDataStoreBackedThemingPreferenceSupplier by lazy {
        defaultSharedPreferencesBackedPreferenceDataStore.toThemingPreferencesSupplier(this)
    }

}