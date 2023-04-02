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

    val cookieJar by lazy {
        PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(WanApplication.context))
    }

    /**
     * 清除cookie
     */
    fun clear() {
        cookieJar.clear()
    }

}