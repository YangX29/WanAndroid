package com.example.wanandroid.viewmodel.auth.register

import com.example.wanandroid.base.mvi.ViewState

/**
 * @author: Yang
 * @date: 2023/3/28
 * @description: 注册页ViewState
 */
sealed class RegisterViewState : ViewState() {
    //注册成功
    object RegisterSuccess : RegisterViewState()

    //注册失败
    object RegisterFailed : RegisterViewState()
}