package com.example.wanandroid.viewmodel.home

/**
 * @author: Yang
 * @date: 2023/2/23
 * @description: 首页三个tab的ViewState
 */
sealed class TabViewState {
    //开始刷新
    data class StartRefresh(val tab: Int) : TabViewState()
}