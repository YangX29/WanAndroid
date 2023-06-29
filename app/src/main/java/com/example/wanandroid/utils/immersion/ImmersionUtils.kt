package com.example.wanandroid.utils.immersion

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.module_common.utils.extension.changeLayoutParams
import com.gyf.immersionbar.ImmersionBar

/**
 * @author: Yang
 * @date: 2023/2/6
 * @description: 沉浸式工具类
 */
object ImmersionUtils {

    /**
     * 通过marginTop进行沉浸式适配
     */
    fun adaptByMargin(view: View) {
        view.changeLayoutParams<ConstraintLayout.LayoutParams> {
            topMargin += ImmersionBar.getStatusBarHeight(view.context)
        }
    }

    /**
     * 通过paddingTop进行沉浸式适配
     */
    fun adaptByPaddingTop(view: View) {
        view.apply {
            val topPadding = paddingTop + ImmersionBar.getStatusBarHeight(view.context)
            setPadding(paddingLeft, topPadding, paddingRight, paddingBottom)
        }
    }

}