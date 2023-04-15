package com.example.wanandroid.mvi.list

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
    data class RefreshFinish(val finish: Boolean) : ListPageViewStatus()

    //加载更多
    data class LoadMoreFinish(val finish: Boolean) : ListPageViewStatus()

    //加载失败
    object LoadMoreFailed : ListPageViewStatus()

    //加载失败
    object RefreshFailed : ListPageViewStatus()
}