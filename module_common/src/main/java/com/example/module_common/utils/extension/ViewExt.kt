package com.example.module_common.utils.extension

import android.content.res.TypedArray
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams

fun View.visible(visible: Boolean = true) {
    visibility = if (visible) View.VISIBLE else View.INVISIBLE
}

fun View.invisible(invisible: Boolean = true) {
    visibility = if (!invisible) View.VISIBLE else View.INVISIBLE
}

fun View.gone(gone: Boolean = true) {
    visibility = if (gone) View.GONE else View.VISIBLE
}

fun View.removeFromParent() {
    (parent as? ViewGroup)?.removeView(this)
}

inline fun <reified LP : LayoutParams> View.changeLayoutParams(changer: LP.() -> Unit) {
    if (layoutParams is LP) {
        val lp = layoutParams as LP
        changer.invoke(lp)
        layoutParams = lp
    }
}

inline fun View.obtainStyledAttributes(
    set: AttributeSet?,
    attrs: IntArray,
    block: TypedArray.() -> Unit
) {
    val ta = context.obtainStyledAttributes(set, attrs)
    block.invoke(ta)
    ta.recycle()
}

/**
 * 更新View尺寸
 */
fun View.updateSize(width: Int, height: Int) {
    changeLayoutParams<LayoutParams> {
        this.height = height
        this.width = width
    }
}

/**
 * 更新View高度
 */
fun View.updateHeight(height: Int) {
    changeLayoutParams<LayoutParams> {
        this.height = height
    }
}

/**
 * 更新View宽度
 */
fun View.updateWidth(width: Int) {
    changeLayoutParams<LayoutParams> {
        this.width = width
    }
}

/**
 * View截图生成Bitmap,注意该方法需要确保View已经完成测量绘制,否则当View宽高为0时会导致闪退,可以在post中使用
 */
fun View.createBitmap(): Bitmap {
    //绘制bitmap
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    draw(canvas)
    canvas.save()
    return bitmap
}