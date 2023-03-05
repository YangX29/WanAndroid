package com.example.wanandroid.viewmodel.official

import com.example.wanandroid.base.mvi.ViewIntent

/**
 * @author: Yang
 * @date: 2023/3/5
 * @description: 公众号页面ViewIntent
 */
sealed class OfficialViewIntent : ViewIntent() {
    //初始化公众号Tab
    object InitTab : OfficialViewIntent()
}