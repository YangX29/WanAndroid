package com.example.wanandroid.viewmodel.guide.system

import com.example.wanandroid.viewmodel.list.ListPageViewIntent
import com.example.wanandroid.viewmodel.list.ListPageViewModel
import com.example.wanandroid.viewmodel.list.ListPageViewStatus

/**
 * @author: Yang
 * @date: 2023/3/16
 * @description: 体系ViewModel
 */
class SystemViewModel : ListPageViewModel<SystemViewState, ListPageViewIntent>() {

    override fun refresh(isInit: Boolean) {
        executeCall({ apiService.getSystemCategory() }, {
            updateViewState(SystemViewState(ListPageViewStatus.RefreshFinish, it))
        }, {
            updateViewState(SystemViewState(ListPageViewStatus.RefreshFailed))
        })
    }

}