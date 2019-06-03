package com.example.mvrxpaged.ui.main.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import com.example.mvrxpaged.R
import com.example.mvrxpaged.ui.OnClick
import com.example.mvrxpaged.ui.main.ItemViewModel

class FooterView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : LinearLayout(context, attributeSet) {

    data class Model(override val id: String, val content: String, val onClick: OnClick) : ItemViewModel

    private val contentTextView: TextView by lazy {
        findViewById<TextView>(R.id.content)
    }

    fun setContent(content: String) {
        println("FooterView setContent: $content")
        contentTextView.text = "-- FOOTER $content --".toUpperCase()
    }

    fun setOnClick(onClick: OnClick? = null) {
        setOnClickListener(onClick)
    }
}