package com.example.modele_net.common.exception

/**
 * @author: Yang
 * @date: 2023/1/7
 * @description: 网络调用异常类，用于抛出网络调用过程中的异常
 */
class NetException(
    private val code: Int,
    override val message: String,
    override val cause: Throwable? = null) : Throwable(){

    override fun toString(): String {
        return "${code}:${message}"
    }

}