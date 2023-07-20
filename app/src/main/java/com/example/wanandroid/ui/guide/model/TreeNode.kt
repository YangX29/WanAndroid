package com.example.wanandroid.ui.guide.model

import com.chad.library.adapter.base.entity.node.BaseExpandNode
import com.chad.library.adapter.base.entity.node.BaseNode
import com.example.wanandroid.model.Article
import com.example.wanandroid.model.Category

/**
 * 学习路径章节结点模型
 */
data class TreeGroupNode(
    val content: Category,
    override val childNode: MutableList<BaseNode>
) : BaseExpandNode()

/**
 * 学习路径文章结点
 */
data class TreeArticleNode(
    val article: Article
) : BaseExpandNode() {
    override val childNode = null
}

/**
 * 学习路径列表结点类型
 */
class TreeNodeType {
    companion object {
        const val ITEM_TYPE_GROUP = 1
        const val ITEM_TYPE_ARTICLE = 2
    }
}