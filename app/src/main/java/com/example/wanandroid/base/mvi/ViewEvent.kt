package com.example.wanandroid.base.mvi

import android.os.Bundle
import androidx.annotation.StringRes

/**
 * @author: Yang
 * @date: 2023/2/2
 * @description: MVI架构，界面一次性事件
 */
sealed class ViewEvent {
    /**
     * 显示Toast
     */
    data class Toast(@StringRes val res: Int) : ViewEvent()

    /**
     * 页面跳转
     */
    data class JumpToPage(val route: String, val bundle: Bundle? = null) : ViewEvent()

    /**
     * 返回上一页
     */
    object Back : ViewEvent()

    /**
     * 跳转到网页
     */
    data class JumpToWeb(val url: String) : ViewEvent()

    /**
     * loading
     */
    data class Loading(val show: Boolean) : ViewEvent()
}
