package com.example.wanandroid.mvi.coin

import com.example.wanandroid.model.CoinHistory
import com.example.wanandroid.model.CoinInfo
import com.example.wanandroid.mvi.list.ListPageViewState
import com.example.wanandroid.mvi.list.ListPageViewStatus

/**
 * @author: Yang
 * @date: 2023/4/16
 * @description: 我的积分ViewState
 */
data class MineCoinViewState(
    override val status: ListPageViewStatus,
    val historyList: MutableList<CoinHistory>,
    val coinInfo: CoinInfo? = null,
) : ListPageViewState(status) {
}