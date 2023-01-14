package com.example.module_common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.module_common.utils.view_binding.VBUtil

/**
 * @author: Yang
 * @date: 2023/1/11
 * @description: 基于ViewBinding实现Activity基类
 */
abstract class BaseVBActivity<VB : ViewBinding> : AppCompatActivity() {

    private var _binding: VB? = null
    protected val mBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //绑定ViewBinding
        _binding = VBUtil.inflate(this, layoutInflater)
        //设置界面布局
        setContentView(_binding?.root)
    }

}