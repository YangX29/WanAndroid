package com.example.wanandroid.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * @author: Yang
 * @date: 2023/4/2
 * @description: 我的分享列表
 */
@Parcelize
data class ShareList(
    @SerializedName("coinInfo")
    val coinInfo: CoinInfo,
    @SerializedName("shareArticles")
    val shareArticles: ListPage<Article>
) : Parcelable