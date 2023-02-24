package com.example.wanandroid.viewmodel.home

import com.example.wanandroid.base.mvi.ViewIntent

/**
 * @author: Yang
 * @date: 2023/2/23
 * @description: 首页ViewIntent
 */
sealed class HomeViewIntent : ViewIntent() {

    //刷新
    data class Refresh(val tab: Int) : HomeViewIntent()

    //刷新结束
    object RefreshFinish : HomeViewIntent()

}