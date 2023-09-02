package com.example.module_common.utils.extension

import com.example.module_common.utils.screen.DensityUtil
import kotlin.math.PI


/**
 * dp->px
 */
fun Int.dp2px(): Int {
    return DensityUtil.dp2px(this)
}

/**
 * sp->px
 */
fun Int.sp2px(): Int {
    return DensityUtil.sp2px(this)
}

/**
 * px->dp
 */
fun Int.px2dp(): Int {
    return DensityUtil.px2dp(this)
}

/**
 * px->sp
 */
fun Int.px2sp(): Int {
    return DensityUtil.px2sp(this)
}

/**
 * dp->px
 */
fun Float.dp2px(): Float {
    return DensityUtil.dp2px(this)
}

/**
 * sp->px
 */
fun Float.sp2px(): Float {
    return DensityUtil.sp2px(this)
}

/**
 * px->dp
 */
fun Float.px2dp(): Float {
    return DensityUtil.px2dp(this)
}

/**
 * px->sp
 */
fun Float.px2sp(): Float {
    return DensityUtil.px2sp(this)
}

/**
 * 弧度->角度
 */
fun Float.radian2Angle(): Float {
    return this * (180 / PI).toFloat()
}

/**
 * 角度->弧度
 */
fun Float.angle2Radian(): Float {
    return this * (PI / 180).toFloat()
}