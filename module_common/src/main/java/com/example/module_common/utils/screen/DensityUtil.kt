package com.example.module_common.utils.screen

import android.content.res.Resources
import android.util.TypedValue
import kotlin.math.roundToInt


/**
 * @author: Yang
 * @date: 2023/1/12
 * @description: 屏幕适配相关工具类
 */
object DensityUtil {

    private const val TAG = "DensityUtil"

    /**
     * dp转px
     */
    fun dp2px(dp: Int): Int {
        val density = getDensity()
        return (density * dp).roundToInt()
    }

    /**
     * sp转px
     */
    fun sp2px(sp: Int): Int {
        val density = getScaledDensity()
        return (density * sp).roundToInt()
    }

    /**
     * px转dp
     */
    fun px2dp(px: Int): Int {
        val density = getDensity()
        return (px / density).roundToInt()
    }

    /**
     * px转sp
     */
    fun px2sp(px: Int): Int {
        val density = getScaledDensity()
        return (px / density).roundToInt()
    }

    /**
     * 获取对应单位px
     */
    fun applyDimension(value: Float, unit: Int): Float {
        val metrics = Resources.getSystem().displayMetrics
        when (unit) {
            TypedValue.COMPLEX_UNIT_PX -> return value
            TypedValue.COMPLEX_UNIT_DIP -> return value * metrics.density
            TypedValue.COMPLEX_UNIT_SP -> return value * metrics.scaledDensity
            TypedValue.COMPLEX_UNIT_PT -> return value * metrics.xdpi * (1.0f / 72)
            TypedValue.COMPLEX_UNIT_IN -> return value * metrics.xdpi
            TypedValue.COMPLEX_UNIT_MM -> return value * metrics.xdpi * (1.0f / 25.4f)
        }
        return 0f
    }

    /**
     * 获取density
     */
    fun getDensity(): Float {
        return Resources.getSystem().displayMetrics.density
    }

    /**
     * 获取sp的density
     */
    fun getScaledDensity(): Float {
        return Resources.getSystem().displayMetrics.scaledDensity
    }

    /**
     * 获取dpi
     */
    fun getDensityDpi(): Int {
        return Resources.getSystem().displayMetrics.densityDpi
    }

    /**
     * 设置density
     */
    fun setDensity(target: Float) {
        val density = getDensity()
        val scaledDensity = getScaledDensity()
        val displayMetrics = Resources.getSystem().displayMetrics;
        displayMetrics.density = target
        displayMetrics.scaledDensity = target * (scaledDensity / density)
        displayMetrics.densityDpi = (target * 160).toInt()
    }

}