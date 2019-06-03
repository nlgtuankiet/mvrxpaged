package com.example.mvrxpaged.domain.repository

import com.example.mvrxpaged.domain.entity.BannerData
import com.example.mvrxpaged.domain.entity.CategoryData
import com.example.mvrxpaged.domain.entity.DealData
import com.example.mvrxpaged.domain.entity.MainViewType
import io.reactivex.Observable

interface MainRepository {

    fun getCategory(code: String): Observable<CategoryData>
    fun getDeal(code: String): Observable<DealData>
    fun getBanner(name: String): Observable<BannerData>

    // from remote config for a/b testing purpose
    // this example assume that you use Firebase Remote Config, its api is synchronous
    fun getMainScreenLayer(): List<MainViewType>
}