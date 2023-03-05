package com.example.wanandroid.viewmodel.home.square

import com.example.wanandroid.model.Article
import com.example.wanandroid.ui.list.ListPageViewState
import com.example.wanandroid.ui.list.ListPageViewStatus

/**
 * @author: Yang
 * @date: 2023/3/5
 * @description: 广场页ViewState
 */
data class SquareViewState(
    override val status: ListPageViewStatus,
    val articles: MutableList<Article>? = null
) : ListPageViewState(status)