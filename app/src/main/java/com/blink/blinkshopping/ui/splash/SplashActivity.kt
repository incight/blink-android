package com.blink.blinkshopping.ui.splash

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.blink.blinkshopping.R
import com.blink.blinkshopping.base.BaseDaggerActivity
import com.blink.blinkshopping.ui.main.MainActivity
import dagger.android.AndroidInjection
import javax.inject.Inject


class SplashActivity : BaseDaggerActivity() {

    private lateinit var splashScreenViewModel: SplashScreenViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.splash_screen)
        AndroidInjection.inject(this)

        splashScreenViewModel = ViewModelProviders.of(this, viewModelFactory).get(SplashScreenViewModel::class.java)

        splashScreenViewModel.isFinished.observe(this, Observer {
            startActivity(Intent(this@SplashActivity,MainActivity::class.java))
            finish()
        })
    }
}
