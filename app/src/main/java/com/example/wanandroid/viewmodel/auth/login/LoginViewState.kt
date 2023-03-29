package com.example.wanandroid.viewmodel.auth.login

import com.example.wanandroid.base.mvi.ViewState

/**
 * @author: Yang
 * @date: 2023/3/27
 * @description: 登录页面ViewState
 */
sealed class LoginViewState : ViewState() {
    //登录成功
    object LoginSuccess : LoginViewState()

    //登录失败
    object LoginFailed : LoginViewState()
}