package com.example.wanandroid.viewmodel.search

import com.example.wanandroid.base.mvi.ViewState


/**
 * @author: Yang
 * @date: 2023/3/23
 * @description: 搜索页ViewState
 */
sealed class SearchViewState : ViewState() {
    //搜索
    data class SearchKey(val key: String) : SearchViewState()

    //更新历史记录
    data class UpdateHistory(val key: String): SearchViewState()

    //刷新搜索列表
    data class RefreshSearchList(val key: String) : SearchViewState()
}