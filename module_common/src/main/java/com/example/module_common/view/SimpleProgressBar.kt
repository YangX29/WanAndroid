package com.example.module_common.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.module_common.R
import com.example.module_common.utils.log.logE

/**
 * @author: Yang
 * @date: 2023/2/14
 * @description: 简单自定义进度条View
 */
class SimpleProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    //最大值
    private var max = 100

    //当前进度
    var progress = 0
        private set

    //百分比
    val percent: Float
        get() = progress.toFloat() / max.toFloat()

    //前景色
    private var fgColor = Color.BLUE

    //背景色
    private var bgColor = Color.WHITE

    //画笔
    private val mPaint = Paint()

    init {
        //获取参数
        val ta = context.obtainStyledAttributes(attrs, R.styleable.SimpleProgressBar)
        max = ta.getInt(R.styleable.SimpleProgressBar_max, 100)
        progress = ta.getInt(R.styleable.SimpleProgressBar_progress, 0)
        fgColor = ta.getColor(R.styleable.SimpleProgressBar_fgColor, Color.BLUE)
        bgColor = ta.getColor(R.styleable.SimpleProgressBar_bgColor, Color.WHITE)
        ta.recycle()
        //抗锯齿
        mPaint.isAntiAlias = true
        mPaint.isFilterBitmap = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //绘制progress
        if (progress > 0) {
            drawProgress(canvas)
        }
        //绘制bar
        if (progress < max) {
            drawBar(canvas)
        }
    }

    /**
     * 绘制progress
     */
    private fun drawProgress(canvas: Canvas?) {
        //设置颜色
        mPaint.color = fgColor
        //计算坐标
        val right = percent * width
        //绘制
        canvas?.drawRect(0f, 0f, right, height.toFloat(), mPaint)
    }

    /**
     * 绘制bar
     */
    private fun drawBar(canvas: Canvas?) {
        //设置颜色
        mPaint.color = bgColor
        //计算坐标
        val left = percent * width
        //绘制
        canvas?.drawRect(left, 0f, width.toFloat(), height.toFloat(), mPaint)
    }

    /**
     * 更新进度条
     */
    fun update(progress: Int, max: Int = this.max) {
        this.progress = progress
        this.max = max
        invalidate()
    }

}