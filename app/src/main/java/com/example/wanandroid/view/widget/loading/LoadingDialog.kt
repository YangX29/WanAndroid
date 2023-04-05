package com.example.wanandroid.view.widget.loading

import android.content.Context
import android.os.Bundle
import com.example.module_common.utils.extension.dp2px
import com.example.wanandroid.R
import com.example.wanandroid.base.BaseDialog
import com.example.wanandroid.databinding.DialogLoadingBinding
import com.example.wanandroid.utils.view.LoadingManager
import com.example.wanandroid.view.widget.RoundDrawable

/**
 * @author: Yang
 * @date: 2023/4/3
 * @description: 通用loading，推荐直接使用[LoadingManager]
 */
class LoadingDialog(context: Context) : BaseDialog<DialogLoadingBinding>(context), ILoading {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //初始化
        initView()
    }

    override fun canceledOnTouchOutside() = false

    /**
     * 初始化View
     */
    private fun initView() {
        //设置背景
        mBinding.viewBg.background =
            RoundDrawable(
                context.resources.getColor(R.color.white),
                10.dp2px().toFloat(),
                RoundDrawable.Style.RECTANGLE
            )
        //不允许退出
        setCancelable(false)
    }

    /**
     * 设置提示
     */
    fun setHint(hint: String) {
        mBinding.tvHint.text = hint
    }

}