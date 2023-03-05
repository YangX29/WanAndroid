package com.example.wanandroid.viewmodel.article

import com.example.wanandroid.model.Article
import com.example.wanandroid.viewmodel.list.ListPageViewState
import com.example.wanandroid.viewmodel.list.ListPageViewStatus

/**
 * @author: Yang
 * @date: 2023/3/5
 * @description: 通用文章列表ViewState
 */
data class ArticleListViewState(
    override val status: ListPageViewStatus,
    val articles: MutableList<Article>? = null
) : ListPageViewState(status)