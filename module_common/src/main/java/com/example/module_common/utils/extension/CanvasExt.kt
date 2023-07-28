package com.example.module_common.utils.extension

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF

/**
 * 保存并恢复
 */
fun Canvas.saveAndRestore(action: Canvas.() -> Unit) {
    //保存
    save()
    //画布转换操作
    action()
    //恢复
    restore()
}

/**
 * 保存图层并恢复
 */
inline fun Canvas.withSaveLayer(
    bounds: RectF? = null,
    paint: Paint? = null,
    block: Canvas.() -> Unit
) {
    val checkpoint = saveLayer(bounds, paint)
    block()
    restoreToCount(checkpoint)
}

/**
 * 保存图层并恢复
 */
inline fun Canvas.withSaveLayer(
    left: Float,
    top: Float,
    right: Float,
    bottom: Float,
    paint: Paint? = null,
    block: Canvas.() -> Unit
) {
    val checkpoint = saveLayer(left, top, right, bottom, paint)
    block()
    restoreToCount(checkpoint)
}