package com.example.module_common.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.animation.doOnStart
import com.example.module_common.R
import com.example.module_common.utils.extension.dp2px
import com.example.module_common.utils.extension.obtainStyledAttributes
import kotlin.math.max

/**
 * @author: Yang
 * @date: 2023/5/3
 * @description: 开关按钮View
 */
class SwitchButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        //透明度最大值
        private const val ALPHA_MAX = 255

        //最大动画时长
        private const val MAX_DURATION_ANIMATOR = 200L

        //最小动画时长
        private const val MIN_DURATION_ANIMATOR = 50L
    }

    //开启开关背景
    private lateinit var onBackground: Drawable

    //关闭开关背景
    private lateinit var offBackground: Drawable

    //开关滑块背景
    private lateinit var thumbBackground: Drawable

    //滑块边距，决定了滑块的大小
    private var thumbPadding = 0f

    //前景色透明度
    private val foregroundAlpha: Int
        get() = (percent * ALPHA_MAX).toInt()

    //圆角矩形半径
    private val rectRadius: Float
        get() = height.toFloat() / 2

    //滑块半径
    private val thumbRadius: Float
        get() = height / 2 - thumbPadding

    //滑块x坐标最大值
    private val thumbRangeMax: Float
        get() = width - height.toFloat() / 2

    //滑块x坐标最小值
    private val thumbRangeMin: Float
        get() = height.toFloat() / 2

    //滑块中心点位置
    private val thumbCx: Float
        get() = (thumbRangeMax - thumbRangeMin) * percent + thumbRangeMin

    //画笔
    private val paint = Paint()

    //开关状态
    private var isOn = false

    //滑块滑动比例
    private var percent = 0f

    //开关切换监听
    private var switchListener: ((Boolean) -> Unit)? = null

    //按住滑块
    private var isPressedThumb = false

    //是否在拖动
    private var isDrag = false
    private var lastX = 0f

    //动画
    private var animator: ValueAnimator? = null


    init {
        obtainStyledAttributes(attrs, R.styleable.SwitchButton) {
            onBackground =
                getDrawable(R.styleable.SwitchButton_on_background) ?: ColorDrawable(Color.GRAY)
            offBackground =
                getDrawable(R.styleable.SwitchButton_off_background) ?: ColorDrawable(Color.BLUE)
            thumbBackground =
                getDrawable(R.styleable.SwitchButton_thumb_background) ?: ColorDrawable(Color.WHITE)
            thumbPadding = getDimension(R.styleable.SwitchButton_thumb_padding, 2.dp2px().toFloat())
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                isPressedThumb = inThumb(event.x, event.y)
                lastX = event.x
                isDrag = false
            }

            MotionEvent.ACTION_MOVE -> {
                if (isPressedThumb) {
                    isDrag = true
                    //计算thumb位置
                    val offset = event.x - lastX
                    val newX =
                        (thumbCx + offset).coerceAtLeast(thumbRangeMin).coerceAtMost(thumbRangeMax)
                    percent =
                        ((newX - thumbRangeMin) / thumbRangeMax).coerceAtLeast(0f).coerceAtMost(1f)
                    //重绘
                    invalidate()
                }
            }

            MotionEvent.ACTION_UP -> {
                if (isPressedThumb) {
                    if (isDrag) {
                        //与之前状态相同不回调
                        val callback = (isOn != percent >= 0.5f)
                        //超过一半位置开启，否则关闭
                        if (percent >= 0.5f) {
                            switchToOn(callback)
                        } else {
                            switchToOff(callback)
                        }
                    } else {
                        //未拖动，点击滑块
                        performClick()
                    }
                } else {
                    //点击事件
                    performClick()
                }
            }

            MotionEvent.ACTION_CANCEL -> {
                //切换
                switch()
            }
        }
        return true
    }

    override fun performClick(): Boolean {
        //切换开关
        switch()
        return super.performClick()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            //校验合法性
            checkValues()
            //绘制背景
            drawBackground(it)
            //绘制前景色
            drawForeground(it)
            //绘制滑块
            drawThumb(it)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animator?.cancel()
        animator = null
    }

    /**
     * 绘制按钮背景
     */
    private fun drawBackground(canvas: Canvas) {
        if (percent >= 1f) return
        //重置
        resetPaint()
        //绘制
        if (offBackground is ColorDrawable) {
            paint.color = (offBackground as ColorDrawable).color
            canvas.drawRoundRect(
                0f,
                0f,
                width.toFloat(),
                height.toFloat(),
                rectRadius,
                rectRadius,
                paint
            )
        } else {
            offBackground.setBounds(0, 0, width, height)
            offBackground.draw(canvas)
        }
    }

    /**
     * 绘制按钮开启前景色
     */
    private fun drawForeground(canvas: Canvas) {
        if (percent <= 0f) return
        //重置
        resetPaint()
        //绘制
        if (onBackground is ColorDrawable) {
            paint.color = (onBackground as ColorDrawable).color
            paint.alpha = foregroundAlpha
            canvas.drawRoundRect(
                0f,
                0f,
                width.toFloat(),
                height.toFloat(),
                rectRadius,
                rectRadius,
                paint
            )
        } else {
            onBackground.setBounds(0, 0, width, height)
            onBackground.alpha = foregroundAlpha
            onBackground.draw(canvas)
        }
    }

    /**
     * 绘制滑块
     */
    private fun drawThumb(canvas: Canvas) {
        //重置
        resetPaint()
        //计算
        val left = (thumbCx - thumbRadius).toInt()
        val top = (rectRadius - thumbRadius).toInt()
        val right = (thumbCx + thumbRadius).toInt()
        val bottom = (rectRadius + thumbRadius).toInt()
        //绘制
        if (thumbBackground is ColorDrawable) {
            paint.color = (thumbBackground as ColorDrawable).color
            canvas.drawCircle(thumbCx, rectRadius, thumbRadius, paint)
        } else {
            thumbBackground.setBounds(left, top, right, bottom)
            thumbBackground.draw(canvas)
        }
    }

    /**
     * 重置画笔
     */
    private fun resetPaint() {
        paint.reset()
        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true
    }

    /**
     * 校验各个属性值的合法性
     */
    private fun checkValues() {
        check(thumbRadius > 0)
        check(thumbRangeMax > 0)
        check(width > height)
        check(percent in 0f..1f)
    }

    /**
     * 坐标是否在滑块范围
     */
    private fun inThumb(x: Float, y: Float): Boolean {
        val left = thumbCx - thumbRadius
        val top = rectRadius - thumbRadius
        val right = thumbCx + thumbRadius
        val bottom = rectRadius + thumbRadius
        return (x in left..right) && (y in top..bottom)
    }

    /**
     * 切换状态
     */
    private fun switch() {
        if (isOn) {
            switchToOff()
        } else {
            switchToOn()
        }
    }

    /**
     * 动画切换开启
     */
    private fun switchToOn(callback: Boolean = true) {
        if (animator?.isRunning == true) return
        if (percent >= 1f) {
            switchStatus(true, callback)
        } else {
            animator = ValueAnimator.ofFloat(percent, 1f)
            animator?.apply {
                duration =
                    max(MAX_DURATION_ANIMATOR * (1f - percent).toLong(), MIN_DURATION_ANIMATOR)
                addUpdateListener {
                    percent = it.animatedValue as Float
                    this@SwitchButton.invalidate()
                }
                doOnStart { switchStatus(true, callback) }
                start()
            }
        }
    }

    /**
     * 动画切换关闭
     */
    private fun switchToOff(callback: Boolean = true) {
        if (animator?.isRunning == true) return
        if (percent <= 0f) {
            switchStatus(false, callback)
        } else {
            animator = ValueAnimator.ofFloat(percent, 0f)
            animator?.apply {
                duration =
                    max(MAX_DURATION_ANIMATOR * (percent - 0f).toLong(), MIN_DURATION_ANIMATOR)
                addUpdateListener {
                    percent = it.animatedValue as Float
                    this@SwitchButton.invalidate()
                }
                doOnStart { switchStatus(false, callback) }
                start()
            }
        }
    }

    /**
     * 修改状态
     */
    private fun switchStatus(on: Boolean, callback: Boolean) {
        val isSame = (isOn == on)
        isOn = on
        //回调，相同不回调
        if (callback && !isSame) switchListener?.invoke(isOn)
    }

    /**
     * 开关状态
     */
    fun isOn(): Boolean {
        return isOn
    }

    /**
     * 切换开关状态
     */
    fun switch(on: Boolean) {
        if (isOn == on) return
        //切换，主动切换不回调监听
        if (on) {
            switchToOn(false)
        } else {
            switchToOff(false)
        }
    }

    /**
     * 设置开关切换监听
     */
    fun setOnSwitchListener(listener: (Boolean) -> Unit) {
        switchListener = listener
        invalidate()
    }

}