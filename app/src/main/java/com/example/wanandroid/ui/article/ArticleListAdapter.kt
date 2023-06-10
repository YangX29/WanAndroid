package com.example.wanandroid.ui.article

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

    companion object {
        const val PAYLOAD_COLLECT = "payload_collect"
    }

    init {
        // 子View点击事件
        addChildClickViewIds(R.id.tvTag1, R.id.tvTag2, R.id.ivCollect)
    }

    override fun convert(holder: BaseViewHolder, item: Article, payloads: List<Any>) {
        super.convert(holder, item, payloads)
        payloads.forEach {
            if (it == PAYLOAD_COLLECT) {
                holder.getView<ImageView>(R.id.ivCollect).isSelected = item.collect
            }
        }
    }

    override fun convert(holder: BaseViewHolder, item: Article) {
        holder.apply {
            //新文章
            setGone(R.id.tvNew, !item.fresh)
            //置顶文章
            setGone(R.id.tvTop, !item.isTop)
            //作者
            setText(R.id.tvAuthor, item.articleAuthor)
            //时间
            setText(R.id.tvData, item.niceDate)
            //标题
            setText(R.id.tvTitle, Html.fromHtml(item.title))
            //类型
            setText(R.id.tvType, item.articleType)
            //收藏
            getView<ImageView>(R.id.ivCollect).isSelected = item.collect
            //标签
            if (item.tags.isNullOrEmpty()) {
                setGone(R.id.tvTag1, true)
                setGone(R.id.tvTag2, true)
            } else {
                setGone(R.id.tvTag1, item.tags.size < 1)
                setGone(R.id.tvTag2, item.tags.size < 2)
                if (item.tags.size >= 1) {
                    setText(R.id.tvTag1, item.tags[0].name)
                }
                if (item.tags.size >= 2) {
                    setText(R.id.tvTag2, item.tags[1].name)
                }
            }
        }
    }

}