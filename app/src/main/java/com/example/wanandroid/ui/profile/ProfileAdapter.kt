package com.example.wanandroid.ui.profile

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.wanandroid.R

/**
 * @author: Yang
 * @date: 2023/4/24
 * @description: 用户信息列表Adapter
 */
class ProfileAdapter :
    BaseQuickAdapter<ProfileAdapter.Item, BaseViewHolder>(R.layout.item_profile) {

    data class Item(
        val title: String,
        val msg: String
    )

    override fun convert(holder: BaseViewHolder, item: Item) {
        holder.apply {
            setText(R.id.tvTitle, item.title)
            setText(R.id.tvMsg, item.msg)
        }
    }


}