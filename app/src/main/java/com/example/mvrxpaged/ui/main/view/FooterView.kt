package com.example.mvrxpaged.ui.main.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.example.mvrxpaged.R
import com.example.mvrxpaged.R2
import com.example.mvrxpaged.ui.OnClick

@ModelView(defaultLayout = R2.layout.footer)
class FooterView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : LinearLayout(context, attributeSet) {
    private val contentTextView: TextView by lazy {
        findViewById<TextView>(R.id.content)
    }

    @ModelProp
    fun setContent(content: String) {
        println("FooterView setContent: $content")
        contentTextView.text = "-- FOOTER $content --".toUpperCase()

    }

    @CallbackProp
    @JvmOverloads
    fun setOnClick(onClick: OnClick? = null) {
        setOnClickListener(onClick)
    }
}