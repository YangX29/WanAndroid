package com.example.module_common.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.example.module_common.utils.view_binding.VBUtil

/**
 * @author: Yang
 * @date: 2023/1/14
 * @description: 基于ViewBinding实现DialogFragment基类
 */
abstract class BaseVBDialogFragment<VB : ViewBinding> : DialogFragment() {

    private var _binding: VB? = null
    protected val mBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 绑定ViewBinding
        _binding = VBUtil.inflate(this, layoutInflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // window设置
        dialog?.window?.apply {
            // 设置背景透明
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            // 设置全屏，如果layout宽高为match需要设置为全屏
            if (isFullScreen()) {
                setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT
                )
                // 沉浸式
                navigationBarColor = Color.TRANSPARENT
                setType(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL)
            }
        }
    }

    open fun isFullScreen() = true

}