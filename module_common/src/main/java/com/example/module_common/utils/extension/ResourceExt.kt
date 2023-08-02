package com.example.module_common.utils.extension

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.module_common.utils.app.ContextUtils

/**
 * 获取颜色
 */
@ColorInt
fun getColorRes(@ColorRes color: Int): Int {
    return getResource().getColor(color)
}

/**
 * 获取drawable
 */
@SuppressLint("UseCompatLoadingForDrawables")
fun getDrawableRes(@DrawableRes drawable: Int): Drawable? {
    return getResource().getDrawable(drawable)
}

/**
 * 获取bitmap
 */
fun getBitmapRes(bitmap: Int): Bitmap {
    return BitmapFactory.decodeResource(getResource(), bitmap)
}

/**
 * 获取String
 */
fun getStringRes(@StringRes string: Int): String {
    return getResource().getString(string)
}

/**
 * 获取dimension
 */
fun getDimensionRes(@DimenRes dimension: Int): Float {
    return getResource().getDimension(dimension)
}

/**
 * 获取resource
 */
fun getResource(): Resources {
    val context = ContextUtils.getContext()
    //当前模式
    val currentNightMode =
        context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
    //根据模式创建新的configuration和context
    val configuration = Configuration(context.resources.configuration)
    configuration.uiMode = if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
        Configuration.UI_MODE_NIGHT_YES
    } else {
        Configuration.UI_MODE_NIGHT_NO
    }
    return context.createConfigurationContext(configuration).resources
}