package com.example.wanandroid.mvi.guide

import com.example.wanandroid.model.Category
import com.example.wanandroid.mvi.list.ListPageViewState
import com.example.wanandroid.mvi.list.ListPageViewStatus

/**
 * @author: Yang
 * @date: 2023/7/16
 * @description: 分类列表ViewState
 */
data class CategoryViewState(
    override val status: ListPageViewStatus,
    val categoryList: MutableList<Category>? = null
) : ListPageViewState(status)