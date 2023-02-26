package com.example.wanandroid.ui.list

import com.example.wanandroid.base.mvi.ViewState

/**
 * @author: Yang
 * @date: 2023/2/25
 * @description: 通用列表的ViewState
 */
open class ListPageViewState(
    open val status: ListPageViewStatus
) : ViewState()

sealed class ListPageViewStatus {
    //刷新结束
    object RefreshFinish : ListPageViewStatus()

    //加载更多
    object LoadMoreFinish : ListPageViewStatus()
}