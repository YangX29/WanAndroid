package com.example.wanandroid.mvi.profile

import com.example.wanandroid.base.BaseViewModel
import com.example.wanandroid.utils.extension.executeCall
import com.example.wanandroid.utils.user.UserManager

/**
 * @author: Yang
 * @date: 2023/4/24
 * @description: 用户信息ViewModel
 */
class ProfileViewModel : BaseViewModel<ProfileViewState, ProfileViewIntent>() {

    override fun handleIntent(viewIntent: ProfileViewIntent) {
        if (viewIntent is ProfileViewIntent.Init) {
            initData()
        }
    }

    /**
     * 初始化数据
     */
    private fun initData() {
        executeCall({ apiService.getUserInfo() }, {
            if (it != null) {
                //更新用户信息
                UserManager.updateUserInfo(it.userInfo)
                UserManager.updateCoinInfo(it.coinInfo)
                updateViewState(ProfileViewState.InitSuccess(it))
            } else {
                updateViewState(ProfileViewState.InitFailed)
            }
        }, {
            updateViewState(ProfileViewState.InitFailed)
        })
    }

}