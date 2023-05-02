package com.example.wanandroid.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * @author: Yang
 * @date: 2023/5/2
 * @description: 消息模型
 */
@Parcelize
data class Message(
    @SerializedName("category")
    val category: Int,
    @SerializedName("date")
    val date: Long,
    @SerializedName("fromUser")
    val fromUser: String,
    @SerializedName("fromUserId")
    val fromUserId: Int,
    @SerializedName("fullLink")
    val fullLink: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("isRead")
    val isRead: Int,
    @SerializedName("link")
    val link: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("niceDate")
    val niceDate: String,
    @SerializedName("tag")
    val tag: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("userId")
    val userId: Int
) : Parcelable