package com.example.modele_net.common.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author: Yang
 * @date: 2023/2/11
 * @description: 接口公共header拦截器
 */
open class CommonHeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        //添加header
        getHeaders().forEach {
            builder.addHeader(it.key, it.value)
        }
        return chain.proceed(builder.build())
    }

    /**
     * 获取公共header
     */
    protected fun getHeaders(): Map<String, String> {
        return mapOf()
    }

}