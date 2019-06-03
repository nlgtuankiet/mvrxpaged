package com.example.mvrxpaged.ui.main.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.example.mvrxpaged.R
import com.example.mvrxpaged.ui.OnClick

@ModelView(defaultLayout = R.layout.header)
class HeaderView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : LinearLayout(context, attributeSet) {
    private val contentTextView: TextView by lazy {
        findViewById<TextView>(R.id.content)
    }

    @ModelProp
    fun setContent(content: String) {
        println("HeaderView setContent: $content")
        contentTextView.text = "-- HEADER $content --".toUpperCase()
    }

    @CallbackProp
    @JvmOverloads
    fun setOnClick(onClick: OnClick? = null) {
        setOnClickListener(onClick)
    }
}