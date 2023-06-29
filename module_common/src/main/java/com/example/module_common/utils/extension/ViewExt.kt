package com.example.module_common.utils.extension

import android.content.res.TypedArray
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