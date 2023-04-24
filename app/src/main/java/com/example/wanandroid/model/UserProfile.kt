package com.example.wanandroid.model

import com.google.gson.annotations.SerializedName

/**
 * @author: Yang
 * @date: 2023/4/24
 * @description: 用户信息
 */
data class UserProfile(
    @SerializedName("coinInfo")
    val coinInfo: CoinInfo,
    @SerializedName("userInfo")
    val userInfo: UserInfo
)
