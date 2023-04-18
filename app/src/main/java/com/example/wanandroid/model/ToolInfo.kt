package com.example.wanandroid.model

import android.os.Parcelable
import com.example.wanandroid.common.Constants
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * @author: Yang
 * @date: 2023/4/18
 * @description: 工具信息
 */
@Parcelize
data class ToolInfo(
    @SerializedName("desc")
    val desc: String,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("isNew")
    val isNew: Int,
    @SerializedName("link")
    val link: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("order")
    val order: Int,
    @SerializedName("showInTab")
    val showInTab: Int,
    @SerializedName("tabName")
    val tabName: String,
    @SerializedName("visible")
    val visible: Int
) : Parcelable {
    //图片链接
    val imageUrl: String
        get() = "${Constants.HOST_TOOL_IMG}${icon}"
}