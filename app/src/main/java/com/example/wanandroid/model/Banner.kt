package com.example.wanandroid.model

import com.google.gson.annotations.SerializedName

/**
 * @author: Yang
 * @date: 2023/1/7
 * @description: banner数据
 */
data class Banner(
    @SerializedName("id")
    val id: Long = 0,
    @SerializedName("title")
    val title: String,
    @SerializedName("desc")
    val desc: String = "",
    @SerializedName("url")
    val url: String,
    @SerializedName("isVisible")
    val isVisible: Int,
    @SerializedName("imagePath")
    val imagePath: String,
    @SerializedName("type")
    val type: Int,
    @SerializedName("order")
    val order: Int
)
