package com.example.wanandroid.view.dialog.menu

import android.content.Context
import android.os.Bundle
import com.example.module_common.utils.extension.divider
import com.example.module_common.utils.extension.dp2px
import com.example.module_common.utils.extension.linearLayout
import com.example.wanandroid.R
import com.example.wanandroid.base.BaseDialog
import com.example.wanandroid.databinding.DialogMenuBinding

/**
 * @author: Yang
 * @date: 2023/9/2
 * @description: 通用底部菜单弹窗
 */
class MenuDialog(context: Context) : BaseDialog<DialogMenuBinding>(context) {

    private val menuAdapter by lazy { MenuAdapter() }
    private var listener: ((Int) -> Unit)? = null
    private var list = mutableListOf<MenuItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //初始化
        initView()
    }

    /**
     * 初始化View
     */
    private fun initView() {
        //列表
        menuAdapter.setOnItemClickListener { _, _, position ->
            listener?.invoke(position)
            dismiss()
        }
        mBinding.rvMenu.apply {
            linearLayout()
            divider(R.color.common_divider, 0.5f.dp2px().toInt(), 10.dp2px())
            adapter = menuAdapter
        }
        menuAdapter.setList(list)
        //退出按钮
        mBinding.tvCancel.setOnClickListener { dismiss() }
    }

    /**
     * 设置menu
     */
    fun setMenu(list: MutableList<MenuItem>) {
        this.list = list
    }

    /**
     * 设置选择监听
     */
    fun setOnSelectListener(listener: (Int) -> Unit) {
        this.listener = listener
    }

    override fun showDim(): Boolean {
        return true
    }

}