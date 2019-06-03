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

@ModelView(defaultLayout = R2.layout.simple_text)
class SimpleTextView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : LinearLayout(context, attributeSet) {
    private val contentTextView: TextView by lazy {
        findViewById<TextView>(R.id.content)
    }

    @ModelProp
    fun setContent(content: String) {
        println("SimpleTextView setContent: $content")
        contentTextView.text = content
    }

    @CallbackProp
    @JvmOverloads
    fun setOnClick(onClick: OnClick? = null) {
        setOnClickListener(onClick)
    }
}