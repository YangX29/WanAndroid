package com.example.wanandroid.utils.view

import android.app.Activity
import android.view.Gravity
import android.widget.FrameLayout
import androidx.core.view.children
import com.example.module_common.utils.extension.removeFromParent
import com.example.module_common.utils.ime.ClipboardUtils
import com.example.wanandroid.view.widget.ClipboardTipView

/**
 * @author: Yang
 * @date: 2023/6/28
 * @description: 剪切板提示工具类
 */
object ClipboardTip {

    /**
     * 检查剪切板
     */
    fun checkClipboard(
        activity: Activity,
        copyText: String,
        copyAction: (String) -> Unit,
        filter: ((CharSequence) -> Boolean) = { true }
    ) {
        //获取剪切板文本
        ClipboardUtils.getTextCoerced()?.let {
            //过滤不必要的剪切板数据
            if (filter.invoke(it)) {
                //创建提示弹窗
                val tipView = ClipboardTipView(activity)
                tipView.setContent(it.toString(), copyText, copyAction)
                //添加到window中
                val contentView =
                    activity.window.decorView.findViewById<FrameLayout>(android.R.id.content)
                val lp = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    gravity = Gravity.BOTTOM or Gravity.START
                }
                //移除原有的tipView
                contentView.children.forEach { child ->
                    if (child is ClipboardTipView) {
                        child.removeFromParent()
                    }
                }
                contentView.addView(tipView, lp)
            }
        }
    }

    /**
     * 隐藏当前剪切板提示View
     */
    fun hideTipView(activity: Activity) {
        val contentView =
            activity.window.decorView.findViewById<FrameLayout>(android.R.id.content)
        //隐藏原有的tipView
        contentView.children.forEach { child ->
            if (child is ClipboardTipView) {
                child.hide()
            }
        }
    }

    /**
     * 移除所有剪切板提示View
     */
    fun removeAllTipView(activity: Activity) {
        val contentView =
            activity.window.decorView.findViewById<FrameLayout>(android.R.id.content)
        //移除原有的tipView
        contentView.children.forEach { child ->
            if (child is ClipboardTipView) {
                child.removeFromParent()
            }
        }
    }

}