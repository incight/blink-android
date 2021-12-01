package com.blink.blinkshopping.ui.videoplayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blink.blinkshopping.*
import com.blink.blinkshopping.base.Resource
import com.blink.blinkshopping.models.BaseCategorySliders
import com.blink.blinkshopping.models.BaseRecommended
import com.blink.blinkshopping.repository.*
import com.blink.blinkshopping.type.AggregationsInput
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import javax.inject.Inject

class VideoPlayeryFragmentViewModel @Inject constructor(

) : ViewModel() {

    override fun onCleared() {
        super.onCleared()
    }

}