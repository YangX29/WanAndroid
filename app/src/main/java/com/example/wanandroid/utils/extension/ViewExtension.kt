package com.example.wanandroid.utils.extension

import android.view.View
import com.example.wanandroid.utils.immersion.ImmersionUtils

fun View.adaptImmersionByMargin() {
    ImmersionUtils.adaptByMargin(this)
}

fun View.adaptImmersionByPadding() {
    ImmersionUtils.adaptByPaddingTop(this)
}