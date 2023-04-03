package com.example.wanandroid.mvi.guide.system

import com.example.wanandroid.model.Category
import com.example.wanandroid.mvi.list.ListPageViewState
import com.example.wanandroid.mvi.list.ListPageViewStatus

/**
 * @author: Yang
 * @date: 2023/3/16
 * @description: 体系ViewState
 */
data class SystemViewState(
    override val status: ListPageViewStatus,
    val systems: MutableList<Category>? = null
) : ListPageViewState(status)
