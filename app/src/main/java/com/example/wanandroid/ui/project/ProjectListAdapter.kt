package com.example.wanandroid.ui.project

import android.text.Html
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.wanandroid.R
import com.example.wanandroid.model.Article
import com.example.wanandroid.utils.extension.loadWithDefault
import com.google.android.material.imageview.ShapeableImageView

/**
 * @author: Yang
 * @date: 2023/3/7
 * @description: 项目列表Adapter
 */
class ProjectListAdapter : BaseQuickAdapter<Article, BaseViewHolder>(R.layout.item_project),
    LoadMoreModule {

    init {
        addChildClickViewIds(R.id.ivCollect)
    }

    override fun convert(holder: BaseViewHolder, item: Article) {
        holder.apply {
            //项目图片
            getView<ShapeableImageView>(R.id.ivProject).loadWithDefault(item.envelopePic ?: "")
            //作者
            setText(R.id.tvAuthor, item.articleAuthor)
            //时间
            setText(R.id.tvData, item.niceDate)
            //标题
            setText(R.id.tvTitle, Html.fromHtml(item.title))
            //文章描述
            setText(R.id.tvDes, Html.fromHtml(item.desc))
            //收藏
            getView<ImageView>(R.id.ivCollect).isSelected = item.collect
        }
    }
}