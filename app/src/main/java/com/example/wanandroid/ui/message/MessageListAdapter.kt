package com.example.wanandroid.ui.message

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.wanandroid.R
import com.example.wanandroid.model.Message

/**
 * @author: Yang
 * @date: 2023/5/2
 * @description: 消息列表Adapter
 */
class MessageListAdapter : BaseQuickAdapter<Message, BaseViewHolder>(R.layout.item_message),
    LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: Message) {
        holder.apply {
            //标签
            setText(R.id.tvTag, item.tag)
            //来源
            setText(R.id.tvAuthor, item.fromUser)
            //时间
            setText(R.id.tvDate, item.niceDate)
            //标题
            setText(R.id.tvTitle, item.title)
            //消息
            setText(R.id.tvMsg, item.message)
        }
    }

}