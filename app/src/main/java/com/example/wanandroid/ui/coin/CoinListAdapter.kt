package com.example.wanandroid.ui.coin

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.wanandroid.R
import com.example.wanandroid.model.CoinHistory

/**
 * @author: Yang
 * @date: 2023/4/16
 * @description: 我的积分列表adapter
 */
class CoinListAdapter : BaseQuickAdapter<CoinHistory, BaseViewHolder>(R.layout.item_coin),
    LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: CoinHistory) {
        holder.apply {
            //类型
            setText(R.id.tvReason, item.reason)
            //描述
            setText(R.id.tvDesc, item.desc.replace("${item.reason} ,", ""))
            //积分
            setText(R.id.tvCoin, "+${item.coinCount}")
        }
    }

}