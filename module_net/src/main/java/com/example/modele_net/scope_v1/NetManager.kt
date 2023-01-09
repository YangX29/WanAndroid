package com.example.modele_net.scope_v1

import com.example.modele_net.common.exception.NetAssert
import com.example.modele_net.common.utils.NetLog
import com.example.modele_net.common.utils.logI

/**
 * @author: Yang
 * @date: 2023/1/7
 * @description: 网络管理器
 */
object NetManager {

    private const val TAG = "NetManager"

    /**
     * 默认client对应的key
     */
    const val CLIENT_KEY_DEFAULT = "default"

    /**
     * [RetrofitClient]映射表，可拥有配置多个[RetrofitClient]，用于存在在多种配置的情况，如多个baseUrl
     */
    private var retrofitClients = mutableMapOf<String, RetrofitClient>()

    /**
     * 初始化
     */
    fun init(client: RetrofitClient) {
        logI(TAG, "NetManger init.")
        retrofitClients[CLIENT_KEY_DEFAULT] = client
    }

    /**
     * 添加client
     */
    fun addClient(key: String, client: RetrofitClient) {
        if (key == CLIENT_KEY_DEFAULT) {//默认key
            logI(TAG, "You are creating a client with default key, the default client will be recover.")
        }
        retrofitClients[key] = client
    }

    /**
     * 移除client
     */
    fun removeClient(key: String, client: RetrofitClient) {
        logI(TAG, "The client[${key}] is removed.")
        retrofitClients.remove(key)
    }

    /**
     * 获取接口服务对象
     */
    fun <T> api(service: Class<T>, key: String = CLIENT_KEY_DEFAULT): T {
        //断言已初始化
        NetAssert.assertInitialized()
        //断言该client已存在
        NetAssert.assertClientExist(key)
        return retrofitClients[key]!!.api(service)
    }

    /**
     * 获取错误处理委托对象
     */
    fun errorHandler(key: String = CLIENT_KEY_DEFAULT): NetErrorHandleDelegate? {
        //断言已初始化
        NetAssert.assertInitialized()
        //断言该client已存在
        NetAssert.assertClientExist(key)
        return retrofitClients[key]?.errorHandler()
    }

    /**
     * 检查是否初始化
     */
    fun checkInit(): Boolean {
        return retrofitClients.isNotEmpty()
    }

    /**
     * 检查client是否已存在
     */
    fun checkClientExist(key: String): Boolean {
        return retrofitClients.containsKey(key)
    }

    /**
     * 是否启用log，默认在DEBUG模式下启用log
     */
    fun openLog(open: Boolean) {
        NetLog.openLog(open)
    }

}