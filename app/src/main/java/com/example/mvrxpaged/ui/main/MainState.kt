package com.example.mvrxpaged.ui.main

import androidx.paging.PagedList
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState

data class MainState(
    val modelListPaged: Async<PagedList<EpoxyModel<*>>>
) : MvRxState