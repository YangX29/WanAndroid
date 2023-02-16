package com.example.wanandroid.view.dialog

import android.content.Context
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.QuickViewHolder
import com.example.wanandroid.R

/**
 * @author: Yang
 * @date: 2023/2/16
 * @description: 文章菜单列表Adapter
 */
class ArticleMenuAdapter : BaseQuickAdapter<ArticleMenu, QuickViewHolder>() {

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): QuickViewHolder {
        return QuickViewHolder(R.layout.item_article_menu, parent)
    }

    override fun onBindViewHolder(holder: QuickViewHolder, position: Int, item: ArticleMenu?) {
        if (item == null) return
        holder.apply {
            setImageResource(R.id.iv_menu, item.getIcon())
            setText(R.id.tv_menu, item.getText())
        }
    }

}