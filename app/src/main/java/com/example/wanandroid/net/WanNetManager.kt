package com.example.wanandroid.net

import com.example.modele_net.scope_v1.NetManager
import com.example.modele_net.scope_v1.RetrofitClient

/**
 * @author: Yang
 * @date: 2023/1/14
 * @description: WanAndroid网络管理类
 */
object WanNetManager {

    private const val TAG = "WanNetManager"

    //baseUrl
    private const val BASE_URL = "https://www.wanandroid.com"

    //连接超时时长
    private const val CONNECT_TIMEOUT = 5 * 1000L

    //请求超时时长
    private const val CALL_TIMEOUT = 10 * 1000L

    /**
     * 初始化方法
     */
    fun init() {
        //构造client
        val client = RetrofitClient.Builder()
            .baseUrl(BASE_URL)
            .cookieJar(CookieManager.cookieJar)
            .callTimeout(CALL_TIMEOUT)
            .connectTimeout(CONNECT_TIMEOUT)
            .build()
        //初始化
        NetManager.init(client)
    }

}