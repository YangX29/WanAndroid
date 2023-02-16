package com.example.wanandroid.base

import android.content.Context
import androidx.viewbinding.ViewBinding
import com.example.module_common.base.BaseVBDialog

/**
 * @author: Yang
 * @date: 2023/1/31
 * @description: WanAndroid项目Dialog基类，继承[BaseVBDialog]
 */
abstract class BaseDialog<VB : ViewBinding>(context: Context) : BaseVBDialog<VB>(context) {

}