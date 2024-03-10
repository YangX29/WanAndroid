package com.example.module_common.utils.extension

import com.example.module_common.utils.encrypt.EncryptUtils

/**
 * md5
 */
fun String.md5(): String {
    return EncryptUtils.md5(this)
}