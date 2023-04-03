package com.example.wanandroid.mvi.guide.web

import com.example.wanandroid.model.WebCategory
import com.example.wanandroid.mvi.list.ListPageViewState
import com.example.wanandroid.mvi.list.ListPageViewStatus

/**
 * @author: Yang
 * @date: 2023/3/14
 * @description: 网页导航ViewState
 */
data class WebGuideViewState(
    override val status: ListPageViewStatus,
    val articles: MutableList<WebCategory>? = null
) : ListPageViewState(status)