package com.kevin.navmedia.ui.component.binding

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * Created by quf93 on 2018-05-04.
 */
object ImageBindingAdapter {
    @JvmStatic
    @BindingAdapter("image")
    fun loadImage(view: ImageView, imagePath: String) {
        Glide.with(view.context)
                .load(imagePath)
                .apply(RequestOptions.centerCropTransform())
                .into(view)
    }

    @JvmStatic
    @BindingAdapter("circle_image")
    fun loadCircleImage(view: ImageView, imagePath: String) {
        Glide.with(view.context)
                .load(imagePath)
                .apply(RequestOptions.circleCropTransform())
                .into(view)
    }
}