package com.example.wanandroid.ui.guide.adapter

import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.entity.node.BaseNode
import com.chad.library.adapter.base.provider.BaseNodeProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.wanandroid.R
import com.example.wanandroid.common.RoutePath
import com.example.wanandroid.ui.guide.model.TreeArticleNode
import com.example.wanandroid.ui.guide.model.TreeNodeType
import com.example.wanandroid.ui.web.WebActivity

/**
 * @author: Yang
 * @date: 2023/7/19
 * @description: 学习路径文章列表provider
 */
class TreeArticleNodeProvider : BaseNodeProvider() {

    override val itemViewType: Int
        get() = TreeNodeType.ITEM_TYPE_ARTICLE

    override val layoutId: Int
        get() = R.layout.item_tree_child

    override fun convert(helper: BaseViewHolder, item: BaseNode) {
        (item as? TreeArticleNode)?.let {
            helper.apply {
                //标题
                setText(R.id.tvTitle, it.article.title)
                //分享人
                setText(
                    R.id.tvShare,
                    it.article.shareUser?.ifEmpty { it.article.author } ?: it.article.author)
                //时间
                setText(R.id.tvDate, it.article.niceDate)
            }
        }
    }

    override fun onClick(helper: BaseViewHolder, view: View, data: BaseNode, position: Int) {
        val article = (data as? TreeArticleNode)?.article ?: return
        ARouter.getInstance().build(RoutePath.WEB)
            .withString(WebActivity.WEB_URL, article.link)
            .navigation()
    }
}