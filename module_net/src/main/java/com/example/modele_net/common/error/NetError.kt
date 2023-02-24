package com.example.modele_net.common.error

/**
 * @author: Yang
 * @date: 2023/1/7
 * @description: 接口报错信息
 */
class NetError(
    val code: Int,
    val message: String
) {

    override fun toString(): String {
        return "code:$code, message:$message"
    }

}