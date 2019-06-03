package com.example.mvrxpaged.domain.interactor

import com.example.mvrxpaged.di.ApplicationScope
import com.example.mvrxpaged.domain.entity.BannerData
import com.example.mvrxpaged.domain.repository.MainRepository
import io.reactivex.Observable
import javax.inject.Inject

@ApplicationScope
class GetBanner @Inject constructor(
    private val mainRepository: MainRepository
) {

    operator fun invoke(name: String): Observable<BannerData> {
        return mainRepository.getBanner(name)
    }
}