package com.example.wanandroid.mvi.setting

import com.example.wanandroid.base.BaseViewModel
import com.example.wanandroid.base.mvi.ViewEvent
import com.example.wanandroid.utils.extension.executeCall
import com.example.wanandroid.utils.user.UserManager

/**
 * @author: Yang
 * @date: 2023/4/27
 * @description: 设置页ViewModel
 */
class SettingViewModel : BaseViewModel<SettingViewState, SettingViewIntent>() {

    override fun handleIntent(viewIntent: SettingViewIntent) {
        when (viewIntent) {
            is SettingViewIntent.InitConfig -> {
                initConfig()
            }

            is SettingViewIntent.Logout -> {
                logout()
            }
        }
    }

    /**
     * 初始化配置信息
     */
    private fun initConfig() {
        //TODO
    }

    /**
     * 退出登录
     */
    private fun logout() {
        executeCall({ apiService.logout() }, {
            //清除用户信息
            UserManager.logout {
                //退出成功
                updateViewState(SettingViewState.LogoutSuccess)
            }
        }, {
            //TODO 退出失败
            emitViewEvent(ViewEvent.Toast("登出失败"))
        }, true)
    }

}