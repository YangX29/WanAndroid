package com.example.module_common.utils.extension

import android.view.View
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

inline fun <reified LP : LayoutParams> View.changeLayoutParams(changer: LP.() -> Unit) {
    if (layoutParams is LP) {
        val lp = layoutParams as LP
        changer.invoke(lp)
        layoutParams = lp
    }
}