package com.example.wanandroid.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * @author: Yang
 * @date: 2023/2/26
 * @description: Article数据
 */
@Parcelize
data class Article(
    @SerializedName("adminAdd")
    val adminAdd: Boolean,
    @SerializedName("apkLink")
    val apkLink: String?,
    @SerializedName("audit")
    val audit: Int,
    @SerializedName("author")
    val author: String,
    @SerializedName("canEdit")
    val canEdit: Boolean,
    @SerializedName("chapterId")
    val chapterId: Int,
    @SerializedName("chapterName")
    val chapterName: String,
    @SerializedName("collect")
    var collect: Boolean,
    @SerializedName("courseId")
    val courseId: Int,
    @SerializedName("desc")
    val desc: String?,
    @SerializedName("descMd")
    val descMd: String?,
    @SerializedName("envelopePic")
    val envelopePic: String?,
    @SerializedName("fresh")
    val fresh: Boolean,
    @SerializedName("host")
    val host: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("isAdminAdd")
    val isAdminAdd: Boolean?,
    @SerializedName("link")
    val link: String,
    @SerializedName("niceDate")
    val niceDate: String,
    @SerializedName("niceShareDate")
    val niceShareDate: String,
    @SerializedName("origin")
    val origin: String?,
    @SerializedName("prefix")
    val prefix: String?,
    @SerializedName("projectLink")
    val projectLink: String?,
    @SerializedName("publishTime")
    val publishTime: Long,
    @SerializedName("realSuperChapterId")
    val realSuperChapterId: Int,
    @SerializedName("route")
    val route: Boolean,
    @SerializedName("selfVisible")
    val selfVisible: Int,
    @SerializedName("shareDate")
    val shareDate: Long,
    @SerializedName("shareUser")
    val shareUser: String?,
    @SerializedName("superChapterId")
    val superChapterId: Int,
    @SerializedName("superChapterName")
    val superChapterName: String,
    @SerializedName("tags")
    val tags: MutableList<Tag>,
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: Int,
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("visible")
    val visible: Int,
    @SerializedName("zan")
    val zan: Int
) : Parcelable {
    //是否为置顶
    val isTop: Boolean
        get() = type == 1

    //文章类型
    val articleType: String
        get() = "${superChapterName}·${chapterName}"

    //文章作者
    val articleAuthor: String
        get() = author.ifEmpty { shareUser ?: "" }
}

/**
 * @author: Yang
 * @date: 2023/2/26
 * @description: 文章标签
 */
@Parcelize
data class Tag(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
) : Parcelable