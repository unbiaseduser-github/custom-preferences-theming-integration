package com.sixtyninefourtwenty.theming.sample

import android.app.Application
import com.sixtyninefourtwenty.theming.preferences.toThemingPreferencesSupplier
import com.sixtyninefourtwenty.theming.preferences.toThemingPreferencesSupplierWithoutM3CustomColor

class MyApplication : Application() {

    val defaultSharedPreferencesBackedPreferenceDataStore by lazy {
        SharedPreferencesBackedPreferenceDataStore(this)
    }

    val defaultSharedPreferencesBackedPreferenceDataStore2 by lazy {
        SharedPreferencesBackedPreferenceDataStore(this, "something_else")
    }

    val defaultSharedPreferencesBackedPreferenceDataStoreBackedThemingPreferenceSupplier by lazy {
        defaultSharedPreferencesBackedPreferenceDataStore.toThemingPreferencesSupplier(this)
    }

    val defaultSharedPreferencesBackedPreferenceDataStoreBackedThemingPreferenceSupplierWithoutM3CustomColor by lazy {
        defaultSharedPreferencesBackedPreferenceDataStore2.toThemingPreferencesSupplierWithoutM3CustomColor(this)
    }

}