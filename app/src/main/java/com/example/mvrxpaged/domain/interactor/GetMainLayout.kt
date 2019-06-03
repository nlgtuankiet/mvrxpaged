package com.example.mvrxpaged.domain.interactor

import com.example.mvrxpaged.di.ApplicationScope
import com.example.mvrxpaged.domain.entity.MainViewType
import com.example.mvrxpaged.domain.repository.MainRepository
import javax.inject.Inject

@ApplicationScope
class GetMainLayout @Inject constructor(
    private val mainRepository: MainRepository
) {

    operator fun invoke(): List<MainViewType> {
        return mainRepository.getMainScreenLayer()
    }
}