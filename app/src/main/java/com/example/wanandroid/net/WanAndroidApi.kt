package com.example.wanandroid.net

import com.example.wanandroid.model.*
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
    suspend fun getOfficialArticleList(
        @Path("id") id: Int, @Path("page") page: Int
    ): ResponseResult<ListPage<Article>>

    /**
     * 获取项目分类
     */
    @GET("project/tree/json")
    suspend fun getProjectCategories(): ResponseResult<MutableList<Category>>

    /**
     * 项目文章列表
     */
    @GET("project/list/{page}/json")
    suspend fun getProjectArticleList(
        @Path("page") page: Int, @Query("cid") cid: Int
    ): ResponseResult<ListPage<Article>>

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
    suspend fun shareArticle(
        @Field("title") title: String, @Field("link") link: String
    ): ResponseResult<String>

    /**
     * 网页导航
     */
    @GET("navi/json")
    suspend fun getWebList(): ResponseResult<MutableList<WebCategory>>

    /**
     * 体系数据
     */
    @GET("tree/json")
    suspend fun getSystemCategory(): ResponseResult<MutableList<Category>>

    /**
     * 教程列表
     */
    @GET("chapter/547/sublist/json")
    suspend fun getTutorialList(): ResponseResult<MutableList<Category>>

    /**
     * 二级文章列表
     */
    @GET("article/list/{page}/json")
    suspend fun getSubArticleList(
        @Path("page") page: Int, @Query("cid") cid: Int, @Query("order_type") orderType: Int? = null
    ): ResponseResult<ListPage<Article>>

    /**
     * 获取搜索热词
     */
    @GET("hotkey/json")
    suspend fun getSearchHotKey(): ResponseResult<MutableList<HotKey>>

    /**
     * 搜索
     */
    @FormUrlEncoded
    @POST("article/query/{page}/json")
    suspend fun search(
        @Path("page") page: Int, @Field("k") key: String
    ): ResponseResult<ListPage<Article>>

    /**
     * 我的分享
     */
    @GET("user/lg/private_articles/{page}/json")
    suspend fun shareList(@Path("page") page: Int): ResponseResult<UserShareInfo>

    /**
     * 我的收藏
     */
    @GET("lg/collect/list/{page}/json")
    suspend fun collectionList(@Path("page") page: Int): ResponseResult<ListPage<Article>>

    /**
     * 积分信息
     */
    @GET("lg/coin/userinfo/json")
    suspend fun getCoinInfo(): ResponseResult<CoinInfo>

    /**
     * 积分历史
     */
    @GET("lg/coin/list/{page}/json")
    suspend fun getCoinHistoryList(@Path("page") page: Int): ResponseResult<ListPage<CoinHistory>>

    /**
     * 积分排行
     */
    @GET("coin/rank/{page}/json")
    suspend fun getCoinRank(@Path("page") page: Int): ResponseResult<ListPage<CoinInfo>>


    /**
     * 获取工具列表
     */
    @GET("tools/list/json")
    suspend fun getToolList(): ResponseResult<MutableList<ToolInfo>>

    /**
     * 用户信息
     */
    @GET("user/{id}/share_articles/{page}/json")
    suspend fun getUserPage(
        @Path("id") id: Long, @Path("page") page: Int
    ): ResponseResult<UserShareInfo>

    /**
     * 用户信息
     */
    @GET("user/lg/userinfo/json")
    suspend fun getUserInfo(): ResponseResult<UserProfile>

    /**
     * 未读信息数量
     */
    @GET("message/lg/count_unread/json")
    suspend fun getUnreadMsgCount(): ResponseResult<Int>

    /**
     * 信息列表
     */
    @GET("message/lg/{type}/{page}/json")
    suspend fun getMessageList(
        @Path("type") type: String,
        @Path("page") page: Int
    ): ResponseResult<ListPage<Message>>

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("username") username: String, @Field("password") password: String
    ): ResponseResult<UserInfo>

    /**
     * 注册
     */
    @FormUrlEncoded
    @POST("user/register")
    suspend fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("repassword") repassword: String
    ): ResponseResult<UserInfo>

    /**
     * 退出登录
     */
    @GET("user/logout/json")
    suspend fun logout(): ResponseResult<Any>
}