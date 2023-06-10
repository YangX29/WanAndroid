package com.example.wanandroid.mvi.article

import com.example.modele_net.scope_v1.ApiProvider
import com.example.wanandroid.net.ResponseResult
import com.example.wanandroid.net.WanAndroidApi
import com.example.wanandroid.net.executeWASuspend

/**
 * @author: Yang
 * @date: 2023/6/10
 * @description: 文章网络数据源
 */
class ArticleDataSource {

    //接口服务对象
    private val apiService by lazy { ApiProvider.api(WanAndroidApi::class.java) }

    /**
     * 收藏文章
     */
    suspend fun collectArticle(id: Long): ResponseResult<Any> {
        return executeWASuspend({ apiService.collectArticle(id) }, true)
    }

    /**
     * 取消收藏文章，文章列表
     */
    suspend fun uncollectArticle(id: Long): ResponseResult<Any> {
        return executeWASuspend({ apiService.uncollectArticle(id) }, true)
    }

    /**
     * 取消我的收藏
     */
    suspend fun uncollectMine(id: Long, originId: Long): ResponseResult<Any> {
        return executeWASuspend({ apiService.uncollectMine(id, originId) }, true)
    }

}