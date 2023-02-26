package com.example.wanandroid.ui.home

import android.text.Html
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.wanandroid.R
import com.example.wanandroid.model.Article

/**
 * @author: Yang
 * @date: 2023/2/26
 * @description: 文章列表Adapter
 */
class ArticleListAdapter : BaseQuickAdapter<Article, BaseViewHolder>(R.layout.item_article),
    LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: Article) {
        holder.apply {
            //新文章
            setGone(R.id.tvNew, !item.fresh)
            //置顶文章
            setGone(R.id.tvTop, !item.isTop())
            //作者
            setText(R.id.tvAuthor, item.author)
            //时间
            setText(R.id.tvData, item.niceDate)
            //标题
            setText(R.id.tvTitle, Html.fromHtml(item.title))
            //类型
            setText(R.id.tvType, item.articleType())
            //TODO tag
            //收藏
            getView<ImageView>(R.id.ivCollect).isSelected = item.collect
        }
    }

}