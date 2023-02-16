package com.example.wanandroid.view.dialog

import com.example.wanandroid.R

/**
 * @author: Yang
 * @date: 2023/2/16
 * @description: 文章菜单item模型
 */
data class ArticleMenu(
    val type: MenuType
) {

    /**
     * 获取icon图片
     */
    fun getIcon(): Int {
        return when (type) {
            MenuType.FAVOR -> R.drawable.menu_favor
            MenuType.CANCEL_FAVOR -> R.drawable.menu_favor
            MenuType.SHARE -> R.drawable.menu_share
            MenuType.COPY -> R.drawable.menu_favor
            MenuType.BROWSER -> R.drawable.menu_favor
            MenuType.DARK -> R.drawable.menu_favor
            MenuType.LIGHT -> R.drawable.menu_favor
            MenuType.REPORT -> R.drawable.menu_favor
            MenuType.MORE -> R.drawable.menu_favor
        }
    }

    /**
     * 获取按钮文案
     */
    fun getText(): Int {
        return when (type) {
            MenuType.FAVOR -> R.string.menu_favor
            MenuType.CANCEL_FAVOR -> R.string.menu_favor_cancel
            MenuType.SHARE -> R.string.menu_share
            MenuType.COPY -> R.string.menu_copy
            MenuType.BROWSER -> R.string.menu_browser
            MenuType.DARK -> R.string.menu_dark
            MenuType.LIGHT -> R.string.menu_light
            MenuType.REPORT -> R.string.menu_report
            MenuType.MORE -> R.string.menu_more
        }
    }
}

/**
 * 菜单类型
 */
enum class MenuType {
    FAVOR,//收藏
    CANCEL_FAVOR,//已收藏
    SHARE,//分享
    COPY,//复制链接
    BROWSER,//浏览器打开
    DARK,//深色模式
    LIGHT,//浅色模式
    REPORT,//举报
    MORE//更多
}
