package com.example.wanandroid.app

import android.app.Application
import android.content.Context
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.alibaba.android.arouter.launcher.ARouter
import com.example.module_common.utils.log.MLog
import com.example.wanandroid.BuildConfig
import com.example.wanandroid.net.WanNetManager
import com.example.wanandroid.utils.WanImageLoader
import kotlin.properties.Delegates

/**
 * @author: Yang
 * @date: 2023/1/14
 * @description:
 */
class WanApplication : Application(), ImageLoaderFactory {

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
        //日志
        MLog.openLog(BuildConfig.DEBUG)
        //初始化网络
        WanNetManager.init()
        //初始化Arouter
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
        //TODO Activity堆栈工具类初始化
    }

    override fun newImageLoader(): ImageLoader {
        //默认的ImageLoader
        return WanImageLoader.create(this)
    }

}