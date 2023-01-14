package com.example.wanandroid.app

import android.app.Application
import android.content.Context
import com.example.wanandroid.net.WanNetManager
import kotlin.properties.Delegates

/**
 * @author: Yang
 * @date: 2023/1/14
 * @description:
 */
class WanApplication : Application() {

    companion object {
        private const val TAG = "WanApplication"

        //全局context
        var context by Delegates.notNull<Context>()
            private set

        //全局application
        lateinit var instance: WanApplication
    }

    override fun onCreate() {
        super.onCreate()
        //初始化context
        context = applicationContext
        instance = this
        //初始化网络
        WanNetManager.init()
        //TODO Activity堆栈工具类初始化
    }

}