package com.example.wanandroid.mvi.guide.tree

import com.example.wanandroid.model.Category
import com.example.wanandroid.mvi.list.ListPageViewState
import com.example.wanandroid.mvi.list.ListPageViewStatus

/**
 * @author: Yang
 * @date: 2023/3/16
 * @description: 学习路径ViewState
 */
data class TreeViewState(
    override val status: ListPageViewStatus,
    val tree: MutableList<Category>? = null
) : ListPageViewState(status)
