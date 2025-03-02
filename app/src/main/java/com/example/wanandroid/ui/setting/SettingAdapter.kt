package com.example.wanandroid.ui.setting

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.module_common.view.SwitchButton
import com.example.wanandroid.R

/**
 * @author: Yang
 * @date: 2023/4/26
 * @description: 设置列表Adapter
 */
class SettingAdapter : BaseMultiItemQuickAdapter<SettingItem, BaseViewHolder>() {

    //开关切换监听
    private var switchListener: ((SettingItem.Switch, Boolean) -> Unit)? = null

    init {
        //注册类型
        addItemType(SettingItem.ITEM_TYPE_COMMON, R.layout.item_setting_common)
        addItemType(SettingItem.ITEM_TYPE_SWITCH, R.layout.item_setting_switch)
        addItemType(SettingItem.ITEM_TYPE_LOGOUT, R.layout.item_setting_logout)
        addItemType(SettingItem.ITEM_TYPE_SUB_TITLE, R.layout.item_setting_sub_title)
        //处理子View事件
        addChildClickViewIds(R.id.btLogout)
    }

    override fun convert(holder: BaseViewHolder, item: SettingItem) {
        when (item.itemType) {
            SettingItem.ITEM_TYPE_COMMON -> {
                handleCommon(holder, item as SettingItem.Common)
            }

            SettingItem.ITEM_TYPE_SWITCH -> {
                handleSwitch(holder, item as SettingItem.Switch)
            }

            SettingItem.ITEM_TYPE_LOGOUT -> {
                handleLogout(holder, item)
            }

            SettingItem.ITEM_TYPE_SUB_TITLE -> {
                handleSubTitle(holder, item as SettingItem.SubTitle)
            }
        }
    }

    /**
     * 处理副标题item
     */
    private fun handleSubTitle(holder: BaseViewHolder, item: SettingItem.SubTitle) {
        holder.apply {
            //标题
            setText(R.id.tvSubTitle, item.title)
        }
    }

    /**
     * 处理通用item
     */
    private fun handleCommon(holder: BaseViewHolder, item: SettingItem.Common) {
        holder.apply {
            //标题
            setText(R.id.tvTitle, item.title)
            //描述
            setGone(R.id.tvDesc, item.desc.isNullOrEmpty())
            setText(R.id.tvDesc, item.desc)
            //副标题
            setText(R.id.tvSub, item.sub)
        }
    }

    /**
     * 处理开关Item
     */
    private fun handleSwitch(holder: BaseViewHolder, item: SettingItem.Switch) {
        holder.apply {
            //标题
            setText(R.id.tvTitle, item.title)
            //描述
            setGone(R.id.tvDesc, item.desc.isNullOrEmpty())
            setText(R.id.tvDesc, item.desc)
            //开关
            getView<SwitchButton>(R.id.switchButton).apply {
                switch(item.on)
                setOnSwitchListener { switchListener?.invoke(item, it) }
            }
        }
    }

    /**
     * 处理退出登录按钮
     */
    private fun handleLogout(holder: BaseViewHolder, item: SettingItem) {
        //TODO
    }

    /**
     * 设置开关切换监听
     */
    fun setOnItemChildSwitchListener(listener: (SettingItem.Switch, Boolean) -> Unit) {
        switchListener = listener
    }

}