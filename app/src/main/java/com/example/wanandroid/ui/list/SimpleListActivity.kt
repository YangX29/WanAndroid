package com.example.wanandroid.ui.list

import android.os.Parcelable
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.wanandroid.mvi.list.SimpleListViewModel
import com.example.wanandroid.mvi.list.SimpleListViewState

/**
 * @author: Yang
 * @date: 2023/5/2
 * @description:
 */
abstract class SimpleListActivity<T : Parcelable, VM : SimpleListViewModel<T>> :
    ListPageActivity<SimpleListViewState<T>, VM>() {

    abstract override val adapter: BaseQuickAdapter<T, BaseViewHolder>

    override fun onLoadMore(viewState: SimpleListViewState<T>) {
        adapter.addData(viewState.data ?: mutableListOf())
    }

    override fun onRefresh(viewState: SimpleListViewState<T>) {
        adapter.setNewInstance(viewState.data ?: mutableListOf())
    }

}