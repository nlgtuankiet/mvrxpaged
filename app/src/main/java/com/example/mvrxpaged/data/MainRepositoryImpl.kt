package com.example.mvrxpaged.data

import com.example.mvrxpaged.di.ApplicationScope
import com.example.mvrxpaged.domain.entity.BannerData
import com.example.mvrxpaged.domain.entity.CategoryData
import com.example.mvrxpaged.domain.entity.DealData
import com.example.mvrxpaged.domain.entity.MainViewType
import com.example.mvrxpaged.domain.repository.MainRepository
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

    // obtain the layer from A/B testing
    override fun getMainScreenLayer(): List<MainViewType> {
        return if (Random.nextBoolean()) layerA else layerB
    }

    override fun getBanner(name: String): BannerData {
        Thread.sleep(1000)
        return BannerData("banner name:  $name")
    }

    override fun getCategory(code: String): CategoryData {
        Thread.sleep(1000)
        return CategoryData("category $code")
    }

    override fun getDeal(code: String): DealData {
        Thread.sleep(1000)
        return DealData("deal $code")
    }

}