package com.sixtyninefourtwenty.theming.preferences

import android.content.Context
import android.content.pm.ActivityInfo
import androidx.test.core.app.ApplicationProvider
import org.robolectric.Shadows.shadowOf

fun registerActivity(className: String) {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val activityInfo = ActivityInfo().apply {
        name = className
        packageName = context.packageName
        theme = androidx.appcompat.R.style.Theme_AppCompat
    }
    shadowOf(context.packageManager).addOrUpdateActivity(activityInfo)
}