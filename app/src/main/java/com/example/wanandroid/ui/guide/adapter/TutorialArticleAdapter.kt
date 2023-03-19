package com.example.wanandroid.ui.guide.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.wanandroid.R
import com.example.wanandroid.model.Article

/**
 * @author: Yang
 * @date: 2023/3/19
 * @description: 课程文章列表Adapter
 */
class TutorialArticleAdapter :
    BaseQuickAdapter<Article, BaseViewHolder>(R.layout.item_tutorial_article), LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: Article) {
        holder.apply {
            setText(R.id.tvTitle, "${holder.adapterPosition+1}.${item.title}")
            setText(R.id.tvDate, item.niceDate)
            //TODO
            setText(R.id.tvState, R.string.tutorial_state_not_learn)
        }
    }
}