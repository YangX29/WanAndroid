package com.example.wanandroid.viewmodel.home

import com.example.wanandroid.base.mvi.ViewState

/**
 * @author: Yang
 * @date: 2023/2/23
 * @description: 首页ViewState
 */
sealed class HomeViewState : ViewState() {
    //刷新结束
    object RefreshFinish : HomeViewState()

    //开始刷新
    object StartRefresh : HomeViewState()
}