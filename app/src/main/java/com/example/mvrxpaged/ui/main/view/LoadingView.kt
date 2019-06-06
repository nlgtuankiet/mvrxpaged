package com.example.mvrxpaged.ui.main.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import com.airbnb.epoxy.ModelView
import com.example.mvrxpaged.R
import com.example.mvrxpaged.R2
import com.example.mvrxpaged.ui.OnClick
import com.example.mvrxpaged.ui.main.ItemViewModel

class LoadingView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : LinearLayout(context, attributeSet) {

    data class Model(override val id: String) : ItemViewModel

}