package com.example.wanandroid.base

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.viewbinding.ViewBinding
import com.example.module_common.base.BaseVBFragment

/**
 * @author: Yang
 * @date: 2023/2/21
 * @description: WanAndroid项目Fragment基类，继承[BaseVBFragment]
 */
abstract class BaseFragment<VB : ViewBinding> : BaseVBFragment<VB>() {

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}