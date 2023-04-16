package com.example.wanandroid.mvi.coin

import com.example.wanandroid.model.CoinInfo
import com.example.wanandroid.mvi.list.SimpleListViewModel

/**
 * @author: Yang
 * @date: 2023/4/16
 * @description: 积分排名ViewModel
 */
class RankViewModel : SimpleListViewModel<CoinInfo>() {

    override suspend fun getList() = apiService.getCoinRank(page?.page ?: 1)

}