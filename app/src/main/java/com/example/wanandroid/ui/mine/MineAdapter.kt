package com.example.wanandroid.ui.mine

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.wanandroid.R

/**
 * @author: Yang
 * @date: 2023/4/12
 * @description: 我的页面列表Adapter
 */
class MineAdapter :
    BaseQuickAdapter<MineAdapter.MineItem, BaseViewHolder>(R.layout.item_mine_function) {

    enum class MineItemType {
        MIME_COIN, MINE_SHARE, MINE_COLLECTION, MINE_HISTORY, MINE_TODO, MINE_TOOL, MINE_SETTING
    }

    data class MineItem(
        val type: MineItemType,
        @DrawableRes val icon: Int,
        @StringRes val title: Int,
        val desc: String? = null
    )

    override fun convert(holder: BaseViewHolder, item: MineItem) {
        holder.apply {
            //图标
            setImageResource(R.id.ivIcon, item.icon)
            //标题
            setText(R.id.tvTitle, item.title)
            //描述
            setText(R.id.tvDesc, item.desc ?: "")
        }
    }

}