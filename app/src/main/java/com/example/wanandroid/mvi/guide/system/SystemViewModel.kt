package com.example.wanandroid.mvi.guide.system

import com.example.wanandroid.utils.extension.executeCall
import com.example.wanandroid.mvi.list.ListPageViewIntent
import com.example.wanandroid.mvi.list.ListPageViewModel
import com.example.wanandroid.mvi.list.ListPageViewStatus

/**
 * @author: Yang
 * @date: 2023/3/16
 * @description: 体系ViewModel
 */
class SystemViewModel : ListPageViewModel<SystemViewState, ListPageViewIntent>() {

    override fun refresh(isInit: Boolean) {
        executeCall({ apiService.getSystemCategory() }, {
            //更新分页
            updateViewState(
                SystemViewState(
                    ListPageViewStatus.RefreshFinish(
                        page?.isFinish ?: false
                    ), it
                )
            )
        }, {
            updateViewState(SystemViewState(ListPageViewStatus.RefreshFailed))
        })
    }

}