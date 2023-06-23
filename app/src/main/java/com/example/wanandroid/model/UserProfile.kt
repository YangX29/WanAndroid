package com.example.wanandroid.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * @author: Yang
 * @date: 2023/4/24
 * @description: 用户信息
 */
@Parcelize
data class UserProfile(
    @SerializedName("coinInfo")
    val coinInfo: CoinInfo,
    @SerializedName("userInfo")
    val userInfo: UserInfo
) : Parcelable
