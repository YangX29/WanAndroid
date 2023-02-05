package com.example.wanandroid.utils.extension

import android.widget.ImageView
import coil.load
import coil.request.Disposable
import coil.request.ImageRequest
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