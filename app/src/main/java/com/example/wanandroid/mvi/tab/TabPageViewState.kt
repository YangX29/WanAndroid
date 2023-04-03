package com.example.wanandroid.mvi.tab

import com.example.wanandroid.base.mvi.ViewState
import com.example.wanandroid.model.Category

/**
 * @author: Yang
 * @date: 2023/3/17
 * @description: 通用tab页面ViewState
 */
data class TabPageViewState(
    val status: TabPageViewStatus,
    val tabs: MutableList<Category>? = null
) : ViewState()

sealed class TabPageViewStatus {
    //初始化成功
    object InitFinish : TabPageViewStatus()

    //初始化失败
    object InitFailed : TabPageViewStatus()
}