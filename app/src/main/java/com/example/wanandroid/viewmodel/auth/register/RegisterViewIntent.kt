package com.example.wanandroid.viewmodel.auth.register

import com.example.wanandroid.base.mvi.ViewIntent

/**
 * @author: Yang
 * @date: 2023/3/28
 * @description: 注册ViewIntent
 */
sealed class RegisterViewIntent : ViewIntent() {
    //注册
    data class Register(val account: String, val password: String, val ensurePassword: String) :
        RegisterViewIntent()
}