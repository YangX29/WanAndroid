package com.example.wanandroid.viewmodel.guide.tutorial

import com.example.wanandroid.viewmodel.list.ListPageViewIntent
import com.example.wanandroid.viewmodel.list.ListPageViewModel
import com.example.wanandroid.viewmodel.list.ListPageViewStatus

/**
 * @author: Yang
 * @date: 2023/3/16
 * @description:
 */
class TutorialViewModel : ListPageViewModel<TutorialViewState, ListPageViewIntent>() {

    override fun refresh(isInit: Boolean) {
        executeCall({ apiService.getTutorialList() }, {
            updateViewState(TutorialViewState(ListPageViewStatus.RefreshFinish, it))
        }, {
            updateViewState(TutorialViewState(ListPageViewStatus.RefreshFailed))
        })
    }

}