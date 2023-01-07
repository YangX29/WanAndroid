package com.example.module_common.html

import android.text.Editable
import android.text.TextUtils
import android.text.style.AbsoluteSizeSpan
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import org.xml.sax.Attributes

class CustomSpanTag : BaseHtmlTag() {

    companion object {
        const val FONT_SIZE = "font-size"
        const val COLOR = "color"
        const val BACKGROUND_COLOR = "background-color"
        const val FONT_WEIGHT = "font-weight"
        const val BOLD = "bold"
        const val STYLE = "style"
    }

    override val TAG_NAME: String
        get() = TAG_SPAN

    override fun startHandleTagAttributes(originEditable: Editable?, atts: Attributes?): Boolean {
        val style = atts!!.getValue("", STYLE)
        if (TextUtils.isEmpty(style)) {
            return false
        }
        val textColorStr = getValueFromStyle(style, COLOR)
        val fontSizeStr = getValueFromStyle(style, FONT_SIZE)
        val backgroundColorStr = getValueFromStyle(style, BACKGROUND_COLOR)
        val fontWeight = getValueFromStyle(style, FONT_WEIGHT)
        val fontSize = getFontSize(fontSizeStr!!)
        var isFind = false
        if (fontSize != -1) {
            //接收数据为px单位但因为iOS、Android使用单位不同且无法使用px实现适配，暂时采用15px当做15dp进行处理
            setSpanStartIndex(originEditable, FontSize(fontSize))
            isFind = true
        }
        val textColor = parseColor(textColorStr)
        if (textColor != -1) {
            setSpanStartIndex(originEditable, ForegroundColor(textColor))
            isFind = true
        }
        val backgroundColor = parseColor(backgroundColorStr)
        if (backgroundColor != -1) {
            setSpanStartIndex(originEditable, BackgroundColor(backgroundColor))
            isFind = true
        }
        if (fontWeight != null && fontWeight.toLowerCase() == BOLD) {
            setSpanStartIndex(originEditable, Bold())
            isFind = true
        }
        return isFind
    }

    override fun endHandleTagAttributes(index: Int, originEditable: Editable) {
        val fontSizeSpan = getLastSpanFromEdit(index!!, originEditable!!, FontSize::class.java)
        if (fontSizeSpan != null) {
            tagSpans(originEditable, fontSizeSpan, AbsoluteSizeSpan(fontSizeSpan.fontSize))
        }
        val foregroundColorSpan = getLastSpanFromEdit(index, originEditable, ForegroundColor::class.java)
        if (foregroundColorSpan != null) {
            tagSpans(originEditable, foregroundColorSpan, ForegroundColorSpan(foregroundColorSpan.foregroundColor))
        }
        val backgroundColorSpan = getLastSpanFromEdit(index, originEditable, BackgroundColor::class.java)
        if (backgroundColorSpan != null) {
            tagSpans(originEditable, backgroundColorSpan, BackgroundColorSpan(backgroundColorSpan.backgroundColor))
        }
        val boldSpans = getLastSpanFromEdit(index, originEditable, Bold::class.java)
        if (boldSpans != null) {
            tagSpans(originEditable, boldSpans, CustomFontBoldSpan())
        }
    }

    private fun getValueFromStyle(style: String, matchAttr: String): String? {
        return if (TextUtils.isEmpty(style)) {
            null
        } else getHtmlCssAttrs(style, matchAttr)
    }

    private fun getHtmlCssAttrs(style: String, matchAttr: String): String? {
        if (TextUtils.isEmpty(style)) {
            return null
        }
        val styleAttrs = style.trim { it <= ' ' }.toLowerCase().split(";".toRegex()).toTypedArray()
        for (attr in styleAttrs) {
            val str = attr.trim { it <= ' ' }
            if (str.indexOf(matchAttr) == 0) {
                val split = str.split(":".toRegex()).toTypedArray()
                if (split.size != 2) {
                    continue
                }
                return split[1].trim { it <= ' ' }
            }
        }
        return null
    }

    override fun endHandleTag(originEditable: Editable?) {
        var index: Int? = 0
        if (!spanStartIndexStack.empty()) {
            index = spanStartIndexStack.pop()
            if (index == null) {
                index = 0
            }
        }

    }

}