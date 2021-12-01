package com.blink.blinkshopping.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.os.CountDownTimer
import com.blink.blinkshopping.util.SPLASH_SCREEN_DELAY
import javax.inject.Inject


class SplashScreenViewModel @Inject constructor() : ViewModel() {

    val isFinished = MutableLiveData<Boolean>()

    init {
        object : CountDownTimer(SPLASH_SCREEN_DELAY, 1000) {

            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                isFinished.postValue(true)
            }

        }.start()
    }
}