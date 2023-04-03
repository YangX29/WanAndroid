package com.example.wanandroid.mvi.article

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wanandroid.utils.extension.executeCall
import com.example.wanandroid.mvi.tab.TabPageViewModel
import com.example.wanandroid.mvi.tab.TabPageViewState
import com.example.wanandroid.mvi.tab.TabPageViewStatus

/**
 * @author: Yang
 * @date: 2023/3/17
 * @description: 文章二级页面ViewModel
 */
class ArticleSubViewModel(private val id: Int) : TabPageViewModel() {

    override suspend fun getTabCategories() = apiService.getSystemCategory()

    override fun initTab() {
        executeCall({ getTabCategories() }, {
            it?.apply {
                //获取二级分类
                val subCategory = it.find { category -> category.id == id }?.children
                updateViewState(TabPageViewState(TabPageViewStatus.InitFinish, subCategory))
            }
        }, {
            updateViewState(TabPageViewState(TabPageViewStatus.InitFailed))
        })
    }

    class Factory(val id: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ArticleSubViewModel(id) as T
        }
    }

}