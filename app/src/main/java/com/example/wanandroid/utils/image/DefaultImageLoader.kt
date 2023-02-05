package com.example.wanandroid.utils.image

import android.content.Context
import coil.ImageLoader

/**
 * @author: Yang
 * @date: 2023/2/5
 * @description: 默认的ImageLoader，无placeholder和error
 */
object DefaultImageLoader {

    //图片淡入时长，ms
    private const val TIME_CROSS_FADE = 200

    /**
     * 生成ImageLoader
     */
    fun create(context: Context): ImageLoader {
        return ImageLoader.Builder(context)
            .crossfade(true)
            .crossfade(TIME_CROSS_FADE)
            .build()
    }

}