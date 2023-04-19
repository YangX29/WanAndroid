package com.example.wanandroid.mvi.user

import com.example.wanandroid.model.Article
import com.example.wanandroid.model.CoinInfo
import com.example.wanandroid.mvi.list.ListPageViewState
import com.example.wanandroid.mvi.list.ListPageViewStatus

/**
 * @author: Yang
 * @date: 2023/4/19
 * @description: 用户主页ViewState
 */
data class UserPageViewState(
    override val status: ListPageViewStatus,
    val coinInfo: CoinInfo? = null,
    val data: MutableList<Article>? = null
) : ListPageViewState(status)