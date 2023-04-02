package com.example.wanandroid.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * @author: Yang
 * @date: 2023/4/2
 * @description: 积分信息
 */
@Parcelize
data class CoinInfo(
    @SerializedName("coinCount")
    val coinCount: Int,
    @SerializedName("level")
    val level: Int,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("rank")
    val rank: Int,
    @SerializedName("userId")
    val userId: Long,
    @SerializedName("username")
    val username: String
) : Parcelable
