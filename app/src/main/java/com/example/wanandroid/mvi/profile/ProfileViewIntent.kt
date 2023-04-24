package com.example.wanandroid.mvi.profile

import com.example.wanandroid.base.mvi.ViewIntent

/**
 * @author: Yang
 * @date: 2023/4/24
 * @description: 用户信息ViewIntent
 */
sealed class ProfileViewIntent : ViewIntent() {

    //初始化
    object Init : ProfileViewIntent()

}