package com.example.wanandroid.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * @author: Yang
 * @date: 2023/3/29
 * @description: 用户信息
 */
@Parcelize
data class UserInfo(
    @SerializedName("admin")
    val admin: Boolean,
    @SerializedName("chapterTops")
    val chapterTops: List<String>,
    @SerializedName("coinCount")
    val coinCount: Int,
    @SerializedName("collectIds")
    val collectIds: MutableList<Int>,
    @SerializedName("email")
    val email: String,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("id")
    val id: Long,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("publicName")
    val publicName: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("token")
    val token: String,
    @SerializedName("type")
    val type: Int
) : Parcelable
