package com.example.wanandroid.mvi.home.sub

import com.example.wanandroid.model.Article
import com.example.wanandroid.mvi.article.ArticleRepository
import com.example.wanandroid.utils.extension.executeCall
import com.example.wanandroid.utils.extension.executeCallSuspend
import com.example.wanandroid.mvi.list.ListPageViewModel
import com.example.wanandroid.mvi.list.ListPageViewStatus
import com.example.wanandroid.utils.extension.launchByIo
import com.example.wanandroid.mvi.list.ListPageViewIntent
import com.example.wanandroid.utils.extension.launch
import kotlinx.coroutines.async

/**
 * @author: Yang
 * @date: 2023/2/23
 * @description: 首页ViewModel
 */
class HomeSubViewModel : ListPageViewModel<HomeSubViewState, ListPageViewIntent>() {

    private val repository by lazy { ArticleRepository() }

    //文章列表
    private val articleList = mutableListOf<Article>()

    /**
     * 刷新
     */
    override fun refresh(isInit: Boolean) {
        launchByIo {
            //首页轮播图, 只在初始化时获取banner数据
            val getBanner = if (isInit) {
                async { executeCallSuspend({ apiService.getBanner() }) }
            } else {
                null
            }
            //置顶文章
            val getTopArticle = async { executeCallSuspend({ apiService.getTopArticles() }) }
            // 首页文章列表
            val getArticle =
                async { executeCallSuspend({ apiService.getHomeArticles(page?.page ?: 0) }) }
            //获取数据
            val topArticles = getTopArticle.await()
            val banners = getBanner?.await()
            val articles = getArticle.await()
            //检查接口回调结果
            if (articles.isFailed() || banners?.isFailed() == true || topArticles.isFailed()) {
                updateViewState(HomeSubViewState(ListPageViewStatus.RefreshFailed))
            } else {
                // 更新分页
                articles.data?.let { updatePage(it) }
                // 合并置顶文章和文章列表
                val list = ((topArticles.data ?: mutableListOf()) + (articles.data?.list
                    ?: mutableListOf())).toMutableList()
                //更新界面状态
                updateViewState(
                    HomeSubViewState(
                        ListPageViewStatus.RefreshFinish(page?.isFinish ?: false),
                        banners?.data,
                        list
                    )
                )
                //更新列表
                articleList.clear()
                articleList.addAll(list)
            }
        }

    }

    /**
     * 加载更多
     */
    override fun loadMore() {
        if (page?.isFinish == true) return
        executeCall({ apiService.getHomeArticles(page?.page ?: 0) }, {
            it?.apply {
                //更新分页
                updatePage(it)
                //刷新数据
                updateViewState(
                    HomeSubViewState(
                        ListPageViewStatus.LoadMoreFinish(page?.isFinish ?: false),
                        articles = it.list
                    )
                )
                //更新列表
                articleList.addAll(it.list)
            }
        }, {
            updateViewState(HomeSubViewState(ListPageViewStatus.LoadMoreFailed))
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