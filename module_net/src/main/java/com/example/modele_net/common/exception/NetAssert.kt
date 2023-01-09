package com.example.modele_net.common.exception

import com.example.modele_net.scope_v1.NetManager

/**
 * @author: Yang
 * @date: 2023/1/7
 * @description: 网络库断言类，用于判断各种异常情况
 */
object NetAssert {

    /**
     * 断言已经初始化
     * @see NetManager.checkInit
     */
    fun assertInitialized() {
        if (!NetManager.checkInit()) {
            throw UninitializedException
        }
    }

    /**
     * 断言该key对应的Client已经初始化
     * @see NetManager.checkClientExist
     * @see NetManager.retrofitClients
     */
    fun assertClientExist(key: String) {
        if (!NetManager.checkClientExist(key)) {
            throw ClientUninitializedException
        }
    }

}