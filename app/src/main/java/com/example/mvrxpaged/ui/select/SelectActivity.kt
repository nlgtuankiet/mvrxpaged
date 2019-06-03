package com.example.mvrxpaged.ui.select

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mvrxpaged.R
import com.example.mvrxpaged.ui.main.MainActivity
import kotlinx.android.synthetic.main.select_activity.*

class SelectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_activity)
        go_button.setOnClickListener {
            startActivity(
                MainActivity.starterIntent(
                    this,
                    infinity = infinity_check_box.isChecked,
                    placeholder = placeholder_checkbox.isChecked
                )
            )
        }
    }
}