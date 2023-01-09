package com.example.module_common.utils.html

import android.content.Context
import android.graphics.Paint
import android.graphics.Rect
import android.net.Uri
import android.text.Editable
import android.text.style.DynamicDrawableSpan
import android.text.style.ImageSpan
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import org.xml.sax.Attributes
import java.lang.ref.WeakReference
import kotlin.math.ceil

/**
 * TODO 图片标签,暂时直接使用CommonImageGetter处理
 */
class CustomImgTag(private val context: Context, textView: TextView) : BaseHtmlTag() {

    override val TAG_NAME: String
        get() = TAG_IMG

    companion object {
        const val SRC = "src"
        const val ALIGN = "align"

        const val ALIGN_CENTER = "center"
    }

    private var mTextView: WeakReference<TextView>? = null

    init {
        mTextView = WeakReference(textView)
    }

    override fun startHandleTagAttributes(originEditable: Editable?, atts: Attributes?): Boolean {
        val src = atts?.getValue("", SRC)
        val gravity = atts?.getValue("", ALIGN) ?: ALIGN_CENTER
        var isFind = false
        if (!src.isNullOrEmpty()) {
            setSpanStartIndex(originEditable, Image(src, gravity))
            isFind = true
        }
        return isFind
    }

    override fun endHandleTagAttributes(index: Int, originEditable: Editable) {
        val imageSpan = getLastSpanFromEdit(index, originEditable, Image::class.java)
        if (imageSpan != null) {
            //获取本地资源图片
//            var placeHolder = ImageLoader.getInstance(context).getDrawable(imageSpan.src)
//            if (placeHolder != null){
//                placeHolder.bounds = getImageRect(imageSpan.src)
//            } else {
            //TODO
            val placeHolder = ResourcesCompat.getDrawable(context.resources, androidx.appcompat.R.drawable.abc_ab_share_pack_mtrl_alpha, context.theme)
            placeHolder?.bounds = getImageRect(imageSpan.src)
//            DPImageLoader.with(context).load(imageSpan.src)
//                    .listener(object : DrawableListener {
//                        override fun onLoadFailed() {
//
//                        }
//
//                        override fun onLoadComplete(t: Drawable?, imageUrl: String?) {
//                            if (t == null) return
//                            t.bounds = getImageRect(imageUrl?:"")
//                            mTextView?.get()?.apply{
//                                post {
//                                    //更新图片
//                                    val spBuilder = SpannableStringBuilder(text.toSpanned())
//                                    val oldSpan = spBuilder.getSpans(0, text.length, ImageSpan::class.java).find { it.source == imageUrl } ?: return@post
//                                    val star = spBuilder.getSpanStart(oldSpan)
//                                    val end = spBuilder.getSpanEnd(oldSpan)
//                                    val flag = spBuilder.getSpanFlags(oldSpan)
//                                    spBuilder.removeSpan(oldSpan)
//                                    spBuilder.setSpan(ImageSpan(t, imageUrl?:""), star, end, flag)
//                                    text = spBuilder
//                                }
//                            }
//                        }
//
//                    })
//                    .request()
//            }
            if (imageSpan.align == ALIGN_CENTER) {
                tagSpans(originEditable, imageSpan, CenterImageSpan(placeHolder!!))
            } else {
                tagSpans(originEditable, imageSpan, ImageSpan(placeHolder!!, imageSpan.src, DynamicDrawableSpan.ALIGN_BASELINE))
            }
        }
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