package com.example.regolo.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

val <T> T.exhaustive: T
    get() = this

fun ImageView.loadRoundImageFromUrl(imageUrl: String?) {
    imageUrl?.let {
        val httpsImageUrl = imageUrl.replace("http://", "https://")

        Glide.with(this.context)
            .load(httpsImageUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(this)
    }
}

fun ImageView.loadImageFromUrl(imageUrl: String?) {
    imageUrl?.let {
        val httpsImageUrl = imageUrl.replace("http://", "https://")

        Glide.with(this.context)
            .load(httpsImageUrl)
            .into(this)
    }
}

fun ImageView.transformImageToRoundImage(imageUrl: String?) {
    imageUrl?.let {

        Glide.with(this.context)
            .load(imageUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(this)
    }
}
