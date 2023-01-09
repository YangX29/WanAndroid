package com.example.module_common.utils.log

import com.example.module_common.utils.log.MLog
import org.json.JSONArray
import org.json.JSONObject

/**
 * @author: Yang
 * @date: 2023/1/7
 * @description: 日志工具类
 */
object LogUtils {

    private const val INDENT_SPACES = 4

    /**
     * 打印json数据
     */
    fun printJson(tag: String, json: String) {
        val result = StringBuilder()
        // json字符串格式化
        if (json.startsWith("{")) {
            val jsonObject = JSONObject(json)
            result.append(jsonObject.toString(INDENT_SPACES))
        } else if (json.startsWith("[")) {
            val jsonArray = JSONArray(json)
            result.append(jsonArray.toString(INDENT_SPACES))
        } else {
            result.append(json)
        }
        // 分行
        val separator = System.getProperty("line.separator") ?: ""
        val list = result.split(separator)
        // 输出日志
        list.forEach {
            MLog.d(tag, it)
        }
    }

}