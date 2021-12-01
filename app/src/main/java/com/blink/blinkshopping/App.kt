package com.blink.blinkshopping

import android.app.Activity
import android.app.Application
import android.content.Context
import com.blink.blinkshopping.di.AppInjector
import com.blink.blinkshopping.di.module.NetworkModule
import com.nikhil.viewpager2withexoplayer2.application.ExoPlayerCaching
import com.nikhil.viewpager2withexoplayer2.application.StorageUtil
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class App : Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)

        context = applicationContext
        val networkModule = NetworkModule()

        initExoPlayerCaching()

    }


private fun initExoPlayerCaching() {

    val exoPlayerCaching = ExoPlayerCaching()
    exoPlayerCaching.init(this, StorageUtil.getExoCacheDir(this))

}




    companion object {
        val instance: App? = null
        var context: Context? = null
            private set
    }


}