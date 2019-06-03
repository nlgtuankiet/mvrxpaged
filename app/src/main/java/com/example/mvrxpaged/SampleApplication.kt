package com.example.mvrxpaged

import com.example.mvrxpaged.data.MainRepositoryImpl
import com.example.mvrxpaged.di.ApplicationScope
import com.example.mvrxpaged.domain.repository.MainRepository
import com.example.mvrxpaged.ui.main.MainActivityModule
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication

class SampleApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerSampleApplication_Component.create()
    }


    @dagger.Component(
        modules = [
            AndroidSupportInjectionModule::class,
            MainActivityModule::class,
            Component.Binding::class
        ]
    )
    @ApplicationScope
    interface Component : AndroidInjector<SampleApplication> {

        @Module
        interface Binding {
            @Binds
            fun mainRepo(impl: MainRepositoryImpl): MainRepository
        }
    }


}