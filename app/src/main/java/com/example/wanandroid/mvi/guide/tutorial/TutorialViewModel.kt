package com.example.wanandroid.mvi.guide.tutorial

import com.example.wanandroid.utils.extension.executeCall
import com.example.wanandroid.mvi.list.ListPageViewIntent
import com.example.wanandroid.mvi.list.ListPageViewModel
import com.example.wanandroid.mvi.list.ListPageViewStatus

/**
 * @author: Yang
 * @date: 2023/3/16
 * @description:
 */
class TutorialViewModel : ListPageViewModel<TutorialViewState, ListPageViewIntent>() {

    override fun refresh(isInit: Boolean) {
        executeCall({ apiService.getTutorialList() }, {
            updateViewState(
                TutorialViewState(
                    ListPageViewStatus.RefreshFinish(
                        page?.isFinish ?: false
                    ), it
                )
            )
        }, {
            updateViewState(TutorialViewState(ListPageViewStatus.RefreshFailed))
        })
    }

}