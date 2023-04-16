package com.example.wanandroid.mvi.list

import android.os.Parcelable
import com.example.wanandroid.model.ListPage
import com.example.wanandroid.net.ResponseResult
import com.example.wanandroid.utils.extension.executeCall

/**
 * @author: Yang
 * @date: 2023/4/16
 * @description: 通用简单列表ViewModel
 */
abstract class SimpleListViewModel<T : Parcelable> :
    ListPageViewModel<SimpleListViewState<T>, ListPageViewIntent>() {

    override fun refresh(isInit: Boolean) {
        loadData(true)
    }

    override fun loadMore() {
        loadData(false)
    }

    /**
     * 加载数据
     */
    private fun loadData(isRefresh: Boolean) {
        executeCall({ getList() }, {
            it?.apply {
                //更新分页
                updatePage(it)
                //更新界面
                val status = if (isRefresh) {
                    ListPageViewStatus.RefreshFinish(page?.isFinish ?: false)
                } else {
                    ListPageViewStatus.LoadMoreFinish(page?.isFinish ?: false)
                }
                updateViewState(SimpleListViewState(status, it.list))
            }
        }, {
            val status = if (isRefresh) {
                ListPageViewStatus.RefreshFailed
            } else {
                ListPageViewStatus.LoadMoreFailed
            }
            updateViewState(SimpleListViewState(status))
        })
    }

    /**
     * 获取文章列表API
     */
    abstract suspend fun getList(): ResponseResult<ListPage<T>>

}