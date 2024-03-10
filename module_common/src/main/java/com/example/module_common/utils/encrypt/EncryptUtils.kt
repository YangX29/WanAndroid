package com.example.module_common.utils.encrypt

import java.security.MessageDigest

/**
 * @author: Yang
 * @date: 2023/12/17
 * @description: 加密工具类
 */
object EncryptUtils {

    /**
     * md5加密
     */
    fun md5(message: String): String {
        val bytes = MessageDigest.getInstance("MD5").digest(message.toByteArray())
        return bytes.joinToString("") { "%X".format(it) }
    }



}