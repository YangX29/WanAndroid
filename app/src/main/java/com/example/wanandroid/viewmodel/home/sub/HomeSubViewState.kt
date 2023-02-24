package com.example.wanandroid.viewmodel.home.sub

import com.example.wanandroid.base.mvi.ViewState

/**
 * @author: Yang
 * @date: 2023/2/23
 * @description: 首页子tab对应ViewState
 */
sealed class HomeSubViewState : ViewState() {
    //刷新完成
    object RefreshFinish : HomeSubViewState()
}