package com.example.wanandroid.mvi.auth.login

import com.example.wanandroid.base.BaseViewModel
import com.example.wanandroid.utils.datastore.StoreKey
import com.example.wanandroid.utils.extension.*
import com.example.wanandroid.utils.user.UserManager
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.zip

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
            is LoginViewIntent.Init -> {
                initData()
            }
        }
    }

    /**
     * 初始化数据
     */
    private fun initData() {
        launch {
            //获取上次登录的用户名和密码
            getData(StoreKey.KEY_ACCOUNT)
                .zip(getData(StoreKey.KEY_PASSWORD)) { account, password ->
                    updateViewState(LoginViewState.ShowOldMsg(account, password))
                }.collect()
        }
    }

    /**
     * 登录
     */
    private fun login(account: String, password: String, remember: Boolean) {
        executeCall({ apiService.login(account, password) }, {
            launch {
                //保存用户名
                val accountTask = async { putDataSuspend(StoreKey.KEY_ACCOUNT, account) }
                //记住密码
                val passwordTask = async {
                    if (remember) {
                        putDataSuspend(StoreKey.KEY_PASSWORD, password)
                    } else {
                        removeDataSuspend(StoreKey.KEY_PASSWORD)
                    }
                }
                //用户信息
                val userTask = async { UserManager.login(it) }
                accountTask.await()
                passwordTask.await()
                userTask.await()
                //登录成功
                updateViewState(LoginViewState.LoginSuccess)
            }
        }, {
            updateViewState(LoginViewState.LoginFailed)
        })
    }

}