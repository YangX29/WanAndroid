package com.example.wanandroid.viewmodel.search

import com.example.wanandroid.base.mvi.ViewIntent

/**
 * @author: Yang
 * @date: 2023/3/23
 * @description: 搜索页面ViewIntent
 */
sealed class SearchViewIntent : ViewIntent() {
    //搜索
    data class Search(val key: String) : SearchViewIntent()
    //在搜索列表修改key
    data class UpdateSearchKey(val key: String): SearchViewIntent()
}