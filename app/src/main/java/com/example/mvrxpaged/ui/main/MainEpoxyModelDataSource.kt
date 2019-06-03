package com.example.mvrxpaged.ui.main

import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.airbnb.epoxy.EpoxyModel
import com.example.mvrxpaged.di.FormViewModel
import com.example.mvrxpaged.di.FragmentScope
import com.example.mvrxpaged.domain.entity.MainViewType.*
import com.example.mvrxpaged.domain.interactor.GetBanner
import com.example.mvrxpaged.domain.interactor.GetCategory
import com.example.mvrxpaged.domain.interactor.GetDeal
import com.example.mvrxpaged.domain.interactor.GetMainLayout
import com.example.mvrxpaged.ui.OnClick
import com.example.mvrxpaged.ui.main.view.FooterViewModel_
import com.example.mvrxpaged.ui.main.view.HeaderViewModel_
import com.example.mvrxpaged.ui.main.view.SeperatorViewModel_
import com.example.mvrxpaged.ui.main.view.SimpleTextViewModel_
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Provider
import kotlin.random.Random

@FragmentScope
class MainEpoxyModelDataSource @Inject constructor(
    @FormViewModel private val viewModelProvider: Provider<MainViewModel>,
    private val args: MainArgs,
    private val getBanner: GetBanner,
    private val getCategory: GetCategory,
    private val getDeal: GetDeal,
    private val getMainLayout: GetMainLayout
) : PageKeyedDataSource<Int, EpoxyModel<*>>() {
    private val layout = getMainLayout()
    private val totalItemCount = layout.map { viewType ->
        when (viewType) {
            is Banner -> 1 + 1 // banner + separator
            is Deal -> 1 + 1 + 1 // header + content + separator
            is Category -> 1 + 1 + 1 + 1 // header + content + footer + separator
        }
    }.sum()

    private val viewModel: MainViewModel by lazy { viewModelProvider.get() }
    private val compositeDisposable: CompositeDisposable by lazy { viewModel.disposable }

    @FragmentScope
    class Factory @Inject constructor(
        private val provider: Provider<MainEpoxyModelDataSource>
    ) : DataSource.Factory<Int, EpoxyModel<*>>() {
        override fun create(): DataSource<Int, EpoxyModel<*>> {
            return provider.get()
        }
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, EpoxyModel<*>>) {
        load(page = 0, loadInitialCallback = callback)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, EpoxyModel<*>>) {
        load(page = params.key, loadCallback = callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, EpoxyModel<*>>) {
        // data source does not change
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun load(
        page: Int,
        loadInitialCallback: LoadInitialCallback<Int, EpoxyModel<*>>? = null,
        loadCallback: LoadCallback<Int, EpoxyModel<*>>? = null
    ) {
        // index goes out of bound
        if (page > totalItemCount - 1) {
            // for infinity list -> load more random category block
            if (args.infinity) {
                val code = Random.nextInt() % 1000
                loadCategory(code.toString())
            } else {
                loadCallback?.onResult(emptyList(), null)
                loadInitialCallback?.onResult(emptyList(), 0, 0, null, null)
            }
        }
        val viewType = layout[page]
        val modelListObservable = when (viewType) {
            is Banner -> loadBanner(viewType.name)
            is Deal -> loadDeal(viewType.code)
            is Category -> loadCategory(viewType.code)
        }

        modelListObservable.subscribe { data ->
            val nextPage = page + 1
            if (args.infinity) {
                loadInitialCallback?.onResult(data, null, nextPage)
            } else {
                loadInitialCallback?.onResult(data, 0, totalItemCount, null, nextPage)

            }
            loadCallback?.onResult(data, nextPage)
        }.let(compositeDisposable::add)
    }

    private fun loadBanner(name: String): Observable<List<EpoxyModel<*>>> {
        return getBanner(name).map { bannerData ->
            listOf<EpoxyModel<*>>(
                SimpleTextViewModel_()
                    .id("banner ${bannerData.value}")
                    .content(bannerData.value)
                    .onClick(OnClick {
                        viewModel.onBannerClick(bannerData)
                    }),
                SeperatorViewModel_()
                    .id("seperator banner")
            )

        }
    }

    private fun loadDeal(code: String): Observable<List<EpoxyModel<*>>> {
        return getDeal(code).map {
            listOf<EpoxyModel<*>>(
                HeaderViewModel_()
                    .id("header ${it.value}")
                    .content(it.value)
                    .onClick(OnClick {
                        viewModel.onDealHeaderClick(it)
                    }),
                SimpleTextViewModel_()
                    .id("text ${it.value}")
                    .content(it.value)
                    .onClick(OnClick {
                        viewModel.onDealClick(it)
                    }),
                SeperatorViewModel_()
                    .id("Seperator ${it.value}")
            )
        }
    }

    private fun loadCategory(code: String): Observable<List<EpoxyModel<*>>> {
        return getCategory(code).map {
            listOf<EpoxyModel<*>>(
                HeaderViewModel_()
                    .id("header ${it.value}")
                    .content(it.value)
                    .onClick(OnClick {
                        viewModel.onCategoryHeaderClick(it)
                    }),
                SimpleTextViewModel_()
                    .id("text ${it.value}")
                    .content(it.value)
                    .onClick(OnClick {
                        viewModel.onCategoryClick(it)
                    }),
                FooterViewModel_()
                    .id("Footer ${it.value}")
                    .content(it.value)
                    .onClick(OnClick {
                        viewModel.onCategoryFooterClick(it)
                    }),
                SeperatorViewModel_()
                    .id("Seperator ${it.value}")
            )
        }
    }
}