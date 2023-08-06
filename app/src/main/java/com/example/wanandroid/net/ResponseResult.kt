package com.example.wanandroid.net

import com.example.modele_net.scope_v1.IResult
import com.google.gson.annotations.SerializedName

/**
 * @author: Yang
 * @date: 2023/1/7
 * @description: WanAndroid响应数据基类
 */
data class ResponseResult<T>(
    @SerializedName("errorCode")
    var errorCode: Int?,
    @SerializedName("errorMsg")
    var errorMsg: String?,
    @SerializedName("data")
    var data: T? = null
) : IResult {

    companion object {
        //登录错误
        const val ERROR_CODE_LOGIN = -1001

        //其他错误
        const val ERROR_CODE_COMMON = -1

        //成功
        const val ERROR_CODE_NONE = 0
    }

    /**
     * 接口调用成功
     */
    fun isSuccess() = errorCode == ERROR_CODE_NONE

    /**
     * 接口调用失败
     */
    fun isFailed() = errorCode != ERROR_CODE_NONE

}