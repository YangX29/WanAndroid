package com.example.module_common.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import androidx.viewbinding.ViewBinding
import com.example.module_common.utils.view_binding.VBUtil

/**
 * @author: Yang
 * @date: 2023/1/11
 * @description:
 */
abstract class BaseVBDialog<VB : ViewBinding>(context: Context) : Dialog(context) {

    private var _binding: VB? = null
    protected val mBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //绑定ViewBinding
        _binding = VBUtil.inflate(this, layoutInflater)
        //设置界面布局
        setContentView(_binding!!.root)
        // 设置全屏
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        // 沉浸式
        window?.navigationBarColor = Color.TRANSPARENT
        window?.setType(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL)
    }

    override fun dismiss() {
        //防止内存泄漏
        _binding = null
        super.dismiss()
    }

}