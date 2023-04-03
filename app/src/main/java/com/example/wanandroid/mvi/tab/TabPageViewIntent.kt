package com.example.wanandroid.mvi.tab

import com.example.wanandroid.base.mvi.ViewIntent

/**
 * @author: Yang
 * @date: 2023/3/17
 * @description: 通用tab页面ViewIntent
 */
sealed class TabPageViewIntent: ViewIntent() {
    //初始化tab
    object InitTab : TabPageViewIntent()
}