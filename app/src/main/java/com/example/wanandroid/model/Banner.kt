package com.example.wanandroid.model

import com.google.gson.annotations.SerializedName

/**
 * @author: Yang
 * @date: 2023/1/7
 * @description: banner数据
 */
data class Banner(
    @SerializedName("id")
    var id: Long = 0,
    @SerializedName("title")
    var title: String = "",
    @SerializedName("desc")
    var desc: String = "",
    @SerializedName("url")
    var url: String = ""
)
