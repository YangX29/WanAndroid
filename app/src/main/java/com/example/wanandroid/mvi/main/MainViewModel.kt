package com.example.wanandroid.mvi.main

import com.example.wanandroid.R
import com.example.wanandroid.base.BaseViewModel
import com.example.wanandroid.base.mvi.ViewEvent
import com.example.wanandroid.net.CookieManager
import com.example.wanandroid.utils.extension.launchByIo
import com.example.wanandroid.utils.user.AuthManager
import com.example.wanandroid.utils.user.UserManager
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first

/**
 * @author: Yang
 * @date: 2023/2/5
 * @description: 主页ViewModel
 */
class MainViewModel : BaseViewModel<MainViewState, MainViewIntent>() {

    override fun handleIntent(viewIntent: MainViewIntent) {
        if (viewIntent == MainViewIntent.CheckLoginState) {
            checkLoginState()
        }
    }

    /**
     * 检查登录状态，如果已登录且未过期就更新用户信息
     */
    private fun checkLoginState() {
        launchByIo {
            //检查当前登录状态
            val checkLoginTask = async { UserManager.isLogin().first() }
            val checkCookieTask = async { CookieManager.loginExpired() }
            val hasLogin = checkLoginTask.await()
            val cookieExpired = checkCookieTask.await()
            if (hasLogin && !cookieExpired) {//已登录，未过期
                //更新用户信息
                apiService.getUserInfo().let {
                    if (it.isSuccess()) {
                        UserManager.updateUserInfo(it.data?.userInfo)
                        UserManager.updateCoinInfo(it.data?.coinInfo)
                    }
                }
            } else if (hasLogin) {//登录过期
                //清空用户信息，退出登录状态
                AuthManager.logout()
                emitViewEvent(ViewEvent.Toast(R.string.toast_login_expires))
            } else {
                //未登录
            }
        }
    }

}