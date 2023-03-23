package com.example.wanandroid.ui.search

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.wanandroid.R

/**
 * @author: Yang
 * @date: 2023/3/22
 * @description: 搜索关键词列表adapter
 */
class SearchKeyAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_tag) {

    override fun convert(holder: BaseViewHolder, item: String) {
        holder.apply {
            setText(R.id.tv_name, item)
        }
    }

}