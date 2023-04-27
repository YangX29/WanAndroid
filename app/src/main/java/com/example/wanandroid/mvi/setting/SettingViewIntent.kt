package com.example.wanandroid.mvi.setting

import com.example.wanandroid.base.mvi.ViewIntent

/**
 * @author: Yang
 * @date: 2023/4/27
 * @description: 设置页ViewIntent
 */
sealed class SettingViewIntent : ViewIntent() {
    //初始化配置信息
    object InitConfig : SettingViewIntent()

    //退出登录
    object Logout : SettingViewIntent()
}