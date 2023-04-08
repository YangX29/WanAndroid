package com.example.wanandroid.utils.user

import com.example.wanandroid.model.UserInfo
import com.example.wanandroid.utils.datastore.StoreKey
import com.example.wanandroid.utils.datastore.putDataSuspend
import com.example.wanandroid.utils.datastore.removeDataSuspend
import kotlinx.coroutines.*

/**
 * @author: Yang
 * @date: 2023/4/8
 * @description: 登录注册工具类
 */
object AuthManager {

    /**
     * 登录
     */
    suspend fun login(account: String, password: String?, userInfo: UserInfo?) {
        if (userInfo == null) return
        runBlocking {
            //保存用户名
            val task1 = async { putDataSuspend(StoreKey.KEY_ACCOUNT, account) }
            //保存密码
            val task2 = async {
                if (password.isNullOrEmpty()) {
                    removeDataSuspend(StoreKey.KEY_PASSWORD)
                } else {
                    putDataSuspend(StoreKey.KEY_PASSWORD, password)
                }
            }
            //保存用户信息
            val task3 = async { UserManager.login(userInfo) }
            //并行执行
            task1.await()
            task2.await()
            task3.await()
        }
    }

    /**
     * 退出登录
     */
    fun logout() {
        //删除用户信息
        UserManager.logout()
    }

}