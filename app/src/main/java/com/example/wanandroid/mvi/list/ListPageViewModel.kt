package com.example.wanandroid.mvi.list

import com.example.wanandroid.base.BaseViewModel
import com.example.wanandroid.model.ListPage
import com.example.wanandroid.model.Page

/**
 * @author: Yang
 * @date: 2023/2/25
 * @description: 通用列表的ViewModel
 */
abstract class ListPageViewModel<VS : ListPageViewState, VI : ListPageViewIntent> :
    BaseViewModel<VS, VI>() {

    protected var page: Page? = null

    override fun handleIntent(viewIntent: VI) {
        when (viewIntent) {
            is ListPageViewIntent.Refresh -> {
                //刷新重置分页
                page = null
                refresh(viewIntent.isInit)
            }

            is ListPageViewIntent.LoadMore -> {
                loadMore()
            }
        }
    }

    /**
     * 更新分页
     */
    protected fun updatePage(listPage: ListPage<*>) {
        if (page == null) {
            page = Page(listPage.curPage, listPage.pageCount)
        } else {
            page?.update(listPage)
        }
    }

    /**
     * 刷新
     */
    open fun refresh(isInit: Boolean) {}

    /**
     * 加载更多
     */
    open fun loadMore() {}

}