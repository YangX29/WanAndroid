package com.example.modele_net.common.exception

/**
 * @author: Yang
 * @date: 2023/1/7
 * @description: 网络接口异常错误码
 */
class NetExceptionCode {

    companion object {

        /**
         * 未知错误
         */
        const val UNKNOWN = 1000

        /**
         * 未初始化错误
         */
        const val UNINITIALIZED = 1001

        /**
         * Client未初始化错误
         */
        const val CLIENT_UNINITIALIZED = 1002

    }

}

/**
 * @author: Yang
 * @date: 2023/1/7
 * @description: 网络接口异常信息
 */
class NetExceptionMsg {

    companion object {

        /**
         * 未知错误
         */
        const val UNKNOWN = "Unknown exception"

        /**
         * 未初始化错误
         */
        const val UNINITIALIZED = "Uninitialized exception. Please init the NetManager first."

        /**
         * Client未初始化错误
         */
        const val CLIENT_UNINITIALIZED = "Client Uninitialized exception. Please add the client to NetManager first."

    }

}