package com.example.module_common.utils.html

import android.content.Context
import android.text.Editable
import org.xml.sax.Attributes

/**
 * router标签处理
 */
class CustomRouterTag(private val context: Context) : BaseHtmlTag() {

    companion object {
        const val ROUTE = "route"
    }

    override val TAG_NAME: String
        get() = TAG_ROUTER

    override fun startHandleTagAttributes(originEditable: Editable?, atts: Attributes?): Boolean {
        val route = atts?.getValue("", ROUTE)
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