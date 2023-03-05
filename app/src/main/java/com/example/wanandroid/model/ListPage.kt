package com.example.wanandroid.model

import com.google.gson.annotations.SerializedName

/**
 * @author: Yang
 * @date: 2023/2/26
 * @description: 数据列表基类
 */
class ListPage<T : Any>(
    @SerializedName("curPage")
    val curPage: Int,
    @SerializedName("datas")
    val list: MutableList<T>,
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("over")
    val over: Boolean,
    @SerializedName("pageCount")
    val pageCount: Int,
    @SerializedName("size")
    val size: Int,
    @SerializedName("total")
    val total: Int
)
