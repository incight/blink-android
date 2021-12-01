package com.blink.blinkshopping.di

import android.app.Application
import com.blink.blinkshopping.App
import com.blink.blinkshopping.di.module.AppModule
import com.blink.blinkshopping.di.module.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        NetworkModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}