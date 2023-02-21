package com.example.wanandroid.base

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.viewbinding.ViewBinding
import com.example.module_common.base.BaseVBActivity
import com.gyf.immersionbar.ImmersionBar

/**
 * @author: Yang
 * @date: 2023/2/21
 * @description: WanAndroid项目Activity基类，继承[BaseVBActivity]
 */
abstract class BaseActivity<VB : ViewBinding> : BaseVBActivity<VB>() {

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置沉浸式
        setImmersionBar()
        //适配沉浸式状态栏
        adaptImmersion()
    }

    /**
     * 设置沉浸式状态栏
     */
    open fun setImmersionBar() {
        ImmersionBar.with(this)
            .statusBarDarkFont(statusBarDarkFont(), 0.2f)
            .init()
    }

    /**
     * 沉浸式适配
     */
    open fun adaptImmersion() {
        //默认设置fitsSystemWindows
        mBinding.root.fitsSystemWindows = true
    }

    /**
     * 状态栏文字颜色，默认为深色模式
     */
    open fun statusBarDarkFont() = true


}