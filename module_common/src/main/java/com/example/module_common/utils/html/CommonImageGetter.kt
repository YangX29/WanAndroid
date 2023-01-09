package com.example.module_common.utils.html

import android.content.Context
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.Html
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import java.lang.ref.WeakReference
import kotlin.math.ceil

/**
 * TODO 通用Html图片获取类
 */
class CommonImageGetter(private val context: Context, textView: TextView) : Html.ImageGetter {

    private var mTextView: WeakReference<TextView>? = null

    init {
        mTextView = WeakReference(textView)
    }

    override fun getDrawable(source: String?): Drawable {
        //获取本地资源图片
//        var drawable = ImageLoader.getInstance(context).getDrawable(source)
//        if (drawable != null){
//            drawable.bounds = getImageRect(source ?: "")
//            return drawable
//        }
        //TODO 网络图片
        var drawable = ResourcesCompat.getDrawable(context.resources, androidx.appcompat.R.drawable.abc_ab_share_pack_mtrl_alpha, context.theme)//占位图
        drawable?.bounds = getImageRect(source ?: "")
//        DPImageLoader.with(context)
//                .load(source)
//                .listener(object : DrawableListener {
//                    override fun onLoadFailed() {
//
//                    }
//
//                    override fun onLoadComplete(t: Drawable?, imageUrl: String?) {
//                        if (t == null) return
//                        t.bounds = getImageRect(imageUrl?:"")
//                        mTextView?.get()?.apply{
//                            post {
//                                //更新图片
//                                val spBuilder = SpannableStringBuilder(text.toSpanned())
//                                val imageSpan = spBuilder.getSpans(0, text.length, ImageSpan::class.java).find { it.source == imageUrl } ?: return@post
//                                val star = spBuilder.getSpanStart(imageSpan)
//                                val end = spBuilder.getSpanEnd(imageSpan)
//                                val flag = spBuilder.getSpanFlags(imageSpan)
//                                spBuilder.removeSpan(imageSpan)
//                                spBuilder.setSpan(ImageSpan(t, imageUrl?:""), star, end, flag)
//                                text = spBuilder
//                            }
//                        }
//                    }
//
//                })
//                .request()
        return drawable!!
    }

    /**
     * 获取图片尺寸
     */
    private fun getImageRect(src: String): Rect {
        val uri = Uri.parse(src)
        val height = uri.getQueryParameter("height")?.toIntOrNull() ?: getFontHeight()
        val width = uri.getQueryParameter("width")?.toIntOrNull() ?: getFontHeight()
        return Rect(0, 0, width, height)
    }

    /**
     * 获取文字高度
     */
    private fun getFontHeight(): Int {
        val paint = Paint()
        mTextView?.get()?.apply {
            paint.textSize = textSize
            val fm = paint.fontMetrics
            return ceil(fm.bottom - fm.top.toDouble()).toInt()
        }
        return 0
    }

}