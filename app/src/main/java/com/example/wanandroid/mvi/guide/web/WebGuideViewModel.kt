package com.example.wanandroid.mvi.guide.web

import com.example.wanandroid.utils.extension.executeCall
import com.example.wanandroid.mvi.list.ListPageViewIntent
import com.example.wanandroid.mvi.list.ListPageViewModel
import com.example.wanandroid.mvi.list.ListPageViewStatus

/**
 * @author: Yang
 * @date: 2023/3/14
 * @description: 网页导航ViewModel
 */
class WebGuideViewModel : ListPageViewModel<WebGuideViewState, ListPageViewIntent>() {

    override fun refresh(isInit: Boolean) {
        executeCall({ apiService.getWebList() }, {
            updateViewState(
                WebGuideViewState(
                    ListPageViewStatus.RefreshFinish(
                        page?.isFinish ?: false
                    ), it
                )
            )
        }, {
            updateViewState(WebGuideViewState(ListPageViewStatus.RefreshFailed))
        })
    }

}