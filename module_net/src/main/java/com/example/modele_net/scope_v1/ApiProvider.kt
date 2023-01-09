package com.example.modele_net.scope_v1

/**
 * @author: Yang
 * @date: 2023/1/7
 * @description: Api生成器
 */
object ApiProvider {

    /**
     * 获取api服务
     */
    inline fun <reified T> api(service : Class<T>, key: String = NetManager.CLIENT_KEY_DEFAULT): T {
        return NetManager.api(service, key)
    }

}