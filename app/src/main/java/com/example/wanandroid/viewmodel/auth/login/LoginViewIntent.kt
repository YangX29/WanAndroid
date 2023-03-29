package com.example.wanandroid.viewmodel.auth.login

import com.example.wanandroid.base.mvi.ViewIntent

/**
 * @author: Yang
 * @date: 2023/3/27
 * @description: 登录页面ViewIntent
 */
sealed class LoginViewIntent : ViewIntent() {
    //登录
    data class Login(val account: String, val password: String, val remember: Boolean) :
        LoginViewIntent()
}