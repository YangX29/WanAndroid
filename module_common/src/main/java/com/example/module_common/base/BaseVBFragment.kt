package com.example.module_common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.module_common.utils.view_binding.VBUtil

/**
 * @author: Yang
 * @date: 2023/1/11
 * @description: 基于ViewBinding的Fragment基类
 */
abstract class BaseVBFragment<VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null
    protected val mBinding get() = _binding!!

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //绑定ViewBinding
        _binding = VBUtil.inflate(this, layoutInflater, container, false)
        return _binding?.root
    }

    @CallSuper
    override fun onDestroyView() {
        //防止内存泄漏
        _binding = null
        super.onDestroyView()
    }

}