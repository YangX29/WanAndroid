package com.example.wanandroid.viewmodel.home.sub

import com.example.wanandroid.base.mvi.ViewIntent

/**
 * @author: Yang
 * @date: 2023/2/23
 * @description: 首页子tab的ViewIntent
 */
sealed class HomeSubViewIntent : ViewIntent() {
    //刷新
    object Refresh : HomeSubViewIntent()
}