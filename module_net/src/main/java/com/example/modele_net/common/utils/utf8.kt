package com.example.modele_net.common.utils

import okio.Buffer
import java.io.EOFException

/**
 * @author: Yang
 * @date: 2023/1/8
 * @description: Returns true if the body in question probably contains human readable text. Uses a small
 * sample of code points to detect unicode control characters commonly used in binary file
 * signatures.
 */
internal fun Buffer.isProbablyUtf8(): Boolean {
    try {
        val prefix = Buffer()
        val byteCount = size.coerceAtMost(64)
        copyTo(prefix, 0, byteCount)
        for (i in 0 until 16) {
            if (prefix.exhausted()) {
                break
            }
            val codePoint = prefix.readUtf8CodePoint()
            if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                return false
            }
        }
        return true
    } catch (_: EOFException) {
        return false // Truncated UTF-8 sequence.
    }
}