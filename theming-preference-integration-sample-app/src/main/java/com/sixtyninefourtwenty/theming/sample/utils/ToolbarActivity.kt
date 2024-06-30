package com.sixtyninefourtwenty.theming.sample.utils

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.sixtyninefourtwenty.theming.sample.databinding.ActivityToolbarBaseBinding

abstract class ToolbarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityToolbarBaseBinding
    private var _toolbar: Toolbar? = null
    val toolbar: Toolbar
        get() = _toolbar ?: throw IllegalStateException("Toolbar accessed before onCreate")

    abstract fun createContentView(): View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityToolbarBaseBinding.inflate(layoutInflater)
        _toolbar = binding.toolbar
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        binding.content.addView(createContentView())
    }

}