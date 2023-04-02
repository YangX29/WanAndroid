package com.example.wanandroid.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * @author: Yang
 * @date: 2023/3/14
 * @description: 网页导航分类
 */
@Parcelize
data class WebCategory(
    @SerializedName("articles")
    val articles: MutableList<Article>,
    @SerializedName("cid")
    val cid: Int,
    @SerializedName("name")
    val name: String
) : TagCategory(), Parcelable {
    override val category: String
        get() = name
    override val tags: MutableList<String>
        get() = articles.map { it.title }.toMutableList()
}
