package com.example.module_common.html

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.TextPaint
import android.text.style.ReplacementSpan

/**
 * 垂直方向居中Span，设置大小和颜色，fontSize为sp
 */
class CenterTextSpan(private val fontSize: Float, private val color: Int) : ReplacementSpan() {

    override fun getSize(
        paint: Paint,
        text: CharSequence?,
        start: Int,
        end: Int,
        fm: Paint.FontMetricsInt?
    ): Int {
        val newPaint = getCustomTextPaint(paint)
        return newPaint.measureText(text, start, end).toInt()
    }

    override fun draw(
        canvas: Canvas,
        text: CharSequence?,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {
        val newPaint = getCustomTextPaint(paint)
        val fontMetricsInt = newPaint.fontMetricsInt
        val offsetY = (y + fontMetricsInt.ascent + y + fontMetricsInt.descent) / 2 - (top + bottom) / 2
        canvas.drawText(text?:"", start, end, x, (y - offsetY).toFloat(), newPaint)
    }

    private fun getCustomTextPaint(srcPaint: Paint): TextPaint {
        val textPaint = TextPaint(srcPaint)
        //TODO 设置大小
//        textPaint.textSize = ConvertUtils.sp2px(fontSize).toFloat()
        textPaint.color = color
        return textPaint
    }
}