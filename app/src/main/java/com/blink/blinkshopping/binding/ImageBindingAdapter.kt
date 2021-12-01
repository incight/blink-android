package com.blink.blinkshopping.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


object ImageBindingAdapter {

    @JvmStatic
    @BindingAdapter("bind:loadimage")
    fun setImageResource(imageView: ImageView, resource: Int) {
        imageView.setImageResource(resource)
    }

    @JvmStatic
    @BindingAdapter("bind:loadimage")
    fun setImageGlide(imageView: ImageView, url: String?){
        if (url != null && !url.isEmpty()) {
            Glide
                .with(imageView.context)
                .load(url)
                .into(imageView)
        }
    }

}
