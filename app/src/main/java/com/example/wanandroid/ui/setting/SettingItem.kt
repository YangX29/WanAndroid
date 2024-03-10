package com.example.wanandroid.ui.setting

import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * @author: Yang
 * @date: 2023/4/26
 * @description: 设置列表Item模型
 */
sealed class SettingItem : MultiItemEntity {

    companion object {
        const val ITEM_TYPE_COMMON = 1
        const val ITEM_TYPE_SWITCH = 2
        const val ITEM_TYPE_LOGOUT = 3
    }

    //设置项类型
    enum class Type {
        ABOUT, CLEAR_CACHE, THEME, VERSION, HISTORY, NOTIFY, TOP_ARTICLE, BANNER, POLICY, SHORTCUT, WIDGET
    }

    //通用类型
    data class Common(
        val type: Type,
        val title: String,
        val sub: String? = null,
        val desc: String? = null
    ) : SettingItem() {
        override val itemType = ITEM_TYPE_COMMON
    }

    //切换按钮类型
    data class Switch(
        val type: Type,
        val title: String,
        var on: Boolean,
        val sub: String? = null,
        val desc: String? = null
    ) : SettingItem() {
        override val itemType = ITEM_TYPE_SWITCH
    }

    //退出登录类型
    object Logout : SettingItem() {
        override val itemType = ITEM_TYPE_LOGOUT
    }
}