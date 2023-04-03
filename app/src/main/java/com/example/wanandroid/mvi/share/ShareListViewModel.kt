package com.example.wanandroid.mvi.share

import com.example.wanandroid.model.Article
import com.example.wanandroid.model.ListPage
import com.example.wanandroid.net.ResponseResult
import com.example.wanandroid.mvi.article.ArticleListViewModel

/**
 * @author: Yang
 * @date: 2023/3/25
 * @description: 我的分享列表ViewModel
 */
class ShareListViewModel : ArticleListViewModel() {

    override suspend fun getArticleList() = getShareList()

    /**
     * shareList接口返回接口转换为通用列表结果
     */
    private suspend fun getShareList(): ResponseResult<ListPage<Article>> {
        val response = apiService.shareList(page?.page ?: 1)
        return ResponseResult(
            response.errorCode,
            response.errorMsg,
            response.data?.shareArticles
        )
    }

}