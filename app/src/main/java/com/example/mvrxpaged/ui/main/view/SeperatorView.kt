package com.example.mvrxpaged.ui.main.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.airbnb.epoxy.ModelView
import com.example.mvrxpaged.R2
import com.example.mvrxpaged.ui.main.ItemViewModel

@ModelView(defaultLayout = R2.layout.seperator)
class SeperatorView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : LinearLayout(context, attributeSet) {

    class Model(override val id: String) : ItemViewModel

}