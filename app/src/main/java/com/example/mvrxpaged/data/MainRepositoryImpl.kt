package com.example.mvrxpaged.data

import com.example.mvrxpaged.di.ApplicationScope
import com.example.mvrxpaged.domain.entity.BannerData
import com.example.mvrxpaged.domain.entity.CategoryData
import com.example.mvrxpaged.domain.entity.DealData
import com.example.mvrxpaged.domain.entity.MainViewType
import com.example.mvrxpaged.domain.repository.MainRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.random.Random

@ApplicationScope
class MainRepositoryImpl @Inject constructor() : MainRepository {
    private val layerA = listOf(
        MainViewType.Banner("A"),

        MainViewType.Deal("1"),
        MainViewType.Deal("2"),

        MainViewType.Category("1"),
        MainViewType.Category("2"),
        MainViewType.Category("3")
    )

    // change view content + layout
    private val layerB = listOf(
        MainViewType.Banner("B"),
        MainViewType.Deal("1"),

        MainViewType.Category("1"),
        MainViewType.Deal("3"),

        MainViewType.Category("5"),
        MainViewType.Category("3")
    )


    override fun getBanner(name: String): Observable<BannerData> {
        return Observable
            .just(BannerData("banner name:  $name"))
            .delay(1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getMainScreenLayer(): List<MainViewType> {
        return if (Random.nextBoolean()) layerA else layerB
    }

    override fun getCategory(code: String): Observable<CategoryData> {
        return Observable
            .just(CategoryData("category $code"))
            .delay(1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getDeal(code: String): Observable<DealData> {
        return Observable.just(DealData("deal $code"))
            .delay(1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}