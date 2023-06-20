package com.example.module_common.utils.screen

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import com.example.module_common.utils.app.ContextUtil
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
     * 获取当前应用实际density
     */
    fun getDensity(): Float {
        return ContextUtil.getContext().resources.displayMetrics.density
    }

    /**
     * 获取当前应用实际scaledDensity
     */
    fun getScaledDensity(): Float {
        return ContextUtil.getContext().resources.displayMetrics.scaledDensity
    }

    /**
     * 获取当前应用实际dpi
     */
    fun getDensityDpi(): Float {
        return ContextUtil.getContext().resources.displayMetrics.scaledDensity
    }

    /**
     * 设置当前实例的density
     */
    fun setDensity(target: Float, context: Context) {
        val displayMetrics = context.resources.displayMetrics
        val density = displayMetrics.density
        val scaledDensity = displayMetrics.scaledDensity
        displayMetrics.density = target
        displayMetrics.scaledDensity = target * (scaledDensity / density)
        displayMetrics.densityDpi = (target * 160).toInt()
    }

    /**
     * 获取系统density，通过auto_size进行适配后，和实际density可能不同，见[getDensity]
     */
    fun getSystemDensity(): Float {
        return Resources.getSystem().displayMetrics.density
    }

    /**
     * 获取系统sp的density，通过auto_size进行适配后，和实际density可能不同，见[getScaledDensity]
     */
    fun getSystemScaledDensity(): Float {
        return Resources.getSystem().displayMetrics.scaledDensity
    }

    /**
     * 获取系统dpi，通过auto_size进行适配后，和实际spi可能不同，见[getDensityDpi]
     */
    fun getSystemDensityDpi(): Int {
        return Resources.getSystem().displayMetrics.densityDpi
    }

}