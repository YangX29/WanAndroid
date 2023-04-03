package com.example.wanandroid.mvi.auth

import com.example.wanandroid.base.mvi.ViewIntent

/**
 * @author: Yang
 * @date: 2023/3/28
 * @description: 登录注册ViewIntent
 */
sealed class AuthViewIntent : ViewIntent() {
    //切换到登录页
    object SwitchToLogin : AuthViewIntent()

    //切换到注册页
    object SwitchToRegister : AuthViewIntent()

    //登录成功
    object LoginSuccess : AuthViewIntent()
}