package com.example.wanandroid.ui.guide.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.wanandroid.R
import com.example.wanandroid.model.Category
import com.example.wanandroid.utils.extension.loadWithDefault
import com.google.android.material.imageview.ShapeableImageView

/**
 * @author: Yang
 * @date: 2023/3/16
 * @description: 教程列表Adapter
 */
class TutorialListAdapter : BaseQuickAdapter<Category, BaseViewHolder>(R.layout.item_tutorial) {

    override fun convert(holder: BaseViewHolder, item: Category) {
        holder.apply {
            //教程名
            setText(R.id.tvTitle, item.name)
            //作者
            setText(R.id.tvAuthor, item.author)
            //描述
            setText(R.id.tvDesc, item.desc)
            //封面
            getView<ShapeableImageView>(R.id.ivCover).loadWithDefault(item.cover)
        }
    }

}