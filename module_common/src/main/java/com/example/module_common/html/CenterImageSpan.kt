package com.example.module_common.html

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.style.ImageSpan

/**
 * 图文居中ImageSpan
 */
class CenterImageSpan(drawable: Drawable) : ImageSpan(drawable) {

    override fun getSize(
        paint: Paint,
        text: CharSequence?,
        start: Int,
        end: Int,
        fm: Paint.FontMetricsInt?
    ): Int {
        val rect = drawable.bounds
        fm?.apply {
            val fontHeight = paint.fontMetricsInt.bottom - paint.fontMetricsInt.top
            val drHeight = rect.bottom - rect.top
            val t = drHeight / 2 - fontHeight / 4
            val b = drHeight / 2 + fontHeight / 4
            //重新设置文字位置
            ascent = -b
            top = -b
            bottom = t
            descent = t
        }
        return rect.right
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
        //计算y方向的位移
        val offset = ((bottom-top)-drawable.bounds.bottom)/2+top
        canvas.save()
        //绘制图片位移一段距离
        canvas.translate(x, offset.toFloat())
        drawable.draw(canvas)
        canvas.restore()
    }

}