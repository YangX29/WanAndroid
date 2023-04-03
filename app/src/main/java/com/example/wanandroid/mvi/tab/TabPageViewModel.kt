package com.example.wanandroid.mvi.tab

import com.example.wanandroid.base.BaseViewModel
import com.example.wanandroid.model.Category
import com.example.wanandroid.net.ResponseResult
import com.example.wanandroid.utils.extension.executeCall

/**
 * @author: Yang
 * @date: 2023/3/17
 * @description: 通用tab页面ViewModel
 */
abstract class TabPageViewModel : BaseViewModel<TabPageViewState, TabPageViewIntent>() {

    override fun handleIntent(viewIntent: TabPageViewIntent) {
        if (viewIntent == TabPageViewIntent.InitTab) {
            initTab()
        }
    }

    /**
     * 初始化tab
     */
    open fun initTab() {
        executeCall({ getTabCategories() }, {
            it?.apply {
                updateViewState(TabPageViewState(TabPageViewStatus.InitFinish, it))
            }
        }, {
            updateViewState(TabPageViewState(TabPageViewStatus.InitFailed))
        })
    }

    /**
     * 获取tab数据
     */
    abstract suspend fun getTabCategories(): ResponseResult<MutableList<Category>>

}