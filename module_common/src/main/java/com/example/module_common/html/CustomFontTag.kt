package com.example.module_common.html

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import org.xml.sax.Attributes

/**
 * font标签处理
 */
class CustomFontTag(private val context: Context): BaseHtmlTag() {

    companion object {
        const val SIZE = "size"
        const val COLOR = "color"
        const val ROUTE = "route"
    }

    override val TAG_NAME: String
        get() = TAG_FONT

    override fun startHandleTagAttributes(originEditable: Editable?, atts: Attributes?): Boolean {
        val fontSize = atts?.getValue("", SIZE)
        val color = atts?.getValue("", COLOR)
        val route = atts?.getValue("", ROUTE)
        var isFind = false
        if (!fontSize.isNullOrEmpty()) {
            setSpanStartIndex(originEditable, FontSize(getFontSize(fontSize)))
            isFind = true
        }
        if (!color.isNullOrEmpty()) {
            setSpanStartIndex(originEditable, ForegroundColor(Color.parseColor(color)))
            isFind = true
        }
        if (!route.isNullOrEmpty()) {
            setSpanStartIndex(originEditable, Router(route))
            isFind = true
        }
        return isFind
    }

    override fun endHandleTagAttributes(index: Int, originEditable: Editable) {
        val fontSizeSpan = getLastSpanFromEdit(index, originEditable, FontSize::class.java)
        if (fontSizeSpan != null) {
            tagSpans(originEditable, fontSizeSpan, AbsoluteSizeSpan(fontSizeSpan.fontSize))
        }
        val foregroundColorSpan = getLastSpanFromEdit(index, originEditable, ForegroundColor::class.java)
        if (foregroundColorSpan != null) {
            tagSpans(originEditable, foregroundColorSpan, ForegroundColorSpan(foregroundColorSpan.foregroundColor))
        }
        val routerSpan = getLastSpanFromEdit(index, originEditable, Router::class.java)
        if (routerSpan != null) {
            tagSpans(originEditable, routerSpan, CustomClickSpan(context, routerSpan.route))
        }
    }

}