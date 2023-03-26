package com.example.wanandroid.viewmodel.guide.web

import com.example.wanandroid.viewmodel.list.ListPageViewIntent
import com.example.wanandroid.viewmodel.list.ListPageViewModel
import com.example.wanandroid.viewmodel.list.ListPageViewStatus

/**
 * @author: Yang
 * @date: 2023/3/14
 * @description: 网页导航ViewModel
 */
class WebGuideViewModel : ListPageViewModel<WebGuideViewState, ListPageViewIntent>() {

    override fun refresh(isInit: Boolean) {
        executeCall({ apiService.getWebList() }, {
            updateViewState(WebGuideViewState(ListPageViewStatus.RefreshFinish, it))
        }, {
            updateViewState(WebGuideViewState(ListPageViewStatus.RefreshFailed))
        })
    }

}