package com.example.module_common.utils.log

import android.util.Log
import org.json.JSONArray
import org.json.JSONObject

/**
 * @author: Yang
 * @date: 2023/1/7
 * @description: 日志工具类
 */
object LogUtils {

    /**
     * 格式化缩进空格数量
     */
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
            logD(tag, it)
        }
    }

    /**
     * 输出当前线程调用堆栈
     */
    fun printStack(tag: String = "") {
        val stack = Log.getStackTraceString(Throwable())
        logD(tag, stack)
    }

    /**
     * 获取当前线程调用堆栈
     */
    fun printThreadStack(tag: String = "") {
        val currentThread = Thread.currentThread()
        val log = StringBuilder()
        val stackTrack = currentThread.stackTrace
        stackTrack.forEachIndexed { index, trace ->
            log.append(trace.toString())
            if (index < stackTrack.size - 1) {
                log.append("\n at ")
            }
        }
        logD(tag, log.toString())
    }

}