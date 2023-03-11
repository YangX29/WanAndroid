package com.example.wanandroid.net

import com.example.wanandroid.model.Article
import com.example.wanandroid.model.Banner
import com.example.wanandroid.model.ListPage
import com.example.wanandroid.model.Category
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

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

    /**
     * 微信公众号列表
     */
    @GET("wxarticle/chapters/json")
    suspend fun getOfficialCategories(): ResponseResult<MutableList<Category>>

    /**
     * 公众号文章列表
     */
    @GET("wxarticle/list/{id}/{page}/json")
    suspend fun getOfficialArticleList(@Path("id") id: Int, @Path("page") page: Int): ResponseResult<ListPage<Article>>

    /**
     * 获取项目分类
     */
    @GET("project/tree/json")
    suspend fun getProjectCategories(): ResponseResult<MutableList<Category>>

    /**
     * 公众号文章列表
     */
    @GET("project/list/{page}/json")
    suspend fun getProjectArticleList(@Path("page") page: Int, @Query("cid") cid: Int): ResponseResult<ListPage<Article>>

    /**
     * 最新项目列表
     */
    @GET("article/listproject/{page}/json")
    suspend fun getNewestProjectList(@Path("page") page: Int): ResponseResult<ListPage<Article>>

    /**
     * 分享文章
     */
    @FormUrlEncoded
    @POST("lg/user_article/add/json")
    suspend fun shareArticle(@Field("title") title: String, @Field("link") link: String): ResponseResult<String>
}