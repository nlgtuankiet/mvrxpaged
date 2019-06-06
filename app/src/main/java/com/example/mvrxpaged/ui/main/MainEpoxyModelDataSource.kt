package com.example.mvrxpaged.ui.main

import androidx.lifecycle.LifecycleOwner
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.example.mvrxpaged.di.FragmentScope
import com.example.mvrxpaged.domain.entity.MainViewType
import com.example.mvrxpaged.domain.entity.MainViewType.*
import com.example.mvrxpaged.domain.interactor.GetBanner
import com.example.mvrxpaged.domain.interactor.GetCategory
import com.example.mvrxpaged.domain.interactor.GetDeal
import com.example.mvrxpaged.domain.interactor.GetMainLayout
import com.example.mvrxpaged.ui.OnClick
import com.example.mvrxpaged.ui.main.view.*
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
    private val getMainLayout: GetMainLayout
) : PageKeyedDataSource<Int, ItemViewModel>() {
    private lateinit var layout: List<MainViewType>
    private val totalItemCount: Int by lazy {
        layout.map { viewType ->
            when (viewType) {
                is Banner -> 1 + 1 // banner + separator
                is Deal -> 1 + 1 // header + separator
                is Category -> 1 + 1 // header + separator
            }
        }.sum()
    }

    private val totalPage: Int by lazy { layout.size }

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
        layout = getMainLayout()
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
            val models = when (val viewType = layout[page]) {
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
                BannerView.Model(
                    id = "banner ${bannerData.value}",
                    content = bannerData.value,
                    onClick = OnClick {
                        viewModel.onBannerClick(bannerData)
                    }),
                SeparatorView.Model(
                    id = "separator ${bannerData.value}"
                )
            )
        }
    }

    private fun loadDeal(code: String): List<ItemViewModel> {
        return getDeal(code).let { dealData ->
            listOf(
                DealView.Model(
                    id = "header ${dealData.value}",
                    content = dealData.value,
                    onClick = OnClick {
                        viewModel.onDealClick(dealData)
                    }
                ),
                SeparatorView.Model(
                    id = "Seperator ${dealData.value}"
                )
            )
        }
    }

    private fun loadCategory(code: String): List<ItemViewModel> {
        return getCategory(code).let { categoryData ->
            listOf(
                CategoryView.Model(
                    id = "category ${categoryData.value}",
                    content = categoryData.value,
                    onClick = OnClick {
                        viewModel.onCategoryClick(categoryData)
                    }),
                SeparatorView.Model(
                    id = "separator ${categoryData.value}"
                )
            )
        }
    }
}