package com.example.wanandroid.viewmodel.list

import com.example.wanandroid.base.mvi.ViewIntent

/**
 * @author: Yang
 * @date: 2023/2/25
 * @description: 通用列表的ViewIntent
 */
sealed class ListPageViewIntent : ViewIntent() {
    //刷新
    data class Refresh(val isInit: Boolean) : ListPageViewIntent()

    //加载更多
    object LoadMore : ListPageViewIntent()

    //点击item
    data class ItemClick(val position: Int) : ListPageViewIntent()
}