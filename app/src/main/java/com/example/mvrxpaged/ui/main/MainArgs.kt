package com.example.mvrxpaged.ui.main

import android.os.Bundle

data class MainArgs(
    val infinity: Boolean,
    val placeHolderEnabled: Boolean
) {
    fun toBundle() = Bundle().apply {
        putBoolean("infinity", infinity)
        putBoolean("placeHolderEnabled", placeHolderEnabled)
    }

    companion object {
        fun fromBundle(bundle: Bundle) = MainArgs(
            infinity = bundle.getBoolean("infinity"),
            placeHolderEnabled = bundle.getBoolean("placeHolderEnabled")
        )
    }
}