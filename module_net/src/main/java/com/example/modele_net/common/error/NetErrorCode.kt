package com.example.modele_net.common.error

/**
 * @author: Yang
 * @date: 2023/1/8
 * @description:
 */
class NetErrorCode {

    companion object {
        /**
         * 未知错误
         */
        const val UNKNOWN = -1

        /**
         * http异常
         */
        const val HTTP_EXCEPTION = 1

        /**
         * 连接超时
         */
        const val CONNECTION_TIMEOUT = 2

        /**
         * https错误
         */
        const val HTTPS_HANDSHAKE_FAILED = 3

        /**
         * json解析异常
         */
        const val JSON_ERROR = 4

        /**
         * 连接错误
         */
        const val CONNECTION_ERROR = 5

    }

}