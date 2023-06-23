package com.example.wanandroid.utils.user

import com.example.wanandroid.model.CoinInfo
import com.example.wanandroid.model.UserInfo
import com.example.wanandroid.utils.datastore.DataStoreUtils
import com.example.wanandroid.utils.datastore.StoreKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

/**
 * @author: Yang
 * @date: 2023/4/2
 * @description: 用户管理类
 */
object UserManager {

    //用户信息
    private var userInfo: Flow<UserInfo?>
    private var coinInfo: Flow<CoinInfo?>

    //已登录
    var hasLogin: Boolean = false
        private set

    //协程实例
    private val scope: CoroutineScope by lazy { CoroutineScope(SupervisorJob() + Dispatchers.IO) }

    init {
        coinInfo = getUserCoinInfo()
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
        //存在key值且值不为null
        return DataStoreUtils.hasData(StoreKey.KEY_USER_INFO)
            .zip(getUserInfo()) { hasValue, info ->
                hasValue && info != null
            }
    }

    /**
     * 登录
     */
    suspend fun login(userInfo: UserInfo?) {
        //保存用户信息
        userInfo?.let {
            DataStoreUtils.putObjectSuspend(StoreKey.KEY_USER_INFO, it)
            hasLogin = true
        }
    }

    /**
     * 退出登录
     */
    fun logout(action: (() -> Unit)? = null) {
        //清除用户信息
        DataStoreUtils.editData {
            it.remove(StoreKey.KEY_USER_INFO)
            it.remove(StoreKey.KEY_COIN_INFO)
            hasLogin = false
            action?.invoke()
        }
    }

    /**
     * 获取用户信息
     */
    fun getUserInfo(): Flow<UserInfo?> {
        return DataStoreUtils.getObject(StoreKey.KEY_USER_INFO)
    }

    /**
     * 用户积分信息
     */
    fun getUserCoinInfo(): Flow<CoinInfo?> {
        return DataStoreUtils.getObject(StoreKey.KEY_COIN_INFO)
    }

    /**
     * 更新用户信息
     */
    fun updateUserInfo(info: UserInfo?) {
        DataStoreUtils.putObject(StoreKey.KEY_USER_INFO, info)
    }

    /**
     * 更新用户积分信息
     */
    fun updateCoinInfo(info: CoinInfo?) {
        DataStoreUtils.putObject(StoreKey.KEY_COIN_INFO, info)
    }

}