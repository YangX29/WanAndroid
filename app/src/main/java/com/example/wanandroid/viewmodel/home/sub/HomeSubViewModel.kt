package com.example.wanandroid.viewmodel.home.sub

import com.example.wanandroid.ui.list.ListPageViewModel
import com.example.wanandroid.ui.list.ListPageViewStatus
import com.example.wanandroid.utils.extension.launchByIo
import kotlinx.coroutines.async

/**
 * @author: Yang
 * @date: 2023/2/23
 * @description:
 */
class HomeSubViewModel : ListPageViewModel<HomeSubViewState>() {

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
            //获取数据
            val articles = getTopArticle.await()
            val banners = getBanner?.await()
            //TODO 检查接口回调结果
            //更新界面状态
            updateViewState(
                HomeSubViewState(
                    ListPageViewStatus.RefreshFinish,
                    banners?.data,
                    articles.data
                )
            )
        }

    }

    /**
     * 加载更多
     */
    override fun loadMore() {

    }

    /**
     * 点击Item
     */
    override fun itemClick(position: Int) {

    }

}