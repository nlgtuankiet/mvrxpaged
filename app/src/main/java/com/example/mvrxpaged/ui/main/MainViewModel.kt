package com.example.mvrxpaged.ui.main

import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.Uninitialized
import com.airbnb.mvrx.ViewModelContext
import com.example.mvrxpaged.domain.entity.BannerData
import com.example.mvrxpaged.domain.entity.CategoryData
import com.example.mvrxpaged.domain.entity.DealData
import io.reactivex.BackpressureStrategy
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject
import javax.inject.Provider

class MainViewModel(
    initialState: MainState,
    getMainEpoxyModelPagedListObservable: GetMainEpoxyModelPagedListObservable,
    args: MainArgs
) : BaseMvRxViewModel<MainState>(initialState, false) {

    private val _messageEvent = BehaviorSubject.create<String>()
    val messageEvent = _messageEvent.toFlowable(BackpressureStrategy.LATEST)

    init {
        getMainEpoxyModelPagedListObservable(args.placeHolderEnabled)
            .execute { pagedList ->
                copy(modelListPaged = pagedList)
            }
    }

    val disposable = CompositeDisposable()

    fun onDealClick(dealData: DealData) {
        println("open deal screen for: ${dealData.value}")
    }

    fun onDealHeaderClick(data: DealData) {
        _messageEvent.onNext("onDealHeaderClick $data")
    }

    fun onCategoryClick(data: CategoryData) {
        _messageEvent.onNext("onCategoryClick $data")
    }

    fun onCategoryHeaderClick(data: CategoryData) {
        _messageEvent.onNext("onCategoryHeaderClick $data")
    }

    fun onCategoryFooterClick(data: CategoryData) {
        _messageEvent.onNext("onCategoryFooterClick $data")
    }

    fun onBannerClick(data: BannerData) {
        _messageEvent.onNext("onBannerClick $data")
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

    class Factory @Inject constructor(
        val provider: Provider<GetMainEpoxyModelPagedListObservable>,
        val argsProvider: Provider<MainArgs>
    ) {
        fun create(initialState: MainState): MainViewModel {
            return MainViewModel(initialState, provider.get(), argsProvider.get())
        }
    }

    companion object : MvRxViewModelFactory<MainViewModel, MainState> {
        @JvmStatic
        override fun create(viewModelContext: ViewModelContext, state: MainState): MainViewModel? {
            val fragment = (viewModelContext.activity.supportFragmentManager.findFragmentByTag("123") as MainFragment)
            val viewModelFactory = fragment.viewModelFactory
            val viewmodel = viewModelFactory.create(state)
            return viewmodel
        }

        @JvmStatic
        override fun initialState(viewModelContext: ViewModelContext): MainState? {
            return MainState(Uninitialized)
        }
    }
}