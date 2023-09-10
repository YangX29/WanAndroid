package com.example.wanandroid.utils.extension

import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.graphics.drawable.DrawableCompat
import coil.load
import coil.request.Disposable
import coil.request.ImageRequest
import com.example.module_common.utils.extension.getColorRes
import com.example.wanandroid.utils.image.DefaultImageLoader

/**
 * 使用DefaultImageLoader加载图片
 */
fun ImageView.loadWithDefault(
    data: Any,
    builder: ImageRequest.Builder.() -> Unit = {}
): Disposable {
    return load(data, DefaultImageLoader.create(context), builder)
}

/**
 * 设置图片tint,需要先设置drawable
 */
fun ImageView.tintColorRes(@ColorRes colorRes: Int) {
    tintColor(getColorRes(colorRes))
}

/**
 * 设置图片tint,需要先设置drawable
 */
fun ImageView.tintColor(@ColorInt color: Int) {
    val tintDrawable = DrawableCompat.wrap(drawable)
    DrawableCompat.setTintList(tintDrawable, null)
    DrawableCompat.setTint(tintDrawable, color)
    setImageDrawable(tintDrawable)
}