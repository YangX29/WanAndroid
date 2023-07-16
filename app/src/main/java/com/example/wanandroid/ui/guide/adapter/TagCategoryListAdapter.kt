package com.example.wanandroid.ui.guide.adapter

import android.view.LayoutInflater
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.wanandroid.R
import com.example.wanandroid.model.TagCategory
import com.google.android.flexbox.FlexboxLayout

/**
 * @author: Yang
 * @date: 2023/3/15
 * @description: 标签分类列表Adapter
 */
class TagCategoryListAdapter<T : TagCategory> :
    BaseQuickAdapter<T, BaseViewHolder>(R.layout.item_tag_category) {

    private var tagClickListener: ((T, Int) -> Unit)? = null

    override fun convert(holder: BaseViewHolder, item: T) {
        holder.apply {
            //分类标题
            setText(R.id.tvTitle, item.category)
            //标签
            val flexboxLayout = getView<FlexboxLayout>(R.id.fblTag)
            //移除原有标签，防止复用导致分类tag混用
            flexboxLayout.removeAllViews()
            item.tags.forEachIndexed { index, it ->
                createTagView(flexboxLayout).apply {
                    text = it
                    setOnClickListener {
                        tagClickListener?.invoke(item, index)
                    }
                    flexboxLayout.addView(this)
                }
            }
        }
    }

    /**
     * 创建tag
     */
    private fun createTagView(flb: FlexboxLayout): TextView {
        return LayoutInflater.from(flb.context).inflate(R.layout.item_tag, flb, false) as TextView
    }

    /**
     * 设置标签点击事件
     */
    fun setOnTagClick(listener: (T, Int) -> Unit) {
        tagClickListener = listener
    }
}