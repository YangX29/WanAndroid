package com.example.wanandroid.viewmodel.guide.tutorial

import com.example.wanandroid.model.Category
import com.example.wanandroid.viewmodel.list.ListPageViewState
import com.example.wanandroid.viewmodel.list.ListPageViewStatus

/**
 * @author: Yang
 * @date: 2023/3/16
 * @description: 教程页ViewState
 */
data class TutorialViewState(
    override val status: ListPageViewStatus,
    val tutorials: MutableList<Category>? = null
) : ListPageViewState(status)
