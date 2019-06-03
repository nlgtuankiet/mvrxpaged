package com.example.mvrxpaged.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvrxpaged.domain.entity.BannerData
import com.example.mvrxpaged.domain.entity.CategoryData
import com.example.mvrxpaged.domain.entity.DealData
import javax.inject.Inject
import javax.inject.Provider

class MainViewModel @Inject constructor(
    args: MainArgs,
    getMainModelPagedListStream: GetMainModelPagedListStream
) : ViewModel() {

    class Factory @Inject constructor(
        private val provider: Provider<MainViewModel>
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>) = provider.get() as T
    }

    // don't do this, use Event pattern
    val messageEvent = MutableLiveData<String>()

    val models = getMainModelPagedListStream(args.placeHolderEnabled)

    fun onDealClick(dealData: DealData) {
        println("open deal screen for: ${dealData.value}")
    }

    fun onDealHeaderClick(data: DealData) {
        messageEvent.value = "onDealHeaderClick $data"
    }

    fun onCategoryClick(data: CategoryData) {
        messageEvent.value = "onCategoryClick $data"
    }

    fun onCategoryHeaderClick(data: CategoryData) {
        messageEvent.value = "onCategoryHeaderClick $data"
    }

    fun onCategoryFooterClick(data: CategoryData) {
        messageEvent.value = "onCategoryFooterClick $data"
    }

    fun onBannerClick(data: BannerData) {
        messageEvent.value = "onBannerClick $data"
    }
}