package com.example.mvrxpaged.ui.main

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.example.mvrxpaged.di.FragmentScope
import com.example.mvrxpaged.ui.main.view.SimpleTextViewModel_
import javax.inject.Inject

@FragmentScope
class MainController @Inject constructor() : PagedListEpoxyController<EpoxyModel<*>>() {
    override fun buildItemModel(currentPosition: Int, item: EpoxyModel<*>?): EpoxyModel<*> {
        return item ?: SimpleTextViewModel_()
            .id("loading $currentPosition")
            .content("loading $currentPosition")
    }
}