package com.example.module_common.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.CallSuper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import com.example.module_common.utils.view_binding.VBUtils

/**
 * @author: Yang
 * @date: 2023/1/11
 * @description: 基于ViewBinding实现Dialog基类
 */
abstract class BaseVBDialog<VB : ViewBinding>(context: Context) : Dialog(context) {

    private var _binding: VB? = null
    protected val mBinding get() = _binding!!

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //绑定ViewBinding
        _binding = VBUtils.inflate(this, layoutInflater)
        //设置界面布局
        setContentView(_binding!!.root)
        // window设置
        window?.apply {
            // 设置背景透明
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            // 设置全屏，如果layout宽高为match需要设置为全屏
            if (isFullScreen()) {
                setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT
                )
            }
            // 沉浸式
            navigationBarColor = Color.TRANSPARENT
            setType(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL)
            //设置是否显示遮罩
            if (!showDim()) setDimAmount(0f)
        }
        //设置是否点击外部区域关闭
        setCancelable(true)
        setCanceledOnTouchOutside(canceledOnTouchOutside())
        if (canceledOnTouchOutside() && isFullScreen()) {
            mBinding.root.setOnClickListener { cancel() }
        }
    }

    @CallSuper
    override fun dismiss() {
        //防止内存泄漏
        _binding = null
        super.dismiss()
    }

    /**
     * 注册生命周期
     */
    fun registerLifecycle(lifecycleOwner: LifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_DESTROY) {
                if (isShowing) dismiss()
            }
        })
    }

    /**
     * 是否全屏
     */
    open fun isFullScreen() = true

    /**
     * 是否点击外部自动关闭
     */
    open fun canceledOnTouchOutside() = true

    /**
     * 是否显示遮罩
     */
    open fun showDim() = true

}