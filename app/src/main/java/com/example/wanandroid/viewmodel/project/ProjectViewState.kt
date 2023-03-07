package com.example.wanandroid.viewmodel.project

import com.example.wanandroid.base.mvi.ViewState
import com.example.wanandroid.model.Category

/**
 * @author: Yang
 * @date: 2023/3/7
 * @description: 项目页面ViewState
 */
data class ProjectViewState(
    val status: ProjectViewStatus,
    val tabs: MutableList<Category>? = null
) : ViewState()

sealed class ProjectViewStatus {
    //初始化成功
    object InitFinish : ProjectViewStatus()

    //初始化失败
    object InitFailed : ProjectViewStatus()
}