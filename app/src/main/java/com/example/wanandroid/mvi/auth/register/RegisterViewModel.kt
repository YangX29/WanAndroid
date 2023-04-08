package com.example.wanandroid.mvi.auth.register

import com.example.wanandroid.base.BaseViewModel
import com.example.wanandroid.utils.datastore.StoreKey
import com.example.wanandroid.utils.datastore.putDataSuspend
import com.example.wanandroid.utils.datastore.putObject
import com.example.wanandroid.utils.extension.executeCall
import com.example.wanandroid.utils.extension.launch
import kotlinx.coroutines.async

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
            launch {
                //保存用户名
                val accountTask = async { putDataSuspend(StoreKey.KEY_ACCOUNT, account) }
                //用户信息
                val userTask = async { putObject(StoreKey.KEY_USER_INFO, it) }
                accountTask.await()
                userTask.await()
                //注册成功
                updateViewState(RegisterViewState.RegisterSuccess)
            }
        }, {
            updateViewState(RegisterViewState.RegisterFailed)
        }, true)
    }

}