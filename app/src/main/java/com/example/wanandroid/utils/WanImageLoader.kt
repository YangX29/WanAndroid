package com.example.wanandroid.utils

import android.content.Context
import coil.ImageLoader
import com.example.wanandroid.R

/**
 * @author: Yang
 * @date: 2023/1/31
 * @description: ImageLoader生成器
 */
object WanImageLoader {

    //图片淡入时长，ms
    private const val TIME_CROSS_FADE = 200

    /**
     * 生成ImageLoader
     */
    fun create(context: Context): ImageLoader {
        // TODO error显示问题
        return ImageLoader.Builder(context)
            .crossfade(true)
            .crossfade(TIME_CROSS_FADE)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.error)
            .build()
    }

}