package com.example.wanandroid.view.dialog

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.wanandroid.R

/**
 * @author: Yang
 * @date: 2023/2/16
 * @description: 文章菜单列表Adapter
 */
class ArticleMenuAdapter : BaseQuickAdapter<ArticleMenu, BaseViewHolder>(R.layout.item_article_menu) {

    override fun convert(holder: BaseViewHolder, item: ArticleMenu) {
        holder.apply {
            setImageResource(R.id.iv_menu, item.getIcon())
            setText(R.id.tv_menu, item.getText())
        }
    }

}