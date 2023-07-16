package com.example.wanandroid.ui.guide.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.module_common.utils.extension.dp2px
import com.example.module_common.utils.extension.getColorRes
import com.example.wanandroid.R
import com.example.wanandroid.model.Category
import com.example.wanandroid.utils.extension.loadWithDefault
import com.example.wanandroid.view.widget.roundDrawable
import com.google.android.material.imageview.ShapeableImageView

/**
 * @author: Yang
 * @date: 2023/3/16
 * @description: 学习路径Adapter
 */
class TreeListAdapter : BaseQuickAdapter<Category, BaseViewHolder>(R.layout.item_tree) {

    init {
        addChildClickViewIds(R.id.btFollow)
    }

    override fun convert(holder: BaseViewHolder, item: Category) {
        holder.apply {
            //教程名
            setText(R.id.tvTitle, item.name)
            //描述
            setText(R.id.tvDesc, item.desc)
            //封面
            getView<ShapeableImageView>(R.id.ivCover).loadWithDefault(item.cover)
            //关注按钮
            getView<TextView>(R.id.btFollow).let {
                it.background = roundDrawable(
                    10.dp2px().toFloat(),
                    normalColor = getColorRes(R.color.common_background),
                    selectedColor = getColorRes(R.color.common_tag_bg)
                )
                //TODO 是否关注
                it.isSelected = false
            }
        }
    }

}