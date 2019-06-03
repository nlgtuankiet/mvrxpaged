package com.example.mvrxpaged.ui.main

import androidx.paging.Config
import androidx.paging.PagedList
import androidx.paging.toObservable
import com.airbnb.epoxy.EpoxyModel
import io.reactivex.Observable
import javax.inject.Inject

class GetMainEpoxyModelPagedListObservable @Inject constructor(
    private val dataSourceFactory: MainEpoxyModelDataSource.Factory
) {
    operator fun invoke(placeholderEnabled: Boolean): Observable<PagedList<EpoxyModel<*>>> {
        return dataSourceFactory.toObservable(
            config = Config(
                pageSize = 10,
                enablePlaceholders = placeholderEnabled,
                prefetchDistance = 10
            )
        )
    }
}