package com.example.wanandroid.view.common

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.core.graphics.toRectF
import kotlin.math.min

/**
 * @author: Yang
 * @date: 2023/3/13
 * @description: 圆角Drawable
 */
class RoundDrawable(
    @ColorInt private var color: Int,
    private var round: Float = 0f,
    private val style: Style = Style.CIRCLE,
) : Drawable() {

    enum class Style {
        CIRCLE, RECTANGLE
    }

    //画笔
    private val paint = Paint()

    init {
        paint.color = color
        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL
    }

    override fun draw(canvas: Canvas) {
        when (style) {
            Style.CIRCLE -> {//圆
                val radius = min(bounds.exactCenterX(), bounds.exactCenterY())
                canvas.drawCircle(bounds.exactCenterX(), bounds.exactCenterY(), radius, paint)
            }
            Style.RECTANGLE -> {//圆角矩形
                canvas.drawRoundRect(bounds.toRectF(), round, round, paint)
            }
        }
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
        invalidateSelf()
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
        invalidateSelf()
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    /**
     * 设置颜色
     */
    public fun setColor(@ColorInt color: Int) {
        paint.color = color
        invalidateSelf()
    }

    /**
     * 设置圆角
     */
    public fun setRound(round: Float) {
        this.round = round
        invalidateSelf()
    }

}