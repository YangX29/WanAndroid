package com.example.wanandroid.mvi.setting

import com.example.wanandroid.base.mvi.ViewState

/**
 * @author: Yang
 * @date: 2023/4/27
 * @description: 设置页ViewState
 */
sealed class SettingViewState : ViewState() {

    //退出登录成功
    object LogoutSuccess : SettingViewState()

    //更新配置信息
    data class UpdateConfig(val config: SettingConfig) : SettingViewState()

}

/**
 * TODO 设置页配置信息
 */
data class SettingConfig(
    val theme: String
)