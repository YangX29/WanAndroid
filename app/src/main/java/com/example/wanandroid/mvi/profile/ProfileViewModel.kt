package com.example.wanandroid.mvi.profile

import com.example.wanandroid.base.BaseViewModel
import com.example.wanandroid.utils.extension.executeCall

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
                updateViewState(ProfileViewState.InitSuccess(it))
            } else {
                updateViewState(ProfileViewState.InitFailed)
            }
        }, {
            updateViewState(ProfileViewState.InitFailed)
        })
    }

}