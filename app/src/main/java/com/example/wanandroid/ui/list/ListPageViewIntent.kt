package com.example.wanandroid.ui.list

import com.example.wanandroid.base.mvi.ViewIntent

/**
 * @author: Yang
 * @date: 2023/2/25
 * @description: 通用列表的ViewIntent
 */
sealed class ListPageViewIntent : ViewIntent() {
    //刷新
    object Refresh : ListPageViewIntent()

    //加载更多
    object LoadMore : ListPageViewIntent()

    //点击item
    data class ItemClick(val position: Int) : ListPageViewIntent()
}