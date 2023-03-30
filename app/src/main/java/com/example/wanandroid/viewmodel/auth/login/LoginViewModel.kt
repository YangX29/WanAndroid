package com.example.wanandroid.viewmodel.auth.login

import com.example.wanandroid.base.BaseViewModel
import com.example.wanandroid.utils.extension.executeCall

/**
 * @author: Yang
 * @date: 2023/3/27
 * @description: 登录页面ViewModel
 */
class LoginViewModel : BaseViewModel<LoginViewState, LoginViewIntent>() {

    override fun handleIntent(viewIntent: LoginViewIntent) {
        when (viewIntent) {
            is LoginViewIntent.Login -> {
                login(viewIntent.account, viewIntent.password, viewIntent.remember)
            }
        }
    }

    /**
     * 登录
     */
    private fun login(account: String, password: String, remember: Boolean) {
        executeCall({ apiService.login(account, password) }, {
            //记住密码
            if (remember) {
                //TODO
            }
            //TODO 记录用户信息，持久化cookie
            //登录成功
            updateViewState(LoginViewState.LoginSuccess)
        }, {
            updateViewState(LoginViewState.LoginFailed)
        })
    }

}