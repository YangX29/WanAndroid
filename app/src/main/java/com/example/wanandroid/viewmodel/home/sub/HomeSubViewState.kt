package com.example.wanandroid.viewmodel.home.sub

import com.example.wanandroid.model.Article
import com.example.wanandroid.model.Banner
import com.example.wanandroid.ui.list.ListPageViewState
import com.example.wanandroid.ui.list.ListPageViewStatus

/**
 * @author: Yang
 * @date: 2023/2/23
 * @description: 首页子tab对应ViewState
 */
data class HomeSubViewState(
    override val status: ListPageViewStatus,
    val banners: MutableList<Banner>? = null,
    val articles: MutableList<Article>? = null
) : ListPageViewState(status)