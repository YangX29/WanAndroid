package com.example.wanandroid.model

import com.google.gson.annotations.SerializedName

/**
 * @author: Yang
 * @date: 2023/3/14
 * @description: 网页导航分类
 */
data class WebCategory(
    @SerializedName("articles")
    val articles: MutableList<Article>,
    @SerializedName("cid")
    val cid: Int,
    @SerializedName("name")
    val name: String
) : TagCategory() {
    override val category: String
        get() = name
    override val tags: MutableList<String>
        get() = articles.map { it.title }.toMutableList()
}
