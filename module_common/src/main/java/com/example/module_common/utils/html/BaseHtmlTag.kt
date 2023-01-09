package com.example.module_common.utils.html

import android.graphics.Color
import android.text.Editable
import android.text.Spanned
import android.text.TextUtils
import org.xml.sax.Attributes
import java.util.*

abstract class BaseHtmlTag {

    protected val spanStartIndexStack = Stack<Int>()
    protected val stashSpanStyleStack = Stack<StashedSpanStyle>()

    abstract val TAG_NAME: String

    /**
     * 处理头标签<AAA>
     *
     * @param originEditable
     * @param atts
    </AAA> */
    open fun startHandleTag(originEditable: Editable?, atts: Attributes?) {
        var isFind = startHandleTagAttributes(originEditable, atts)
        if (isFind) {
            spanStartIndexStack.push(originEditable!!.length)
        }
    }

    /**
     * 处理尾标签
     *
     * @param originEditable
     */
    open fun endHandleTag(originEditable: Editable?) {
        if (originEditable == null) return
        var index = 0
        if (!spanStartIndexStack.empty()) {
            index = spanStartIndexStack.pop()
            if (index == null) {
                index = 0
            }
        }
        endHandleTagAttributes(index, originEditable)
    }

    /**
     * 标签结束处理
     *
     * @param originEditable
     */
    open fun finishHandleTag(originEditable: Editable?) {
        while (!stashSpanStyleStack.empty()) {
            val stashedSpanStyle = stashSpanStyleStack.pop() ?: continue
            originEditable!!.setSpan(stashedSpanStyle.span, stashedSpanStyle.start, stashedSpanStyle.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

    /**
     * 标签属性处理，设置Span标记
     */
    abstract fun startHandleTagAttributes(originEditable: Editable?, atts: Attributes?) : Boolean

    /**
     * 标签属性显示处理，设置Span
     */
    abstract fun endHandleTagAttributes(index: Int, originEditable: Editable)

    fun getFontSize(fontSize: String): Int {
        var fontSize = fontSize
        if (TextUtils.isEmpty(fontSize)) {
            return -1
        }
        fontSize = fontSize.toLowerCase()
        //单位px
        if (fontSize.endsWith(UNIT_PX) && TextUtils.isDigitsOnly(fontSize.substring(0, fontSize.indexOf(
                UNIT_PX
            )))) {
            return fontSize.substring(0, fontSize.indexOf(UNIT_PX)).toFloat().toInt()
        }
        //无单位
        return if (TextUtils.isDigitsOnly(fontSize)) {
            //TODO 单位转换
//            ConvertUtils.sp2px(fontSize.toFloat())
            fontSize.toInt()
        } else -1
    }

    /**
     * 重写Color.parseColor 不希望出现Exception
     *
     * @param colorString
     * @return
     */
    fun parseColor(colorString: String?): Int {
        return if (TextUtils.isEmpty(colorString)) {
            -1
        } else try {
            Color.parseColor(colorString)
        } catch (ex: IllegalArgumentException) {
            -1
        }
    }

    /**
     * 标记span样式的起点位置
     *
     * @param editable
     * @param mark
     */
    protected fun setSpanStartIndex(editable: Editable?, mark: Any) {
        // startHandle阶段 setSpan只做标记位置作用不实现具体效果
        val length = editable!!.length
        editable.setSpan(mark, length, length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
    }

    /**
     * 根据起点终点保存span样式
     *
     * @param editable
     * @param mark
     * @param spans
     */
    protected fun tagSpans(editable: Editable?, mark: Any, vararg spans: Any) {
        val start = editable!!.getSpanStart(mark)
        editable.removeSpan(mark)
        val end = editable.length
        if (start != end) {
            for (span in spans) {
                stashSpanStyleStack.push(StashedSpanStyle(span, start, end))
            }
        }
    }

    protected class StashedSpanStyle(var span: Any, var start: Int, var end: Int)

    protected class Bold
    protected class FontSize(var fontSize: Int)
    protected class Router(val route: String)
    protected class Image(val src: String, val align: String)

    protected class BackgroundColor(var backgroundColor: Int)

    protected class ForegroundColor(var foregroundColor: Int)

    companion object {
        private const val UNIT_PX = "px"
        private const val UNIT_SP = "sp"

        const val TAG_FONT = "font"
        const val TAG_SPAN = "span"
        const val TAG_ROUTER = "router"
        const val TAG_A = "a"
        const val TAG_IMG = "img"

        /**
         * 获取editable中已经存在的span集合，获取最新添加的span
         *
         * @param start 匹配查询起点
         * @param editable
         * @param kind
         */
        fun <T> getLastSpanFromEdit(start: Int, editable: Editable, kind: Class<T>): T? {
            val objs = editable.getSpans(start, editable.length, kind)
            return if (objs.isEmpty()) {
                null
            } else {
                objs[objs.size - 1]
            }
        }
    }
}