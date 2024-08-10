package com.sixtyninefourtwenty.theming.sample

import android.os.Bundle
import android.view.View
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.sixtyninefourtwenty.theming.sample.databinding.ActivityMainBinding
import com.sixtyninefourtwenty.theming.sample.utils.ToolbarActivity

class MainActivity : ToolbarActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun createContentView(): View {
        binding = ActivityMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.commit {
            add<MainFragment>(R.id.fragment_container)
        }
    }
}