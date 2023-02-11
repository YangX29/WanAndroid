package com.example.modele_net.common.interceptor

import okhttp3.*

/**
 * @author: Yang
 * @date: 2023/2/7
 * @description: 公共参数拦截器，添加公共参数
 */
open class CommonParamsInterceptor : Interceptor {

    companion object {
        private const val METHOD_GET = "GET"
        private const val METHOD_POST = "POST"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        //添加公共参数
        val newRequest = addParams(request)
        return chain.proceed(newRequest)
    }

    /**
     * 添加公共参数
     */
    private fun addParams(request: Request): Request {
        return when (request.method.uppercase()) {
            METHOD_GET -> addParamsToGET(request)
            METHOD_POST -> addParamsToPost(request)
            else -> request
        }
    }

    /**
     * 向GET方法添加参数
     */
    private fun addParamsToGET(request: Request): Request {
        //获取参数
        val params = getMethodGetParams()
        //添加参数
        val builder = request.url.newBuilder()
        params.forEach {
            builder.addQueryParameter(it.key, it.value)
        }
        //构造新请求
        return request.newBuilder().url(builder.build()).build()
    }

    /**
     * 向POST方法添加参数
     */
    private fun addParamsToPost(request: Request): Request {
        //获取参数
        val params = getMethodPostParams()
        //获取body
        val requestBody = request.body ?: return request
        //处理公共参数
        when (requestBody) {
            is MultipartBody -> {//文件上传不做处理
                return request
            }
            is FormBody -> {//表单
                val builder = FormBody.Builder()
                //添加原有参数
                for (index in 0..requestBody.size) {
                    builder.add(requestBody.name(index), requestBody.value(index))
                }
                //添加公共参数
                params.forEach {
                    builder.add(it.key, it.value)
                }
                //构造新请求
                return request.newBuilder().post(builder.build()).build()
            }
            else -> {
                val builder = FormBody.Builder()
                return if (requestBody.contentLength() == 0L) {//无参数
                    //添加公共参数
                    params.forEach {
                        builder.add(it.key, it.value)
                    }
                    //构造新请求
                    request.newBuilder().post(builder.build()).build()
                } else {
                    request
                }
            }
        }
    }

    /**
     * 通用公共参数
     */
    protected fun getCommonParams(): Map<String, Any> {
        return mapOf()
    }

    /**
     * GET方法公共参数
     */
    protected fun getMethodGetParams(): Map<String, String> {
        return mapOf()
    }

    /**
     * POST方法公共参数
     */
    protected fun getMethodPostParams(): Map<String, String> {
        return mapOf()
    }


}