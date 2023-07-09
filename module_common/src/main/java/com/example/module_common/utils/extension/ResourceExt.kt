package com.example.module_common.utils.extension

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
    return ContextUtils.getContext().getColor(color)
}

/**
 * 获取drawable
 */
fun getDrawableRes(@DrawableRes drawable: Int): Drawable? {
    return ContextUtils.getContext().getDrawable(drawable)
}

/**
 * 获取String
 */
fun getStringRes(@StringRes string: Int): String {
    return ContextUtils.getContext().getString(string)
}

/**
 * 获取dimension
 */
fun getDimensionRes(@DimenRes dimension: Int): Float {
    return ContextUtils.getContext().resources.getDimension(dimension)
}