package com.example.wanandroid.view.widget

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import androidx.annotation.ColorInt


/**
 * 创建一个圆形Drawable
 */
fun ovalDrawable(
    @ColorInt normalColor: Int,
    @ColorInt pressedColor: Int = normalColor,
    @ColorInt selectedColor: Int = normalColor
): Drawable {
    return createDrawable({
        shape = GradientDrawable.OVAL
        color = ColorStateList.valueOf(normalColor)
    }, {
        color = ColorStateList.valueOf(pressedColor)
    }, {
        color = ColorStateList.valueOf(selectedColor)
    })
}

/**
 * 边框Drawable
 */
fun borderDrawable(
    radius: Float,
    width: Int,
    @ColorInt normalColor: Int,
    @ColorInt pressedColor: Int = normalColor,
    @ColorInt selectedColor: Int = normalColor
): Drawable {
    return createDrawable({
        shape = GradientDrawable.RECTANGLE
        cornerRadius = radius
        setStroke(width, normalColor)
    }, {
        setStroke(width, pressedColor)
    }, {
        setStroke(width, selectedColor)
    })
}

/**
 * 创建一个圆角矩形Drawable
 */
fun roundDrawable(
    radius: Float,
    @ColorInt normalColor: Int,
    @ColorInt pressedColor: Int = normalColor,
    @ColorInt selectedColor: Int = normalColor
): Drawable {
    return createDrawable({
        shape = GradientDrawable.RECTANGLE
        cornerRadius = radius
        color = ColorStateList.valueOf(normalColor)
    }, {
        color = ColorStateList.valueOf(pressedColor)
    }, {
        color = ColorStateList.valueOf(selectedColor)
    })
}

fun topRoundDrawable(
    topLeftRadius: Float,
    topRightRadius: Float,
    @ColorInt normalColor: Int,
): Drawable {
    return createDrawable({
        shape = GradientDrawable.RECTANGLE
        cornerRadii = floatArrayOf(
            topLeftRadius,
            topLeftRadius,
            topRightRadius,
            topRightRadius,
            0f,
            0f,
            0f,
            0f
        )
        color = ColorStateList.valueOf(normalColor)
    })
}

/**
 * 创建一个StateListDrawable实例;
 * [pressed]和[selected]状态的drawable，都是在[normal]的基础上进行修改的
 */
fun createDrawable(
    normal: GradientDrawable.() -> Unit,
    pressed: (GradientDrawable.() -> Unit)? = null,
    selected: (GradientDrawable.() -> Unit)? = null,
): Drawable {
    return StateListDrawable().apply {
        //点击状态
        pressed?.let {
            addState(intArrayOf(android.R.attr.state_pressed), GradientDrawable().apply {
                normal.invoke(this)
                pressed.invoke(this)
            })
        }
        //选中状态
        selected?.let {
            addState(intArrayOf(android.R.attr.state_selected), GradientDrawable().apply {
                normal.invoke(this)
                selected.invoke(this)
            })
        }
        //正常状态
        addState(intArrayOf(), GradientDrawable().apply {
            normal.invoke(this)
        })
    }
}