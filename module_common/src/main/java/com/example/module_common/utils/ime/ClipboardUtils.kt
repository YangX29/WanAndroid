package com.example.module_common.utils.ime

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.PersistableBundle
import com.example.module_common.utils.app.ContextUtils

/**
 * @author: Yang
 * @date: 2023/6/13
 * @description: 剪切板工具类。Android 10以及以上版本限制了对剪贴板数据的访问，
 * 除非您的应用是默认输入法 (IME) 或是目前处于焦点的应用，否则它无法访问 Android 10 或更高版本平台上的剪贴板数据。
 * 可以通过[onWindowFocusChanged]中获取剪切板内容
 * TODO 存在多个ClipData.Item的处理
 */
object ClipboardUtils {

    //默认label
    private const val TEXT_LABEL = "text"
    private const val HTML_LABEL = "html"
    private const val URI_LABEL = "uri"
    private const val INTENT_LABEL = "intent"

    //剪切板manager
    private val manager by lazy { getClipboardManager() }

    enum class TextType {
        TEXT, HTML_TEXT, STYLED_TEXT
    }

    /**
     * 复制文本到剪切板
     */
    fun copyText(
        text: CharSequence,
        htmlText: String? = null,
        isSensitive: Boolean = false,
        label: String? = null
    ) {
        val data = if (htmlText == null) {
            ClipData.newPlainText(label ?: TEXT_LABEL, text)
        } else {
            ClipData.newHtmlText(label ?: HTML_LABEL, text, htmlText)
        }
        //是否为敏感数据
        if (isSensitive) {
            data.description.run {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    extras = PersistableBundle().apply {
                        putBoolean("android.content.extra.IS_SENSITIVE", true)
                    }
                }
            }
        }
        manager.setPrimaryClip(data)
    }

    /**
     * 复制uri到剪切板
     */
    fun copyUri(
        uri: Uri,
        isSensitive: Boolean = false,
        label: String = URI_LABEL
    ) {
        val data = ClipData.newUri(ContextUtils.getContext().contentResolver, label, uri)
        //是否为敏感数据
        if (isSensitive) {
            data.description.run {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    extras = PersistableBundle().apply {
                        putBoolean("android.content.extra.IS_SENSITIVE", true)
                    }
                }
            }
        }
        manager.setPrimaryClip(data)
    }

    /**
     * 复制intent到剪切板
     */
    fun copyIntent(
        intent: Intent,
        isSensitive: Boolean = false,
        label: String = INTENT_LABEL
    ) {
        val data = ClipData.newIntent(label, intent)
        //是否为敏感数据
        if (isSensitive) {
            data.description.run {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    extras = PersistableBundle().apply {
                        putBoolean("android.content.extra.IS_SENSITIVE", true)
                    }
                }
            }
        }
        manager.setPrimaryClip(data)
    }

    /**
     * 获取剪切板text
     */
    fun getText(): CharSequence? {
        return manager.primaryClip?.run {
            if (itemCount > 0) {
                getItemAt(0)?.text
            } else {
                null
            }
        }
    }

    /**
     * 获取剪切板html格式文本
     */
    fun getHtmlText(): CharSequence? {
        return manager.primaryClip?.run {
            if (itemCount > 0) {
                getItemAt(0)?.htmlText
            } else {
                null
            }
        }
    }

    /**
     * 获取剪切板uri
     */
    fun getUri(): Uri? {
        return manager.primaryClip?.run {
            if (itemCount > 0) {
                getItemAt(0)?.uri
            } else {
                null
            }
        }
    }

    /**
     * 获取剪切板intent
     */
    fun getIntent(): Intent? {
        return manager.primaryClip?.run {
            if (itemCount > 0) {
                getItemAt(0)?.intent
            } else {
                null
            }
        }
    }

    /**
     * 获取剪切板内容，并强制转换为文本内容
     *
     */
    fun getTextCoerced(type: TextType = TextType.TEXT): CharSequence? {
        if (manager.primaryClip == null || manager.primaryClip!!.itemCount <= 0) return null
        return manager.primaryClip?.getItemAt(0)?.run {
            when (type) {
                TextType.TEXT -> coerceToText(ContextUtils.getContext())
                TextType.HTML_TEXT -> coerceToHtmlText(ContextUtils.getContext())
                TextType.STYLED_TEXT -> coerceToStyledText(ContextUtils.getContext())
            }
        }
    }

    /**
     * 获取剪切板数据label
     */
    fun getLabel(): CharSequence? {
        return manager.primaryClipDescription?.label
    }

    /**
     * 清空剪切板
     */
    fun clear() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            manager.clearPrimaryClip()
        } else {
            manager.setPrimaryClip(ClipData.newPlainText(null, ""))
        }
    }

    /**
     * 判断剪切板是否存在数据
     */
    fun hasPrimaryClip(): Boolean {
        return manager.hasPrimaryClip()
    }

    /**
     * 添加剪切板监听
     */
    fun addChangeListener(listener: ClipboardManager.OnPrimaryClipChangedListener) {
        manager.addPrimaryClipChangedListener(listener)
    }

    /**
     * 移除剪切板监听
     */
    fun removeChangeListener(listener: ClipboardManager.OnPrimaryClipChangedListener) {
        manager.removePrimaryClipChangedListener(listener)
    }

    /**
     * 获取系统ClipboardManager
     */
    private fun getClipboardManager(): ClipboardManager {
        return ContextUtils.getContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    }

}