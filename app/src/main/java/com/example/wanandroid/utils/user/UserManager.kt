package com.example.wanandroid.utils.user

import com.example.wanandroid.model.UserInfo
import com.example.wanandroid.utils.datastore.DataStoreUtils
import com.example.wanandroid.utils.datastore.StoreKey
import kotlinx.coroutines.flow.Flow

/**
 * @author: Yang
 * @date: 2023/4/2
 * @description: 用户管理类
 */
object UserManager {

    //用户信息
    private var userInfo: Flow<UserInfo?>

    init {
        userInfo = getUserInfo()
    }


    /**
     * 是否已登录
     */
    fun isLogin(): Flow<Boolean> {
        return DataStoreUtils.hasData(StoreKey.KEY_USER_INFO)
    }

    /**
     * 登录
     */
    suspend fun login(userInfo: UserInfo?) {
        //保存用户信息
        userInfo?.let {
            DataStoreUtils.putObjectSuspend(StoreKey.KEY_USER_INFO, it)
        }
        //TODO 发送登录成功Event
    }

    /**
     * 退出登录
     */
    fun logout() {
        //清除用户信息
        DataStoreUtils.removeData(StoreKey.KEY_USER_INFO)
        //TODO 发送退出登录Event
    }

    /**
     * 获取用户信息
     */
    fun getUserInfo(): Flow<UserInfo?> {
        return DataStoreUtils.getObject(StoreKey.KEY_USER_INFO)
    }

}