package com.example.wanandroid.viewmodel.home.qa

import com.example.wanandroid.base.mvi.ViewEvent
import com.example.wanandroid.model.Article
import com.example.wanandroid.ui.list.ListPageViewModel
import com.example.wanandroid.ui.list.ListPageViewStatus

/**
 * @author: Yang
 * @date: 2023/3/5
 * @description:
 */
class QAViewModel : ListPageViewModel<QAViewState>() {

    //文章列表
    private val articleList = mutableListOf<Article>()

    override fun refresh(isInit: Boolean) {
        loadData(true)
    }

    override fun loadMore() {
        loadData(false)
    }

    override fun itemClick(position: Int) {
        val article = articleList.getOrNull(position) ?: return
        emitViewEvent(ViewEvent.JumpToWeb(article.link))
    }

    /**
     * 加载数据
     */
    private fun loadData(isRefresh: Boolean) {
        executeCall({ apiService.getQAList(page?.page ?: 0) }, {
            it?.apply {
                //更新分页
                updatePage(it)
                //更新界面
                val status = if (isRefresh) {
                    ListPageViewStatus.RefreshFinish
                } else {
                    ListPageViewStatus.LoadMoreFinish(page?.isFinish ?: false)
                }
                updateViewState(QAViewState(status, it.list))
                //更新数据
                if (isRefresh) {
                    articleList.clear()
                }
                articleList.addAll(it.list)
            }
        }, {
            val status = if (isRefresh) {
                ListPageViewStatus.RefreshFailed
            } else {
                ListPageViewStatus.LoadMoreFailed
            }
            updateViewState(QAViewState(status))
        })
    }


}