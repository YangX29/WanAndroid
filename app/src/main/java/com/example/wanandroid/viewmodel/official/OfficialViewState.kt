package com.example.wanandroid.viewmodel.official

import com.example.wanandroid.base.mvi.ViewState
import com.example.wanandroid.model.WxOfficial

/**
 * @author: Yang
 * @date: 2023/3/5
 * @description: 公众号页面ViewState
 */
data class OfficialViewState(
    val status: OfficialViewStatus,
    val tabs: MutableList<WxOfficial>? = null
) : ViewState()

sealed class OfficialViewStatus {
    //初始化成功
    object InitFinish : OfficialViewStatus()

    //初始化失败
    object InitFailed : OfficialViewStatus()
}