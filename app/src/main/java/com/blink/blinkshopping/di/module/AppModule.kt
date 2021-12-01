package com.blink.blinkshopping.di.module

import com.blink.blinkshopping.ui.main.MainActivity
import com.blink.blinkshopping.ui.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AppModule {

    @ContributesAndroidInjector
    abstract fun bindSplashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun bindMainActivity(): MainActivity


}