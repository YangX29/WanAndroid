package com.example.wanandroid.mvi.search.home

import com.example.wanandroid.base.mvi.ViewIntent

/**
 * @author: Yang
 * @date: 2023/3/22
 * @description: 搜索页ViewIntent
 */
sealed class SearchHomeViewIntent : ViewIntent() {
    //初始化热词和历史记录数据
    object InitData : SearchHomeViewIntent()

    //清除历史记录
    object ClearHistory : SearchHomeViewIntent()
}