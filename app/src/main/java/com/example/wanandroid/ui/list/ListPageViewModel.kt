package com.example.wanandroid.ui.list

import com.example.wanandroid.base.BaseViewModel

/**
 * @author: Yang
 * @date: 2023/2/25
 * @description: 通用列表的ViewModel
 */
abstract class ListPageViewModel<VS : ListPageViewState> : BaseViewModel<VS, ListPageViewIntent>() {

    override fun handleIntent(viewIntent: ListPageViewIntent) {
        when (viewIntent) {
            is ListPageViewIntent.Refresh -> {
                refresh()
            }
            is ListPageViewIntent.ItemClick -> {
                itemClick(viewIntent.position)
            }
            is ListPageViewIntent.LoadMore -> {
                loadMore()
            }
        }
    }

    /**
     * 刷新
     */
    abstract fun refresh()

    /**
     * 点击item
     */
    abstract fun itemClick(position: Int)

    /**
     * 加载更多
     */
    abstract fun loadMore()

}