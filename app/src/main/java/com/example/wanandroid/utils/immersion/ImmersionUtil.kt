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
object ImmersionUtil {

    /**
     * 通过marginTop进行沉浸式适配
     */
    fun adaptByMargin(view: View) {
        view.changeLayoutParams<ConstraintLayout.LayoutParams> {
            it.topMargin = it.topMargin + ImmersionBar.getStatusBarHeight(view.context)
        }
    }

}