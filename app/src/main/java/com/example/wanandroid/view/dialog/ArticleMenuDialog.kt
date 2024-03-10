package com.example.wanandroid.view.dialog

import android.content.Context
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wanandroid.R
import com.example.wanandroid.base.BaseDialog
import com.example.wanandroid.databinding.DialogArticleMenuBinding

/**
 * @author: Yang
 * @date: 2023/2/15
 * @description: 文章菜单弹窗
 */
class ArticleMenuDialog(context: Context, private val callback: Callback) :
    BaseDialog<DialogArticleMenuBinding>(context) {

    private lateinit var menuAdapter: ArticleMenuAdapter
    private val menuList = mutableListOf<ArticleMenu>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置动画
        window?.setWindowAnimations(R.style.anim_dialog_translate)
        //初始化菜单
        initMenuList()
        //初始化
        initView()
    }

    /**
     * 初始化菜单列表
     */
    private fun initMenuList() {
        //判断是否已收藏
        if (!callback.hasFavored()) {
            menuList.add(ArticleMenu(MenuType.FAVOR))
        } else {
            menuList.add(ArticleMenu(MenuType.CANCEL_FAVOR))
        }
        //固定到桌面快捷方式
        menuList.add(ArticleMenu(MenuType.SHORTCUT))
        //分享
        menuList.add(ArticleMenu(MenuType.SHARE))
        //复制链接
        menuList.add(ArticleMenu(MenuType.COPY))
        //浏览器打开
        menuList.add(ArticleMenu(MenuType.BROWSER))
        //判断深色模式和浅色模式
        if (callback.isDark()) {
            menuList.add(ArticleMenu(MenuType.DARK))
        } else {
            menuList.add(ArticleMenu(MenuType.LIGHT))
        }
        //举报
        menuList.add(ArticleMenu(MenuType.REPORT))
        //更多
        menuList.add(ArticleMenu(MenuType.MORE))
    }

    /**
     * 初始化
     */
    private fun initView() {
        //退出按钮
        mBinding.tvCancel.setOnClickListener { dismiss() }
        //菜单列表
        menuAdapter = ArticleMenuAdapter()
        mBinding.rvMenu.layoutManager = GridLayoutManager(context, 5)
        mBinding.rvMenu.adapter = menuAdapter
        menuAdapter.setNewInstance(menuList)
        //菜单点击事件
        menuAdapter.setOnItemClickListener { _, _, position ->
            val item = menuAdapter.getItem(position)
            //处理收藏和深色模式修改
            handleItemClick(item.type, position)
            //回调
            callback.clickMenu(item.type)
            dismiss()
        }
    }

    /**
     * 处理菜单点击事件
     */
    private fun handleItemClick(type: MenuType, position: Int) {
        when (type) {
            MenuType.FAVOR -> menuAdapter.setData(position, ArticleMenu(MenuType.CANCEL_FAVOR))
            MenuType.CANCEL_FAVOR -> menuAdapter.setData(position, ArticleMenu(MenuType.FAVOR))
            MenuType.DARK -> menuAdapter.setData(position, ArticleMenu(MenuType.LIGHT))
            MenuType.LIGHT -> menuAdapter.setData(position, ArticleMenu(MenuType.DARK))
            else -> {}
        }
    }

    /**
     * 菜单回调
     */
    interface Callback {

        /**
         * 点击菜单
         */
        fun clickMenu(type: MenuType)

        /**
         * 是否已收藏
         */
        fun hasFavored(): Boolean

        /**
         * 是否为深色模式
         */
        fun isDark(): Boolean
    }

}