package com.example.wanandroid.utils.extension

import android.view.View
import com.example.wanandroid.utils.immersion.ImmersionUtil

fun View.adaptImmersionByMargin() {
    ImmersionUtil.adaptByMargin(this)
}

fun View.adaptImmersionByPadding() {
    ImmersionUtil.adaptByPaddingTop(this)
}