package com.example.wanandroid.utils.notify

import com.example.modele_net.scope_v1.ApiProvider
import com.example.module_common.utils.red_dot.RedDotManager
import com.example.wanandroid.net.CookieManager
import com.example.wanandroid.net.WanAndroidApi
import com.example.wanandroid.utils.red_dot.WanRedDotType
import com.example.wanandroid.utils.user.UserManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * @author: Yang
 * @date: 2023/7/5
 * @description: WanAndroid消息通知管理类
 */
object WanNotifyManager {

    private val scope: CoroutineScope by lazy { CoroutineScope(SupervisorJob() + Dispatchers.IO) }


    /**
     * 检查未读消息
     */
    fun checkUnreadMessage() {
        scope.launch {
            //检查当前登录状态
            val checkLoginTask = async { UserManager.isLogin().first() }
            val checkCookieTask = async { CookieManager.loginExpired() }
            val hasLogin = checkLoginTask.await()
            val cookieExpired = checkCookieTask.await()
            //已登录且未过期，获取未读消息
            if (hasLogin && !cookieExpired) {
                val unreadCount =
                    ApiProvider.api(WanAndroidApi::class.java).getUnreadMsgCount().data ?: 0
                //通知红点更新
                RedDotManager.syncRedDot(WanRedDotType.MINE_MESSAGE, unreadCount)
            }
        }
    }

}