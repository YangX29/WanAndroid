package com.example.wanandroid.viewmodel.auth

import com.example.wanandroid.base.mvi.ViewState

/**
 * @author: Yang
 * @date: 2023/3/28
 * @description: 登录注册页面ViewState
 */
sealed class AuthViewState : ViewState() {
    //切换到登录页
    object SwitchToLogin : AuthViewState()

    //切换到注册页
    object SwitchToRegister : AuthViewState()

    //登录成功
    object LoginSuccess : AuthViewState()
}