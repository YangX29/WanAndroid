package com.example.wanandroid.viewmodel.auth

import com.example.wanandroid.base.BaseViewModel

/**
 * @author: Yang
 * @date: 2023/3/28
 * @description: 登录注册页ViewModel
 */
class AuthViewModel : BaseViewModel<AuthViewState, AuthViewIntent>() {

    override fun handleIntent(viewIntent: AuthViewIntent) {
        when (viewIntent) {
            is AuthViewIntent.SwitchToLogin -> {
                updateViewState(AuthViewState.SwitchToLogin)
            }
            is AuthViewIntent.SwitchToRegister -> {
                updateViewState(AuthViewState.SwitchToRegister)
            }
            is AuthViewIntent.LoginSuccess -> {
                updateViewState(AuthViewState.LoginSuccess)
            }
        }
    }

}