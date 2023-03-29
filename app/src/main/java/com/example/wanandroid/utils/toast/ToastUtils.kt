package com.example.wanandroid.utils.toast

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

/**
 * @author: Yang
 * @date: 2023/3/29
 * @description: toast工具类
 */
object ToastUtils {

    /**
     * 显示toast
     */
    fun show(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    /**
     * 显示toast
     */
    fun show(context: Context, @StringRes msg: Int) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

}