package com.example.wanandroid.ui.tool

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.wanandroid.R
import com.example.wanandroid.model.ToolInfo
import com.example.wanandroid.utils.extension.loadWithDefault

/**
 * @author: Yang
 * @date: 2023/4/18
 * @description: 工具列表Adapter
 */
class ToolAdapter : BaseQuickAdapter<ToolInfo, BaseViewHolder>(R.layout.item_tool) {

    override fun convert(holder: BaseViewHolder, item: ToolInfo) {
        holder.apply {
            //图片
            getView<ImageView>(R.id.ivIcon).loadWithDefault(item.imageUrl)
            //名称
            setText(R.id.tvName, item.name)
            //描述
            setText(R.id.tvDesc, item.desc)
        }
    }

}