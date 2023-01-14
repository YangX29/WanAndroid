package com.example.wanandroid.base

import androidx.viewbinding.ViewBinding
import com.example.module_common.base.BaseVBActivity
import com.example.module_common.base.BaseVBFragment

/**
 * @author: Yang
 * @date: 2023/1/14
 * @description: WanAndroid项目Fragment基类，继承[BaseVBActivity]
 */
abstract class BaseFragment<VB : ViewBinding> : BaseVBFragment<VB>() {

}