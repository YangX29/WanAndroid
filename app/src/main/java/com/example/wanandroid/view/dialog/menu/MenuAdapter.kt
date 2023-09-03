package com.example.wanandroid.view.dialog.menu

import android.annotation.SuppressLint
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.wanandroid.R
import com.example.wanandroid.utils.extension.tintColorRes

/**
 * @author: Yang
 * @date: 2023/9/2
 * @description: 底部菜单弹窗
 */
class MenuAdapter : BaseQuickAdapter<MenuItem, BaseViewHolder>(R.layout.item_menu) {

    @SuppressLint("UseCompatLoadingForColorStateLists")
    override fun convert(holder: BaseViewHolder, item: MenuItem) {
        holder.apply {
            setText(R.id.tvMenu, item.text)
            getView<ImageView>(R.id.ivIcon).apply {
                if (item.iconTint == null) {
                    setImageResource(item.icon)
                    imageTintList = null
                } else {
                    tintColorRes(item.iconTint)
                }
            }
        }
    }
}