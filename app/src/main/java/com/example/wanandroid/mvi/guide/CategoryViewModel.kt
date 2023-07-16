package com.example.wanandroid.mvi.guide

import com.example.wanandroid.model.Category
import com.example.wanandroid.mvi.list.ListPageViewIntent
import com.example.wanandroid.mvi.list.ListPageViewModel
import com.example.wanandroid.mvi.list.ListPageViewStatus
import com.example.wanandroid.net.ResponseResult
import com.example.wanandroid.utils.extension.executeCall

/**
 * @author: Yang
 * @date: 2023/7/16
 * @description:
 */
abstract class CategoryViewModel : ListPageViewModel<CategoryViewState, ListPageViewIntent>() {

    abstract suspend fun getCategoryList(): ResponseResult<MutableList<Category>>

    override fun refresh(isInit: Boolean) {
        executeCall({ getCategoryList() }, {
            updateViewState(
                CategoryViewState(
                    ListPageViewStatus.RefreshFinish(
                        page?.isFinish ?: false
                    ), it
                )
            )
        }, {
            updateViewState(CategoryViewState(ListPageViewStatus.RefreshFailed))
        })
    }

}