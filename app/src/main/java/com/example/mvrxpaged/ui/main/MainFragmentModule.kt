package com.example.mvrxpaged.ui.main

import android.os.Bundle
import com.example.mvrxpaged.di.FormViewModel
import com.example.mvrxpaged.di.FragmentScope
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
interface MainFragmentModule {

    @ContributesAndroidInjector(modules = [Provision::class])
    @FragmentScope
    fun fragment(): MainFragment

    @Module
    object Provision {
        @Provides
        @FormViewModel
        @FragmentScope
        @JvmStatic
        fun viewModelHolder(fragment: MainFragment): MainViewModel = fragment.viewModel

        @Provides
        @FragmentScope
        @JvmStatic
        fun args(fragment: MainFragment): MainArgs = MainArgs.fromBundle(fragment.arguments ?: Bundle.EMPTY)
    }
}