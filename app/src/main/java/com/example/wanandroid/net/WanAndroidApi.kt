package com.example.wanandroid.net

import com.example.wanandroid.model.Banner
import retrofit2.http.GET

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

    @GET("banner/json")
    suspend fun getBanner(): ResponseResult<List<Banner>>

}