package com.example.wanandroid.net

import com.example.wanandroid.model.Article
import com.example.wanandroid.model.Banner
import com.example.wanandroid.model.ListPage
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author: Yang
 * @date: 2023/1/7
 * @description: WanAndroid网站api
 */
interface WanAndroidApi {

    companion object {
        /**
         * baseUrl
         */
        const val BASE_URL = "https://www.wanandroid.com"
    }

    /**
     * 获取首页banner
     */
    @GET("banner/json")
    suspend fun getBanner(): ResponseResult<MutableList<Banner>>

    /**
     * 首页置顶文章
     */
    @GET("article/top/json")
    suspend fun getTopArticles(): ResponseResult<MutableList<Article>>

    /**
     * 首页文章列表
     */
    @GET("article/list/{page}/json")
    suspend fun getHomeArticles(@Path("page") page: Int): ResponseResult<ListPage<Article>>

    /**
     * 广场文章列表
     */
    @GET("user_article/list/{page}/json")
    suspend fun getSquareArticles(@Path("page") page: Int): ResponseResult<ListPage<Article>>

    /**
     * 问答列表
     */
    @GET("wenda/list/{page}/json")
    suspend fun getQAList(@Path("page") page: Int): ResponseResult<ListPage<Article>>
}