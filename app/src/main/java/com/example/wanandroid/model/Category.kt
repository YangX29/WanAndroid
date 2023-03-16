package com.example.wanandroid.model

import com.google.gson.annotations.SerializedName

/**
 * @author: Yang
 * @date: 2023/3/5
 * @description: 文章分类
 */
data class Category(
    @SerializedName("articleList")
    val articleList: MutableList<Article>,
    @SerializedName("author")
    val author: String?,
    @SerializedName("children")
    val children: MutableList<Category>,
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
    companion object {
        const val TAB_ID_NEWEST_PROJECT = -1
        const val TAB_NAME_NEWEST_PROJECT = "最新项目"
    }

    override val category: String
        get() = name
    override val tags: MutableList<String>
        get() = children.map { it.name }.toMutableList()
}