package com.graduation.core.extensions.ui

import android.content.res.ColorStateList
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import com.graduation.core.R
import com.bumptech.glide.Glide
import com.stfalcon.imageviewer.StfalconImageViewer

fun <T> ImageView.loadImage(imagePath: T , loadingDrawableId:Int = R.drawable.loading_image_large) {
    Glide.with(this.context)
        .load(imagePath)
        .placeholder(loadingDrawableId)
        .into(this)
}

fun ImageView.setColorOfImage(@ColorRes color: Int) {
    ImageViewCompat.setImageTintList(
        this,
        ColorStateList.valueOf(ContextCompat.getColor(context, color))
    )
}

fun <T> View.openImageViewer(images: List<T>) {
    setOnClickListener {
        StfalconImageViewer.Builder(this.context, images) { view, image ->
            view.loadImage(image)
        }.withHiddenStatusBar(false).show()
    }
}