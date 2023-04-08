package com.example.wanandroid.utils.user

import com.example.wanandroid.model.UserInfo
import com.example.wanandroid.utils.datastore.DataStoreUtils
import com.example.wanandroid.utils.datastore.StoreKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

/**
 * @author: Yang
 * @date: 2023/4/2
 * @description: 用户管理类
 */
object UserManager {

    //用户信息
    private var userInfo: Flow<UserInfo?>

    //已登录
    var hasLogin: Boolean = false
        private set

    //协程实例
    private val scope: CoroutineScope by lazy { CoroutineScope(SupervisorJob() + Dispatchers.IO) }

    init {
        userInfo = getUserInfo()
        scope.launch {
            userInfo.flowOn(Dispatchers.Main).collect {
                //TODO 可能不准确
                hasLogin = it != null
            }
        }
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
        DataStoreUtils.removeData(StoreKey.KEY_USER_INFO) {
            //TODO 发送退出登录Event
        }
    }

    /**
     * 获取用户信息
     */
    fun getUserInfo(): Flow<UserInfo?> {
        return DataStoreUtils.getObject(StoreKey.KEY_USER_INFO)
    }

}