package com.example.wanandroid.ui.guide.adapter

import com.chad.library.adapter.base.BaseNodeAdapter
import com.chad.library.adapter.base.entity.node.BaseNode
import com.example.wanandroid.ui.guide.model.TreeGroupNode
import com.example.wanandroid.ui.guide.model.TreeNodeType

/**
 * @author: Yang
 * @date: 2023/7/17
 * @description: 学习路径列表Adapter
 */
class TreeNodeAdapter : BaseNodeAdapter() {

    init {
        addNodeProvider(TreeGroupNodeProvider())
        addNodeProvider(TreeArticleNodeProvider())
    }

    override fun getItemType(data: List<BaseNode>, position: Int): Int {
        return when (data[position]) {
            is TreeGroupNode -> TreeNodeType.ITEM_TYPE_GROUP
            else -> TreeNodeType.ITEM_TYPE_ARTICLE
        }
    }
}