package com.example.wanandroid.model

import com.google.gson.annotations.SerializedName

/**
 * @author: Yang
 * @date: 2023/3/22
 * @description: 搜索热词
 */
data class HotKey(
    @SerializedName("id")
    val id: Int,
    @SerializedName("link")
    val link: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("order")
    val order: Int,
    @SerializedName("visible")
    val visible: Int
)
