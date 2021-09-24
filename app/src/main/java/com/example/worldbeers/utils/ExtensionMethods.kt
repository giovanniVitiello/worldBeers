package com.example.worldbeers.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.bumptech.glide.Glide

val <T> T.exhaustive: T
    get() = this

fun ImageView.loadImageFromUrl(imageUrl: String?) {
    imageUrl?.let {
        val httpsImageUrl = imageUrl.replace("http://", "https://")

        Glide.with(this.context)
            .load(httpsImageUrl)
            .into(this)
    }
}

fun Context.hideKeyboard() {
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    //Find the currently focused view, so we can grab the correct window token from it.
    var view = (this as Activity).currentFocus
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    if (view == null) {
        view = View(this)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}
