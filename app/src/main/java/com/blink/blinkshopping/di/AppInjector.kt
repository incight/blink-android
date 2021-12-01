package com.blink.blinkshopping.di

import com.blink.blinkshopping.App

object AppInjector {
    fun init(app: App) {
        DaggerAppComponent.builder().application(app)
            .build().inject(app)
    }
}
