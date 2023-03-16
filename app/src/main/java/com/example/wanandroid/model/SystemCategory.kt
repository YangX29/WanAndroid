package com.example.wanandroid.model

import com.google.gson.annotations.SerializedName

/**
 * @author: Yang
 * @date: 2023/3/16
 * @description: 体系分类
 */
data class SystemCategory(
    @SerializedName("articleList")
    val articleList: MutableList<Article>,
    @SerializedName("author")
    val author: String,
    @SerializedName("children")
    val children: MutableList<SystemCategory>,
    @SerializedName("courseId")
    val courseId: Int,
    @SerializedName("cover")
    val cover: String,
    @SerializedName("desc")
    val desc: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("lisense")
    val lisense: String,
    @SerializedName("lisenseLink")
    val lisenseLink: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("order")
    val order: Int,
    @SerializedName("parentChapterId")
    val parentChapterId: Int,
    @SerializedName("type")
    val type: Int,
    @SerializedName("userControlSetTop")
    val userControlSetTop: Boolean,
    @SerializedName("visible")
    val visible: Int
) : TagCategory() {
    override val category: String
        get() = name
    override val tags: MutableList<String>
        get() = children.map { it.name }.toMutableList()
}
