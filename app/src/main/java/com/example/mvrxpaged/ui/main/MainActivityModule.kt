package com.example.mvrxpaged.ui.main

import com.example.mvrxpaged.di.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface MainActivityModule {

    @ContributesAndroidInjector(modules = [MainFragmentModule::class])
    @ActivityScope
    fun activity(): MainActivity
}