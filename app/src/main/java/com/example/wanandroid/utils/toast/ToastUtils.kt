package com.example.wanandroid.utils.toast

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.annotation.StringRes
import com.example.wanandroid.app.WanApplication

/**
 * @author: Yang
 * @date: 2023/3/29
 * @description: toast工具类
 */
object ToastUtils {

    //主线程handler
    private val mainHandler by lazy { Handler(Looper.getMainLooper()) }

    /**
     * 显示toast
     */
    fun show(msg: String) {
        show(WanApplication.context, msg)
    }

    /**
     * 显示toast
     */
    fun show(@StringRes msg: Int) {
        show(WanApplication.context, msg)
    }

    /**
     * 显示toast
     */
    fun show(context: Context, msg: String) {
        mainHandler.post {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 显示toast
     */
    fun show(context: Context, @StringRes msg: Int) {
        mainHandler.post {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
    }

}