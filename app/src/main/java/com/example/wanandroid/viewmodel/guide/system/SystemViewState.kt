package com.example.wanandroid.viewmodel.guide.system

import com.example.wanandroid.model.SystemCategory
import com.example.wanandroid.viewmodel.list.ListPageViewState
import com.example.wanandroid.viewmodel.list.ListPageViewStatus

/**
 * @author: Yang
 * @date: 2023/3/16
 * @description: 体系ViewState
 */
data class SystemViewState(
    override val status: ListPageViewStatus,
    val systems: MutableList<SystemCategory>? = null
) : ListPageViewState(status)
