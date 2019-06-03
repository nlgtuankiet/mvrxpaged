package com.example.mvrxpaged.ui.main

import androidx.lifecycle.LiveData
import androidx.paging.Config
import androidx.paging.PagedList
import androidx.paging.toLiveData
import javax.inject.Inject

class GetMainModelPagedListStream @Inject constructor(
    private val dataSourceFactory: MainEpoxyModelDataSource.Factory
) {
    operator fun invoke(placeholderEnabled: Boolean): LiveData<PagedList<ItemViewModel>> {
        return dataSourceFactory.toLiveData(
            config = Config(
                pageSize = 10,
                enablePlaceholders = placeholderEnabled,
                prefetchDistance = 10
            )
        )
    }
}