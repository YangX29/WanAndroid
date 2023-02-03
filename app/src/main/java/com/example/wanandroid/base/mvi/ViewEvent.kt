package com.example.wanandroid.base.mvi

/**
 * @author: Yang
 * @date: 2023/2/2
 * @description: MVI架构，界面一次性事件
 */
sealed class ViewEvent {
    /**
     * 显示Toast
     */
    data class Toast(val msg: String): ViewEvent()

    /**
     * 页面跳转
     */
    data class JumpToPage(val route: String): ViewEvent()

    /**
     * 返回上一页
     */
    object Back : ViewEvent()
}
