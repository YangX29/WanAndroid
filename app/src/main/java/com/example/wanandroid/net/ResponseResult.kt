package com.example.wanandroid.net

import com.example.modele_net.scope_v1.IResult
import com.google.gson.annotations.SerializedName

/**
 * @author: Yang
 * @date: 2023/1/7
 * @description: WanAndroid响应数据基类
 */
data class ResponseResult<T> (
    @SerializedName("errorCode")
    var errorCode: Int?,
    @SerializedName("errorMsg")
    var errorMsg: String? = "",
    @SerializedName("data")
    var data: T? = null
) : IResult