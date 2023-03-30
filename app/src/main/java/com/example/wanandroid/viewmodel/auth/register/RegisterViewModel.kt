package com.example.wanandroid.viewmodel.auth.register

import com.example.wanandroid.base.BaseViewModel
import com.example.wanandroid.utils.extension.executeCall

/**
 * @author: Yang
 * @date: 2023/3/28
 * @description: 注册页ViewModel
 */
class RegisterViewModel : BaseViewModel<RegisterViewState, RegisterViewIntent>() {

    override fun handleIntent(viewIntent: RegisterViewIntent) {
        when (viewIntent) {
            is RegisterViewIntent.Register -> {
                register(viewIntent.account, viewIntent.password, viewIntent.ensurePassword)
            }
        }
    }

    /**
     * 注册
     */
    private fun register(account: String, password: String, ensurePassword: String) {
        executeCall({ apiService.register(account, password, ensurePassword) }, {
            //TODO 记录用户信息，持久化cookie
            //注册成功
            updateViewState(RegisterViewState.RegisterSuccess)
        }, {
            updateViewState(RegisterViewState.RegisterFailed)
        })
    }

}