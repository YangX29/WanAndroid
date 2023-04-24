package com.example.wanandroid.mvi.profile

import com.example.wanandroid.base.mvi.ViewState
import com.example.wanandroid.model.UserProfile

/**
 * @author: Yang
 * @date: 2023/4/24
 * @description: 用户信息ViewState
 */
sealed class ProfileViewState : ViewState(){
    //初始化成功
    data class InitSuccess(val profile: UserProfile) : ProfileViewState()

    //初始化失败
    object InitFailed : ProfileViewState()
}