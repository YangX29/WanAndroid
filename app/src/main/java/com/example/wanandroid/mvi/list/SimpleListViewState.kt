package com.example.wanandroid.mvi.list

import android.os.Parcelable

/**
 * @author: Yang
 * @date: 2023/3/5
 * @description: 通用文章列表ViewState
 */
data class SimpleListViewState<T: Parcelable>(
    override val status: ListPageViewStatus,
    val data: MutableList<T>? = null
) : ListPageViewState(status)