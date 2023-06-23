package com.example.wanandroid.net

import com.example.wanandroid.app.WanApplication
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor

/**
 * @author: Yang
 * @date: 2023/4/2
 * @description: cookie管理类
 */
object CookieManager {

    private val cookiePersistor by lazy { SharedPrefsCookiePersistor(WanApplication.context) }
    val cookieJar by lazy {
        PersistentCookieJar(SetCookieCache(), cookiePersistor)
    }


    /**
     * 清除cookie
     */
    fun clear() {
        cookieJar.clear()
    }

    /**
     * 检查登录状态，cookie是否过期
     */
    suspend fun loginExpired(): Boolean {
        val expiresCookie =
            cookiePersistor.loadAll().filter { it.expiresAt < System.currentTimeMillis() }
        return expiresCookie.isNotEmpty()
    }

}