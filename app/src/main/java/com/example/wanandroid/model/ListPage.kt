package com.example.wanandroid.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * @author: Yang
 * @date: 2023/2/26
 * @description: 数据列表基类
 */
@Parcelize
class ListPage<T : Parcelable>(
    @SerializedName("curPage")
    val curPage: Int,
    @SerializedName("datas")
    val list: MutableList<T>,
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("over")
    val over: Boolean,
    @SerializedName("pageCount")
    val pageCount: Int,
    @SerializedName("size")
    val size: Int,
    @SerializedName("total")
    val total: Int
) : Parcelable {
    val isFinish: Boolean
        get() = (curPage + 1) >= pageCount
}
