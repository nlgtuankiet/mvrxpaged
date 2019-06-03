package com.example.mvrxpaged.domain.interactor

import com.example.mvrxpaged.di.ApplicationScope
import com.example.mvrxpaged.domain.entity.DealData
import com.example.mvrxpaged.domain.repository.MainRepository
import io.reactivex.Observable
import javax.inject.Inject

@ApplicationScope
class GetDeal @Inject constructor(
    private val mainRepository: MainRepository
) {

    operator fun invoke(code: String): Observable<DealData> {
        return mainRepository.getDeal(code)
    }
}