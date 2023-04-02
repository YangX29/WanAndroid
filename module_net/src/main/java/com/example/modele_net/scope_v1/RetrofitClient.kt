package com.example.modele_net.scope_v1

import com.example.modele_net.common.Constants
import com.example.modele_net.common.interceptor.LogInterceptor
import okhttp3.CookieJar
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author: Yang
 * @date: 2023/1/7
 * @description: Retrofit客户端，实际接口调用类
 */
class RetrofitClient private constructor(
    baseUrl: String?,
    connectTimeout: Long,
    callTimeout: Long,
    interceptors: List<Interceptor>,
    networkInterceptors: List<Interceptor>,
    cookieJar: CookieJar,
    private val netErrorHandleDelegate: NetErrorHandleDelegate?,
) {

    private var retrofit: Retrofit
    private var okHttpClient: OkHttpClient

    init {
        // 构造okhttpClient
        val logInterceptor = LogInterceptor()
        val okHttpClientBuilder = OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .connectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
            .callTimeout(callTimeout, TimeUnit.MILLISECONDS)
            .addInterceptor(logInterceptor)
            .cookieJar(cookieJar)
        interceptors.forEach { okHttpClientBuilder.addInterceptor(it) }
        networkInterceptors.forEach { okHttpClientBuilder.addNetworkInterceptor(it) }
        // TODO 配置缓存策略
        okHttpClient = okHttpClientBuilder.build()
        // 构造retrofit
        val retrofitBuilder = Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        baseUrl?.let { retrofitBuilder.baseUrl(it) }
        retrofit = retrofitBuilder.build()
    }

    /**
     * 获取接口服务对象
     */
    fun <T> api(service: Class<T>): T {
        return retrofit.create(service)
    }

    /**
     * 获取错误处理委托对象
     */
    fun errorHandler(): NetErrorHandleDelegate? {
        return netErrorHandleDelegate
    }

    class Builder {

        /**
         * 接口baseUrl
         */
        private var baseUrl: String? = null

        /**
         * 拦截器
         */
        private var interceptors = mutableListOf<Interceptor>()

        /**
         * 网络拦截器
         */
        private var networkInterceptors = mutableListOf<Interceptor>()

        /**
         * 网络错误处理委托
         */
        private var netErrorHandleDelegate: NetErrorHandleDelegate? = null

        /**
         * 连接超时时长
         */
        private var connectTimeout = Constants.CONNECT_TIMEOUT

        /**
         * 请求超时时长
         */
        private var callTimeout = Constants.CALL_TIMEOUT

        /**
         * cookieJar
         */
        private var cookieJar = CookieJar.NO_COOKIES

        /**
         * 设置baseUrl
         */
        fun baseUrl(url: String): Builder {
            baseUrl = url
            return this
        }

        /**
         * 设置CookieJar
         */
        fun cookieJar(cookieJar: CookieJar): Builder {
            this.cookieJar = cookieJar
            return this
        }

        /**
         * 设置连接超时时长
         */
        fun connectTimeout(time: Long): Builder {
            connectTimeout = time
            return this
        }

        /**
         * 设置请求超时时长
         */
        fun callTimeout(time: Long): Builder {
            callTimeout = time
            return this
        }

        /**
         * 添加拦截器
         */
        fun addInterceptor(interceptor: Interceptor): Builder {
            interceptors.add(interceptor)
            return this
        }

        /**
         * 添加网络拦截器
         */
        fun addNetworkInterceptor(interceptor: Interceptor): Builder {
            interceptors.add(interceptor)
            return this
        }

        /**
         * 设置错误处理器
         */
        fun errorHandler(handler: NetErrorHandleDelegate): Builder {
            netErrorHandleDelegate = handler
            return this
        }


        /**
         * 构造方法
         */
        fun build(): RetrofitClient {
            return RetrofitClient(
                baseUrl,
                connectTimeout,
                callTimeout,
                interceptors,
                networkInterceptors,
                cookieJar,
                netErrorHandleDelegate
            )
        }
    }

}