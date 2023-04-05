package com.example.wanandroid.view.widget.loading

/**
 * @author: Yang
 * @date: 2023/4/3
 * @description: 通用loading接口
 */
interface ILoading {
    fun show()
    fun dismiss()
    fun isShowing(): Boolean
}