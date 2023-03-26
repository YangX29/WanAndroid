package com.example.wanandroid.viewmodel.share

import com.example.wanandroid.viewmodel.article.ArticleListViewModel

/**
 * @author: Yang
 * @date: 2023/3/25
 * @description: 我的分享列表ViewModel
 */
class ShareListViewModel : ArticleListViewModel() {

    override suspend fun getArticleList() = apiService.shareList(page?.page ?: 1)

}