package com.sixtyninefourtwenty.theming.sample.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.sixtyninefourtwenty.theming.applyTheming
import com.sixtyninefourtwenty.theming.applyThemingWithoutM3CustomColors
import com.sixtyninefourtwenty.theming.preferences.ThemingPreferencesSupplier
import com.sixtyninefourtwenty.theming.preferences.ThemingPreferencesSupplierWithoutM3CustomColor
import com.sixtyninefourtwenty.theming.preferences.applyThemingWithDefaultPreferences
import com.sixtyninefourtwenty.theming.sample.MyApplication
import com.sixtyninefourtwenty.theming.sample.R
import com.sixtyninefourtwenty.theming.sample.databinding.BottomSheetDialogBaseBinding
import com.sixtyninefourtwenty.theming.sample.databinding.InfoWithCodeSamplesBinding

fun Activity.theme(
    preferencesSupplier: ThemingPreferencesSupplier? = null
) {
    if (preferencesSupplier != null) {
        applyTheming(
            material2ThemeStyleRes = R.style.Theme_ThemingSample_Material2,
            material3CustomColorsThemeStyleRes = R.style.Theme_ThemingSample_Material3_CustomColors,
            material3DynamicColorsThemeStyleRes = R.style.Theme_ThemingSample_Material3_DynamicColors,
            preferencesSupplier = preferencesSupplier
        )
    } else {
        applyThemingWithDefaultPreferences(
            material2ThemeStyleRes = R.style.Theme_ThemingSample_Material2,
            material3CustomColorsThemeStyleRes = R.style.Theme_ThemingSample_Material3_CustomColors,
            material3DynamicColorsThemeStyleRes = R.style.Theme_ThemingSample_Material3_DynamicColors
        )
    }
}

fun Activity.themeWithoutM3CustomColor(
    preferencesSupplier: ThemingPreferencesSupplierWithoutM3CustomColor
) {
    applyThemingWithoutM3CustomColors(
        material2ThemeStyleRes = R.style.Theme_ThemingSample_Material2,
        material3DynamicColorsThemeStyleRes = R.style.Theme_ThemingSample_Material3_DynamicColors,
        preferencesSupplier = preferencesSupplier
    )
}

inline fun <reified T : Activity> Context.startActivity() = startActivity(
    Intent(this, T::class.java)
)

val Context.myApplication: MyApplication
    get() = if (this is MyApplication) this else applicationContext as MyApplication

fun BottomSheetDialogBaseBinding.setTitle(text: CharSequence) {
    require(text.isNotBlank()) { "text is blank" }
    with(title) {
        isVisible = true
        this.text = text
    }
}

fun BottomSheetDialogBaseBinding.setContentView(view: View) {
    with(content) {
        removeAllViews()
        addView(view)
    }
}

private val javaCodeKeywords = "abstract|boolean|break|byte|case|catch" +
        "|char|class|continue|default|do|double|else" +
        "|enum|extends|final|finally|float|for|if" +
        "|implements|import|instanceof|int|interface" +
        "|long|native|new|null|package|private|protected" +
        "|public|return|short|static|strictfp|super|switch" +
        "|synchronized|this|throw|transient|try|void|volatile|while"

private val kotlinCodeKeywords = "$javaCodeKeywords|as|as?|fun|in|!in|object|typealias|val|var|when|" +
        "by|constructor|delegate|dynamic|field|file|get|init|set|value|where|actual|annotation|companion|" +
        "crossinline|data|enum|expect|external|field|infix|inline|inner|internal|it|lateinit|noinline|" +
        "open|operator|out|override|reified|sealed|suspend|tailrec|vararg"

private val javaCodePattern = ("\\b($javaCodeKeywords)\\b").toPattern()

private val kotlinCodePattern = ("\\b($kotlinCodeKeywords)\\b").toPattern()

fun LayoutInflater.inflateAndSetupInfoWithCodeSamplesLayout(
    infoText: String,
    kotlinCode: String,
    javaCode: String
) = InfoWithCodeSamplesBinding.inflate(this).apply {
    info.text = infoText
    kotlinExpander.setOnClickListener {
        kotlinSampleScroller.isVisible = !kotlinSampleScroller.isVisible
    }
    kotlinSample.addSyntaxPattern(kotlinCodePattern, Color.BLUE)
    kotlinSample.setText(kotlinCode)
    javaExpander.setOnClickListener {
        javaSampleScroller.isVisible = !javaSampleScroller.isVisible
    }
    javaSample.addSyntaxPattern(javaCodePattern, Color.BLUE)
    javaSample.setText(javaCode)
}

fun Context.showInfoWithCodeSamplesDialog(
    infoText: String,
    kotlinCode: String,
    javaCode: String
) {
    val layoutInflater = LayoutInflater.from(this)
    val sheetView = BottomSheetDialogBaseBinding.inflate(layoutInflater).apply {
        setTitle(getString(R.string.info))
        setContentView(
            layoutInflater.inflateAndSetupInfoWithCodeSamplesLayout(
                infoText = infoText,
                kotlinCode = kotlinCode,
                javaCode = javaCode
            ).root
        )
    }
    BottomSheetDialog(this).apply {
        setContentView(sheetView.root)
        with(behavior) {
            state = BottomSheetBehavior.STATE_EXPANDED
            isDraggable = false
        }
    }.show()
}

fun Menu.addInfoItem(action: Int = MenuItem.SHOW_AS_ACTION_IF_ROOM) = add(R.string.info)
    .setIcon(R.drawable.info)
    .apply {
        setShowAsAction(action)
    }
