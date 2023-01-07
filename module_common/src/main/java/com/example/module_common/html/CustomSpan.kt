package com.example.module_common.html

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.util.Patterns
import android.view.View

/**
 * 文字描边span
 */
class CustomFontBoldSpan : StyleSpan(Typeface.BOLD) {

    override fun updateDrawState(tp: TextPaint) {
        tp.strokeWidth = 1.0f
        tp.style = Paint.Style.FILL_AND_STROKE
    }
}

/**
 * 自定义可点击span
 */
class CustomClickSpan(val context: Context, val uri: String) : ClickableSpan() {

    override fun onClick(widget: View) {
        handleJumpUri(context, uri)
    }

    override fun updateDrawState(ds: TextPaint) {
        ds.bgColor = Color.RED
    }

    /**
     * 处理跳转路由
     * 1、路由
     * 2、链接
     * 3、邮箱
     */
    private fun handleJumpUri(context: Context, uri: String) {
        when {
            Patterns.EMAIL_ADDRESS.matcher(uri).matches()-> {//邮箱
                val intent = Intent(Intent.ACTION_SEND)
                val recipients = arrayOf(uri)
                intent.putExtra(Intent.EXTRA_EMAIL, recipients)
                intent.type = "text/csv"
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(Intent.createChooser(intent, "EMail File"))
            }
            else -> {//路由，支持链接
                //TODO 路由跳转
//                Router.routeForServer(context, uri)
            }
        }
    }

}