package com.example.module_common.utils.span

import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import androidx.annotation.ColorInt

/**
 * 通用点击Span
 */
class CommonClickableSpan(@ColorInt private val color: Int, private val action: () -> Unit) :
    ClickableSpan() {

    override fun onClick(widget: View) {
        //点击事件
        action.invoke()
    }

    override fun updateDrawState(ds: TextPaint) {
        //具体样式
        ds.color = color
        ds.isUnderlineText = false
    }
}