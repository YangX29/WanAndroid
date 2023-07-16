package com.example.wanandroid.mvi.guide.tree

import com.example.wanandroid.mvi.list.ListPageViewIntent
import com.example.wanandroid.mvi.list.ListPageViewModel
import com.example.wanandroid.mvi.list.ListPageViewStatus
import com.example.wanandroid.utils.extension.executeCall

/**
 * @author: Yang
 * @date: 2023/3/16
 * @description: 学习路径ViewModel
 */
class TreeViewModel : ListPageViewModel<TreeViewState, ListPageViewIntent>() {

    override fun refresh(isInit: Boolean) {
        executeCall({ apiService.getSystemCategory() }, {
            //更新分页
            updateViewState(
                TreeViewState(
                    ListPageViewStatus.RefreshFinish(
                        page?.isFinish ?: false
                    ), it?.filter { category -> category.isTree() }?.toMutableList()
                )
            )
        }, {
            updateViewState(TreeViewState(ListPageViewStatus.RefreshFailed))
        })
    }

}