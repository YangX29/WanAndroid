package com.example.module_common.html

import android.content.Context
import android.text.Editable
import org.xml.sax.Attributes

/**
 * 本地链接标签用于<a>
 */
class CustomLinkTag(private val context: Context) : BaseHtmlTag() {

    override val TAG_NAME: String
        get() = TAG_A

    companion object {
        const val HREF = "href"
    }

    override fun startHandleTagAttributes(originEditable: Editable?, atts: Attributes?): Boolean {
        val route = atts?.getValue("", HREF)
        var isFind = false
        if (!route.isNullOrEmpty()) {
            setSpanStartIndex(originEditable, Router(route))
            isFind = true
        }
        return isFind
    }

    override fun endHandleTagAttributes(index: Int, originEditable: Editable) {
        val routerSpan = getLastSpanFromEdit(index, originEditable, Router::class.java)
        if (routerSpan != null) {
            tagSpans(originEditable, routerSpan, CustomClickSpan(context, routerSpan.route))
        }
    }

}