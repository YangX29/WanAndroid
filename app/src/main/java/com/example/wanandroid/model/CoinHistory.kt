package com.example.wanandroid.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * @author: Yang
 * @date: 2023/4/16
 * @description: 获取积分历史记录
 */
@Parcelize
data class CoinHistory(
    @SerializedName("coinCount")
    val coinCount: Int,
    @SerializedName("date")
    val date: Long,
    @SerializedName("desc")
    val desc: String,
    @SerializedName("id")
    val id: Long,
    @SerializedName("reason")
    val reason: String,
    @SerializedName("type")
    val type: Int,
    @SerializedName("userId")
    val userId: Long,
    @SerializedName("userName")
    val userName: String,
) : Parcelable
