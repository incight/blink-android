package com.blink.blinkshopping.models

import android.os.Parcelable
import com.blink.blinkshopping.util.Url
import kotlinx.android.parcel.Parcelize

/**
 * Created by Maruthi Ram Yadav on 11/11/2020.
 */
@Parcelize
data class Image(var url: String, var label: String) : Parcelable {

    fun urlVaidator(): String? {
        var path: String = ""

        if (url != null) {
            if (url.contains("https")) {
                path = ""
            } else {
                path = Url
            }
        }
        return path
    }

}