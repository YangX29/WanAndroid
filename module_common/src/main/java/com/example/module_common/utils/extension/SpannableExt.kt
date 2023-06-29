package com.example.module_common.utils.extension

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.AbsoluteSizeSpan
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.example.module_common.utils.html.CenterImageSpan
import com.example.module_common.utils.span.CommonClickableSpan

/**
 * 为TextView设置前景色span
 * @param startIndex 起始索引
 * @param endIndex 结束索引
 * @param colorRes 前景色
 */
fun TextView.foregroundSpan(startIndex: Int, endIndex: Int, @ColorRes colorRes: Int) {
    setTextSpan {
        //设置前景色
        setSpan(
            ForegroundColorSpan(context.resources.getColor(colorRes)),
            startIndex,
            endIndex,
            Spanned.SPAN_INCLUSIVE_INCLUSIVE
        )
    }
}

/**
 * 为TextView设置背景色span
 * @param startIndex 起始索引
 * @param endIndex 结束索引
 * @param colorRes 背景色
 */
fun TextView.backgroundSpan(startIndex: Int, endIndex: Int, @ColorRes colorRes: Int) {
    setTextSpan {
        //设置背景色
        setSpan(
            BackgroundColorSpan(context.resources.getColor(colorRes)),
            startIndex,
            endIndex,
            Spanned.SPAN_INCLUSIVE_INCLUSIVE
        )
    }
}

/**
 * 为TextView设置下划线span
 * @param startIndex 起始索引
 * @param endIndex 结束索引
 */
fun TextView.underlineSpan(startIndex: Int, endIndex: Int) {
    setTextSpan {
        //设置下划线
        setSpan(
            UnderlineSpan(),
            startIndex,
            endIndex,
            Spanned.SPAN_INCLUSIVE_INCLUSIVE
        )
    }
}

/**
 * 为TextView设置删除线span
 * @param startIndex 起始索引
 * @param endIndex 结束索引
 */
fun TextView.strikethroughSpan(startIndex: Int, endIndex: Int) {
    setTextSpan {
        //设置删除线
        setSpan(
            StrikethroughSpan(),
            startIndex,
            endIndex,
            Spanned.SPAN_INCLUSIVE_INCLUSIVE
        )
    }
}

/**
 * 为TextView设置字体样式Span
 * @param startIndex 起始索引
 * @param endIndex 结束索引
 * @param style 字体样式，[Typeface.NORMAL],[Typeface.BOLD],[Typeface.ITALIC],[Typeface.BOLD_ITALIC]
 */
fun TextView.styleSpan(startIndex: Int, endIndex: Int, style: Int) {
    setTextSpan {
        //设置字体样式
        setSpan(
            StyleSpan(style),
            startIndex,
            endIndex,
            Spanned.SPAN_INCLUSIVE_INCLUSIVE
        )
    }
}

/**
 * 为TextView设置字体大小Span
 * @param startIndex 起始索引
 * @param endIndex 结束索引
 * @param size 字体大小，单位px
 */
fun TextView.fontSizeSpan(startIndex: Int, endIndex: Int, size: Int) {
    setTextSpan {
        //设置字体大小
        setSpan(
            AbsoluteSizeSpan(size),
            startIndex,
            endIndex,
            Spanned.SPAN_INCLUSIVE_INCLUSIVE
        )
    }
}

/**
 * 为TextView设置图标span，为将对应位置的文字替换为图标
 * @param startIndex 起始索引
 * @param endIndex 结束索引
 * @param drawableRes drawable资源id
 * @param width 图标宽度
 * @param height 图标高度
 */
@SuppressLint("UseCompatLoadingForDrawables")
fun TextView.imageSpan(
    startIndex: Int,
    endIndex: Int,
    @DrawableRes drawableRes: Int,
    width: Int,
    height: Int
) {
    val drawable =
        context.resources.getDrawable(drawableRes).apply {
            setBounds(0, 0, width, height)
        }
    //点击事件
    imageSpan(startIndex, endIndex, drawable)
}

/**
 * 为TextView设置图标span，为将对应位置的文字替换为图标
 * @param startIndex 起始索引
 * @param endIndex 结束索引
 * @param drawable 图片drawable
 */
fun TextView.imageSpan(startIndex: Int, endIndex: Int, drawable: Drawable) {
    setTextSpan {
        //点击事件
        setSpan(
            CenterImageSpan(drawable),
            startIndex,
            endIndex,
            Spanned.SPAN_INCLUSIVE_INCLUSIVE
        )
    }
}

/**
 * 为TextView设置图标span，直接在对应位置插入
 * @param insertIndex 插入位置
 * @param drawableRes drawable资源id
 * @param width 图标宽度
 * @param height 图标高度
 */
@SuppressLint("UseCompatLoadingForDrawables")
fun TextView.imageSpan(
    insertIndex: Int,
    @DrawableRes drawableRes: Int,
    width: Int,
    height: Int
) {
    val drawable =
        context.resources.getDrawable(drawableRes).apply {
            setBounds(0, 0, width, height)
        }
    //点击事件
    imageSpan(insertIndex, drawable)
}


/**
 * 为TextView设置图标span，直接在对应位置插入
 * @param insertIndex 插入位置
 * @param drawable 图片drawable
 */
fun TextView.imageSpan(insertIndex: Int, drawable: Drawable) {
    setTextSpan {
        //插入一个空格充当占位符
        insert(insertIndex, " ")
        //点击事件
        setSpan(
            CenterImageSpan(drawable),
            insertIndex,
            insertIndex + 1,
            Spanned.SPAN_INCLUSIVE_INCLUSIVE
        )
    }
}

/**
 * 为TextView设置点击事件span
 * @param startIndex 起始索引
 * @param endIndex 结束索引
 * @param colorRes 可点击文字的颜色
 * @param action 点击事件
 */
fun TextView.clickSpan(
    startIndex: Int,
    endIndex: Int,
    @ColorRes colorRes: Int,
    action: () -> Unit
) {
    setTextSpan {
        //点击事件
        setSpan(
            CommonClickableSpan(context.resources.getColor(colorRes), action),
            startIndex,
            endIndex,
            Spanned.SPAN_INCLUSIVE_INCLUSIVE
        )
        //可能会与TextView本身的点击事件冲突
        movementMethod = LinkMovementMethod.getInstance()
        highlightColor = Color.TRANSPARENT
    }
}

/**
 * 设置富文本
 * @param handle 富文本处理
 */
internal fun TextView.setTextSpan(
    handle: SpannableStringBuilder.() -> Unit
) {
    if (text.isNullOrEmpty()) return
    //处理富文本
    val spanned = SpannableStringBuilder(text)
    spanned.handle()
    //设置文本
    text = spanned
}