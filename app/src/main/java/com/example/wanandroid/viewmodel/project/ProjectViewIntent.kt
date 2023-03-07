package com.example.wanandroid.viewmodel.project

import com.example.wanandroid.base.mvi.ViewIntent
import com.example.wanandroid.viewmodel.official.OfficialViewIntent

/**
 * @author: Yang
 * @date: 2023/3/7
 * @description: 项目页面ViewIntent
 */
sealed class ProjectViewIntent : ViewIntent() {
    //初始化公众号Tab
    object InitTab : ProjectViewIntent()
}