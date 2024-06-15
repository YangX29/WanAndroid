package com.android.bc.component

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import com.example.module_common.utils.extension.dp2px
import com.example.module_common.utils.extension.obtainStyledAttributes
import com.example.module_common.utils.extension.saveAndRestore
import com.example.module_common.utils.extension.sp2px
import com.example.module_common.utils.log.logE
import com.example.wanandroid.R
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.atan
import kotlin.math.min

/**
 * 圆形时间刻度SeekBar
 */
class CircleTimeSeekBar @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(context, attrs, defStyleAttr) {

    companion object {
        //总小时数
        private const val MAX_HOUR = 24

        //每个小时的刻度
        private const val GRADUATION_HOUR = 4

        //显示文字的刻度取余
        private const val GRADUATION_TEXT_HOUR = 3

        //最大角度
        private const val MAX_ANGLE = 360f
    }

    //背景色
    @ColorInt
    private var bgColor: Int = Color.GRAY

    //表盘背景色
    @ColorInt
    private var timeDialBgColor: Int = Color.WHITE

    //表盘刻度颜色
    @ColorInt
    private var graduationColor: Int = Color.GRAY

    //表盘时间刻度文字颜色
    @ColorInt
    private var graduationTextColor: Int = Color.BLACK

    //进度条颜色
    @ColorInt
    private var seekBarBgColor: Int = Color.WHITE

    //seekbar和表盘之间的间距
    private var seekBarOffset = 1f

    //刻度与表盘之间的间距
    private var graduationDialOffset = 1f

    //刻度与文字之间的间距
    private var graduationTextOffset = 1f

    //表盘刻度文字大小
    private var graduationTextSize = 12f

    //表盘尺寸，半径
    private var timeDialRadius = 200f

    //刻度宽度
    private var graduationWidth = 2f

    //短刻度长度
    private var shortGraduationLength = 20f

    //长刻度长度
    private var longGraduationLength = 10f

    //开始角度，从0点位置开始计算，顺时针
    private var startAngle = 0f

    //结束角度，从0点位置开始计算，顺时针
    private var endAngle = 360f

    //x中心点，圆心x坐标
    private val mCenterX
        get() = width.toFloat() / 2

    //y中心点，圆心y坐标
    private val mCenterY
        get() = height.toFloat() / 2

    //底部圆半径，小边半径
    private val bgRadius
        get() = min(width, height).toFloat() / 2

    //进度条宽度
    private val seekBarWidth
        get() = bgRadius - timeDialRadius - 2 * seekBarOffset

    //画笔
    private val mPaint = Paint()

    init {
        //初始化参数
        obtainStyledAttributes(attrs, R.styleable.CircleTimeSeekBar) {
            initParams(this)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.saveAndRestore {
            //修改坐标中心到圆心位置,方便计算
            canvas.translate(mCenterX, mCenterY)
            //绘制外层圆背景
            drawCircleBg(canvas)
            //绘制时间表盘
            drawCircleTimeDial(canvas)
            //绘制选中圆环
            drawSeekBar(canvas)
            //绘制图标
            drawThumbIcon(canvas)
        }
    }

    /**
     * 初始化参数
     */
    private fun initParams(ta: TypedArray) {
        ta.apply {
            //背景色
            bgColor = getColor(R.styleable.CircleTimeSeekBar_bgColor, Color.GRAY)
            //表盘色
            timeDialBgColor =
                getColor(R.styleable.CircleTimeSeekBar_bgColor, Color.WHITE)
            //表盘半径
            timeDialRadius = getDimension(R.styleable.CircleTimeSeekBar_timeDialRadius, 10f)
            //刻度文字颜色
            graduationTextColor = getColor(
                R.styleable.CircleTimeSeekBar_timeGraduationTextColor,
                Color.BLACK
            )
            //刻度文字大小
            graduationTextSize = getDimension(
                R.styleable.CircleTimeSeekBar_timeGraduationTextSize,
                11.sp2px().toFloat()
            )
            //进度条颜色
            seekBarBgColor = getColor(
                R.styleable.CircleTimeSeekBar_seekBarBgColor,
                Color.WHITE
            )
            //进度条和表盘间距
            seekBarOffset = getDimension(R.styleable.CircleTimeSeekBar_seekBarOffset, 1f)
        }
        //刻度相关属性
        shortGraduationLength = 2.dp2px().toFloat()
        longGraduationLength = 4.dp2px().toFloat()
        graduationWidth = 1.dp2px().toFloat()
        graduationDialOffset = 2.dp2px().toFloat()
        graduationTextOffset = 2.dp2px().toFloat()
    }

    /**
     * 绘制圆形底部背景
     */
    private fun drawCircleBg(canvas: Canvas) {
        //设置画笔
        resetPaint { color = bgColor }
        //绘制背景
        canvas.drawCircle(0f, 0f, bgRadius, mPaint)
    }

    /**
     * 绘制圆形时间表盘
     */
    private fun drawCircleTimeDial(canvas: Canvas) {
        //设置表盘画笔
        resetPaint { color = timeDialBgColor }
        //绘制表盘圆形背景
        canvas.drawCircle(0f, 0f, timeDialRadius, mPaint)
        //绘制时间刻度
        val count = MAX_HOUR * GRADUATION_HOUR
        val angle = MAX_ANGLE / count
        for (i in 0..count) {
            canvas.saveAndRestore {
                //旋转画布,绘制刻度
                rotate(angle * i)
                //刻度画笔
                resetPaint {
                    color = graduationColor
                    strokeWidth = graduationWidth
                    strokeCap = Paint.Cap.ROUND
                }
                //刻度起始点
                val startY = -timeDialRadius + graduationDialOffset
                if (i % GRADUATION_HOUR == 0) {
                    //小时刻度,长刻度
                    val endY = startY + longGraduationLength
                    //绘制刻度
                    drawLine(0f, startY, 0f, endY, mPaint)
                } else {
                    //短刻度
                    val endY = startY + shortGraduationLength
                    //绘制刻度
                    drawLine(0f, startY, 0f, endY, mPaint)
                }
                //绘制刻度文字
                if (i % (GRADUATION_HOUR * GRADUATION_TEXT_HOUR) == 0 && i != count) {
                    resetPaint {
                        color = graduationTextColor
                        textSize = graduationTextSize
                        textAlign = Paint.Align.CENTER
                        strokeWidth = 2f
                    }
                    //刻度小时
                    val hour = (i / GRADUATION_HOUR).toString()
                    //计算文字位置
                    val textBaseline = mPaint.fontMetrics.run { abs(top - baseline) }
                    val textHeight = mPaint.fontMetrics.run { abs(top - bottom) }
                    val textY =
                        -timeDialRadius + longGraduationLength + graduationDialOffset + graduationTextOffset + textBaseline
                    val textCenterY = textY - textBaseline + (textHeight) / 2
                    saveAndRestore {
                        //以文字中心为圆心逆时针旋转，修正文字角度
                        rotate(-angle * i, 0f, textCenterY)
                        //绘制文字
                        drawText(hour, 0f, textY, mPaint)
                    }
                }
            }
        }
    }

    /**
     * 绘制弧形SeekBar
     */
    private fun drawSeekBar(canvas: Canvas) {
        canvas.saveAndRestore {
            //逆时针旋转90度，从0点位置开始计算角度
            rotate(-90f)
            //重置画笔
            resetPaint {
                color = seekBarBgColor
                strokeWidth = seekBarWidth
                style = Paint.Style.STROKE
                strokeCap = Paint.Cap.ROUND
            }
            //seekbar圆弧半径
            val radius = (bgRadius + timeDialRadius) / 2
            //圆角造成的角度偏差，圆角是以seekbarWidth/2为半径的半圆
            val capAngle = (atan(seekBarWidth / (2 * radius)) * (180 / PI)).toFloat()
            //计算seekbar实际起始角度和滑过角度
            val correctStartAngle = startAngle + capAngle
            val correctSwapAngle = endAngle - startAngle - 2 * capAngle
            //绘制圆弧
            drawArc(
                -radius, -radius, radius, radius,
                correctStartAngle,
                correctSwapAngle,
                false,
                mPaint
            )
        }
    }

    /**
     * 绘制图标
     */
    private fun drawThumbIcon(canvas: Canvas) {
        //绘制按住阴影
        canvas.saveAndRestore {
            //逆时针旋转90度，从0点位置开始计算角度，x方向向上，y方向向右
            rotate(-90f)
            //重置画笔
            resetPaint { color = Color.RED }
            //seekbar圆弧半径
            val radius = (bgRadius + timeDialRadius) / 2
            //圆角造成的角度偏差，圆角是以seekbarWidth/2为半径的半圆
            val capAngle = (atan(seekBarWidth / (2 * radius)) * (180 / PI)).toFloat()
            //计算seekbar实际起始角度和结束角度
            val correctStartAngle = startAngle + capAngle
            val correctSwapAngle = endAngle - startAngle - 2 * capAngle
            //阴影半径
            val smallRadius = (seekBarWidth - 10f) / 2
            //绘制阴影
            canvas.saveAndRestore {
                //旋转correctStartAngle
                rotate(correctStartAngle)
                //绘制起始位置阴影
                drawCircle(radius, 0f, smallRadius, mPaint)
                //继续旋转correctSwapAngle
                rotate(correctSwapAngle)
                //绘制结束位置阴影
                drawCircle(radius, 0f, smallRadius, mPaint)
            }

        }
    }

    /**
     * 重置画笔
     */
    private fun resetPaint(block: Paint.() -> Unit) {
        mPaint.apply {
            reset()
            isAntiAlias = true
            block()
        }
    }


}