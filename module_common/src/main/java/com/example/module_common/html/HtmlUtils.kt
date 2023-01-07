package com.example.module_common.html

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.text.Html
import android.text.SpannedString
import android.text.style.ClickableSpan
import android.view.MotionEvent
import android.widget.TextView

object HtmlUtils {

    @SuppressLint("ClickableViewAccessibility")
    fun fromHtml(context: Context, textView: TextView, html: String) : CharSequence {
        if (html.isEmpty()) return html
        val result = html.replace("\n", "<br/>")
        try {
            val sp = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                val tagHandler = CustomHtmlTagHandler()
                tagHandler.registerTag(BaseHtmlTag.TAG_SPAN, CustomSpanTag())
                tagHandler.registerTag(BaseHtmlTag.TAG_FONT, CustomFontTag(context))
                tagHandler.registerTag(BaseHtmlTag.TAG_ROUTER, CustomRouterTag(context))
                tagHandler.registerTag(BaseHtmlTag.TAG_A, CustomLinkTag(context))
                tagHandler.registerTag(BaseHtmlTag.TAG_IMG, CustomImgTag(context, textView))
                //表情
                Html.fromHtml("<custom>${result}</custom>", Html.FROM_HTML_MODE_LEGACY, CommonImageGetter(context, textView), tagHandler)
            } else {
                Html.fromHtml(result)
            }
            //存在点击点击事件
            if (!sp.getSpans(0, sp.length, CustomClickSpan::class.java).isNullOrEmpty()){
//                    textView.movementMethod = LinkMovementMethod.getInstance()
                textView.highlightColor = Color.TRANSPARENT
                //设置onTouchListener，解决textview的点击事件和clickSpan冲突问题
                textView.setOnTouchListener { v, event ->
                    if (v is TextView && v.text is SpannedString) {//存在富文本
                        //点击坐标
                        var x = event.x
                        var y = event.y

                        x -= v.totalPaddingLeft
                        y -= v.totalPaddingTop

                        x += v.getScrollX()
                        y += v.getScrollY()

                        val line = v.layout.getLineForVertical(y.toInt())
                        val off = v.layout.getOffsetForHorizontal(line, x)
                        //获取点击位置的ClickableSpan
                        val links = (v.text as SpannedString).getSpans(off, off, ClickableSpan::class.java)
                        if (links.isNotEmpty()) {//可点击消费事件
                            if (event.action == MotionEvent.ACTION_UP) {
                                links[0].onClick(v)
                            }
                            return@setOnTouchListener true
                        } else {//不可点击，传递
                            return@setOnTouchListener v.onTouchEvent(event)
                        }
                    }
                    return@setOnTouchListener v.onTouchEvent(event)
                }
            }
            //设置sp
            textView.text = sp
            return sp
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return html
    }

}