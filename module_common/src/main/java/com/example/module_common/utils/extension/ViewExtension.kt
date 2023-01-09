package com.example.module_common.utils.extension

import android.view.View

fun View.visible(visible : Boolean = true) {
    visibility = if (visible) View.VISIBLE else View.INVISIBLE
}

fun View.invisible(invisible : Boolean = true) {
    visibility = if (!invisible) View.VISIBLE else View.INVISIBLE
}

fun View.gone(gone : Boolean = true) {
    visibility = if (gone) View.GONE else View.VISIBLE
}