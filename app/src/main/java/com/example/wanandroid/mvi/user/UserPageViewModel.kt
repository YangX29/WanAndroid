package com.example.wanandroid.mvi.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wanandroid.mvi.article.ArticleRepository
import com.example.wanandroid.mvi.list.ListPageViewIntent
import com.example.wanandroid.mvi.list.ListPageViewModel
import com.example.wanandroid.mvi.list.ListPageViewStatus
import com.example.wanandroid.utils.extension.executeCall
import com.example.wanandroid.utils.extension.launch

/**
 * @author: Yang
 * @date: 2023/4/19
 * @description: 用户主页ViewModel
 */
class UserPageViewModel(private val id: Long) :
    ListPageViewModel<UserPageViewState, ListPageViewIntent>() {

    private val repository by lazy { ArticleRepository() }

    class Factory(val id: Long) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return UserPageViewModel(id) as T
        }
    }

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
        executeCall({ apiService.getUserPage(id, page?.page ?: 1) }, {
            it?.apply {
                //更新分页
                updatePage(shareArticles)
                //更新界面
                val status = if (isRefresh) {
                    ListPageViewStatus.RefreshFinish(page?.isFinish ?: false)
                } else {
                    ListPageViewStatus.LoadMoreFinish(page?.isFinish ?: false)
                }
                updateViewState(UserPageViewState(status, it.coinInfo, it.shareArticles.list))
            }
        }, {
            val status = if (isRefresh) {
                ListPageViewStatus.RefreshFailed
            } else {
                ListPageViewStatus.LoadMoreFailed
            }
            updateViewState(UserPageViewState(status))
        })
    }

    /**
     * 收藏/取消收藏Article
     */
    fun collectArticle(collect: Boolean, id: Long) {
        launch {
            if (collect) {
                repository.collectArticle(id)
            } else {
                repository.uncollectArticle(id)
            }
        }
    }

}