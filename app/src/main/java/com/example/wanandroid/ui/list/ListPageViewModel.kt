package com.example.wanandroid.ui.list

import com.example.wanandroid.base.BaseViewModel
import com.example.wanandroid.model.ListPage
import com.example.wanandroid.model.Page

/**
 * @author: Yang
 * @date: 2023/2/25
 * @description: 通用列表的ViewModel
 */
abstract class ListPageViewModel<VS : ListPageViewState> : BaseViewModel<VS, ListPageViewIntent>() {

    protected var page: Page? = null

    override fun handleIntent(viewIntent: ListPageViewIntent) {
        when (viewIntent) {
            is ListPageViewIntent.Refresh -> {
                //刷新重置分页
                page = null
                refresh(viewIntent.isInit)
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
     * 更新分页
     */
    protected fun <T : Any> updatePage(listPage: ListPage<T>) {
        if (page == null) {
            page = Page(listPage.curPage, listPage.pageCount)
        } else {
            page?.update(listPage)
        }
    }

    /**
     * 刷新
     */
    abstract fun refresh(isInit: Boolean)

    /**
     * 点击item
     */
    abstract fun itemClick(position: Int)

    /**
     * 加载更多
     */
    abstract fun loadMore()

}