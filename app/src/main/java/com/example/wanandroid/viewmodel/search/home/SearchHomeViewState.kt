package com.example.wanandroid.viewmodel.search.home

import com.example.wanandroid.base.mvi.ViewState
import com.example.wanandroid.model.HotKey

/**
 * @author: Yang
 * @date: 2023/3/22
 * @description: 搜索页ViewState
 */
sealed class SearchHomeViewState : ViewState() {
    //初始化成功
    data class InitSuccess(val hotKeys: MutableList<HotKey>, val histories: MutableList<String>) :
        SearchHomeViewState()

    //初始化失败
    object InitFailed : SearchHomeViewState()
}