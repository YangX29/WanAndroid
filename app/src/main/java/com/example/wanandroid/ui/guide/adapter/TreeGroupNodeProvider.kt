package com.example.wanandroid.ui.guide.adapter

import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.entity.node.BaseNode
import com.chad.library.adapter.base.provider.BaseNodeProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.wanandroid.R
import com.example.wanandroid.ui.guide.model.TreeGroupNode
import com.example.wanandroid.ui.guide.model.TreeNodeType

/**
 * @author: Yang
 * @date: 2023/7/19
 * @description: 学习路径章节列表provider
 */
class TreeGroupNodeProvider : BaseNodeProvider() {

    override val itemViewType: Int
        get() = TreeNodeType.ITEM_TYPE_GROUP

    override val layoutId: Int
        get() = R.layout.item_tree_group

    override fun convert(helper: BaseViewHolder, item: BaseNode) {
        (item as? TreeGroupNode)?.let {
            helper.apply {
                //标题
                setText(R.id.tvTitle, it.content.name)
                //图标
                getView<ImageView>(R.id.ivFold).isSelected = it.isExpanded
            }
        }
    }

    override fun onClick(helper: BaseViewHolder, view: View, data: BaseNode, position: Int) {
        //TODO parentPayload，箭头旋转动画
        getAdapter()?.expandOrCollapse(position,
            animate = true,
            notify = true
        );
    }
}