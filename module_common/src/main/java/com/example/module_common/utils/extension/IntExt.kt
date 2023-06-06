package com.example.module_common.utils.extension

import com.example.module_common.utils.screen.DensityUtil


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