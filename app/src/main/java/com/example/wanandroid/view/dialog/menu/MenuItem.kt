package com.example.wanandroid.view.dialog.menu

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

/**
 * @author: Yang
 * @date: 2023/9/2
 * @description: 菜单item
 */
data class MenuItem(
    @DrawableRes val icon: Int,
    val text: String,
    @ColorRes val iconTint: Int? = null
)
