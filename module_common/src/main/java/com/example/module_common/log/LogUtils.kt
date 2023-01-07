package com.example.module_common.utils.log

/**
 * @author: Yang
 * @date: 2023/1/7
 * @description: 日志工具类
 */
object LogUtils {

    /**
     * 打印json数据
     */
    fun printJson(tag: String? = null, json: String) {
        // TODO json字符串格式化
        //打印日志
        MLog.d(tag, json)
    }

}