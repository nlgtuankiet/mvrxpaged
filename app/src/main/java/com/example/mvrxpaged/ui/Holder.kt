package com.example.mvrxpaged.ui

import javax.inject.Provider

class Holder<T>(private val func: Function0<T>) : Provider<T> {
    override fun get(): T {
        return func()
    }
}