package com.example.mvrxpaged.ui

import android.view.View

class OnClick(block: Function0<Unit>) : View.OnClickListener, Function0<Unit> by block {
    override fun onClick(v: View?) {
        invoke()
    }
}