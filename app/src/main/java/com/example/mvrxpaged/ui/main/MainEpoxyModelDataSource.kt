package com.example.mvrxpaged.ui.main

import androidx.lifecycle.LifecycleOwner
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.example.mvrxpaged.di.FragmentScope
import com.example.mvrxpaged.domain.entity.MainViewType.*
import com.example.mvrxpaged.domain.interactor.GetBanner
import com.example.mvrxpaged.domain.interactor.GetCategory
import com.example.mvrxpaged.domain.interactor.GetDeal
import com.example.mvrxpaged.domain.interactor.GetMainLayout
import com.example.mvrxpaged.ui.OnClick
import com.example.mvrxpaged.ui.main.view.FooterView
import com.example.mvrxpaged.ui.main.view.HeaderView
import com.example.mvrxpaged.ui.main.view.SeperatorView
import com.example.mvrxpaged.ui.main.view.SimpleTextView
import javax.inject.Inject
import javax.inject.Provider
import kotlin.random.Random

@FragmentScope
class MainEpoxyModelDataSource @Inject constructor(
    private val viewModelProvider: Provider<MainViewModel>,
    private val args: MainArgs,
    private val getBanner: GetBanner,
    private val getCategory: GetCategory,
    private val getDeal: GetDeal,
    private val getMainLayout: GetMainLayout,
    private val lifecycleOwner: LifecycleOwner
) : PageKeyedDataSource<Int, ItemViewModel>() {
    private val layout = getMainLayout()
    private val totalItemCount = layout.map { viewType ->
        when (viewType) {
            is Banner -> 1 + 1 // banner + separator
            is Deal -> 1 + 1 + 1 // header + content + separator
            is Category -> 1 + 1 + 1 + 1 // header + content + footer + separator
        }
    }.sum()
    private val totalPage = layout.size

    private val viewModel: MainViewModel by lazy { viewModelProvider.get() }

    @FragmentScope
    class Factory @Inject constructor(
        private val provider: Provider<MainEpoxyModelDataSource>
    ) : DataSource.Factory<Int, ItemViewModel>() {
        override fun create(): DataSource<Int, ItemViewModel> {
            return provider.get()
        }
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, ItemViewModel>) {
        load(page = 0, loadInitialCallback = callback)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ItemViewModel>) {
        load(page = params.key, loadCallback = callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ItemViewModel>) {
        // data source does not change
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun load(
        page: Int,
        loadInitialCallback: LoadInitialCallback<Int, ItemViewModel>? = null,
        loadCallback: LoadCallback<Int, ItemViewModel>? = null
    ) {
        val nextPage = page + 1
        // index goes out of bound
        if (page > totalPage - 1) {
            // for infinity list -> load more random category block
            if (args.infinity) {
                val code = Random.nextInt() % 1000
                val models = loadCategory(code.toString())
                loadCallback?.onResult(models, nextPage)
            } else {
                loadCallback?.onResult(emptyList(), null)
                loadInitialCallback?.onResult(emptyList(), 0, 0, null, null)
            }
        } else {
            val viewType = layout[page]
            val models = when (viewType) {
                is Banner -> loadBanner(viewType.name)
                is Deal -> loadDeal(viewType.code)
                is Category -> loadCategory(viewType.code)
            }


            if (args.infinity) {
                loadInitialCallback?.onResult(models, null, nextPage)
            } else {
                loadInitialCallback?.onResult(models, 0, totalItemCount, null, nextPage)

            }
            loadCallback?.onResult(models, nextPage)
        }
    }

    private fun loadBanner(name: String): List<ItemViewModel> {
        return getBanner(name).let { bannerData ->
            listOf(
                SimpleTextView.Model(
                    id = "banner ${bannerData.value}",
                    content = bannerData.value,
                    onClick = OnClick {
                        viewModel.onBannerClick(bannerData)
                    }),
                SeperatorView.Model(
                    id = "seperator banner"
                )
            )
        }
    }

    private fun loadDeal(code: String): List<ItemViewModel> {
        return getDeal(code).let { dealData ->
            listOf(
                HeaderView.Model(
                    id = "header ${dealData.value}",
                    content = dealData.value,
                    onClick = OnClick {
                        viewModel.onDealHeaderClick(dealData)
                    }
                ),
                SimpleTextView.Model(
                    id = "text ${dealData.value}",
                    content = dealData.value,
                    onClick = OnClick {
                        viewModel.onDealClick(dealData)
                    }),
                SeperatorView.Model(
                    id = "Seperator ${dealData.value}"
                )
            )
        }
    }

    private fun loadCategory(code: String): List<ItemViewModel> {
        return getCategory(code).let { categoryData ->
            listOf(
                HeaderView.Model(
                    id = "header ${categoryData.value}",
                    content = categoryData.value,
                    onClick = OnClick {
                        viewModel.onCategoryHeaderClick(categoryData)
                    }),
                SimpleTextView.Model(
                    id = "text ${categoryData.value}",
                    content = categoryData.value,
                    onClick = OnClick {
                        viewModel.onCategoryClick(categoryData)
                    }),
                FooterView.Model(
                    id = ("Footer ${categoryData.value}"),
                    content = (categoryData.value),
                    onClick = OnClick {
                        viewModel.onCategoryFooterClick(categoryData)
                    }),
                SeperatorView.Model(
                    id = "Seperator ${categoryData.value}"
                )
            )
        }
    }
}