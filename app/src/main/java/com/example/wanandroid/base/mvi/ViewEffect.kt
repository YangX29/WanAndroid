package com.example.wanandroid.base.mvi

/**
 * @author: Yang
 * @date: 2023/2/2
 * @description: MVI架构，界面通用行为
 */
sealed class ViewEffect {
    /**
     * 显示Toast
     */
    data class Toast(val msg: String): ViewEffect()

    /**
     * 页面跳转
     */
    data class JumpToPage(val route: String): ViewEffect()

    /**
     * 返回上一页
     */
    object Back : ViewEffect()
}
