package com.example.wanandroid.ui.coin

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.wanandroid.R
import com.example.wanandroid.model.CoinInfo

/**
 * @author: Yang
 * @date: 2023/4/16
 * @description: 积分排名列表Adapter
 */
class RankListAdapter : BaseQuickAdapter<CoinInfo, BaseViewHolder>(R.layout.item_rank),
    LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: CoinInfo) {
        holder.apply {
            //排名
            setText(R.id.tvRank, "${item.rank}")
            //用户名
            setText(R.id.tvName, item.username)
            //积分
            setText(R.id.tvCoin, "${item.coinCount}")
        }
    }

}