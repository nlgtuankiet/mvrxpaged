package com.example.mvrxpaged.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.mvrxpaged.R
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment().apply {
                    arguments = intent?.extras?.getBundle("args")
                }, "123")
                .commit()
        }
    }

    companion object {
        fun starterIntent(context: Context, infinity: Boolean, placeholder: Boolean): Intent {
            return Intent(context, MainActivity::class.java)
                .putExtra(
                    "args",
                    MainArgs(
                        infinity = infinity,
                        placeHolderEnabled = placeholder
                    ).toBundle()
                )
        }
    }
}