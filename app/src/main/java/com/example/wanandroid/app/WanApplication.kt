package com.example.wanandroid.app

import android.app.Application
import android.content.Context
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.alibaba.android.arouter.launcher.ARouter
import com.example.module_common.utils.log.MLog
import com.example.module_common.utils.log.logI
import com.example.module_common.utils.sp.SPUtils
import com.example.wanandroid.BuildConfig
import com.example.wanandroid.net.WanNetManager
import com.example.wanandroid.utils.app.ActivityStackManager
import com.example.wanandroid.utils.image.WanImageLoader
import com.tencent.smtt.export.external.TbsCoreSettings
import com.tencent.smtt.sdk.QbSdk
import com.tencent.smtt.sdk.QbSdk.PreInitCallback
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
        //初始化工具类
        initUtils()
        //初始化第三方库
        initLibrary()
        //TODO Activity堆栈工具类初始化
    }

    override fun newImageLoader(): ImageLoader {
        //默认的ImageLoader
        return WanImageLoader.create(this)
    }

    /**
     * 初始化工具类
     */
    private fun initUtils() {
        //日志
        MLog.openLog(BuildConfig.DEBUG)
        //注册Activity堆栈管理类
        ActivityStackManager.register(this)
        //SP工具类
        SPUtils.init(context)
        //初始化网络
        WanNetManager.init()
    }

    /**
     * 初始化第三方库
     */
    private fun initLibrary() {
        //初始化Arouter
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
        //初始化x5
        initX5()
    }

    /**
     * 初始化x5
     */
    private fun initX5() {
        //初始化x5内核
        QbSdk.initX5Environment(this, object : PreInitCallback {
            override fun onCoreInitFinished() {
                logI(TAG, "onCoreInitFinished")
            }

            override fun onViewInitFinished(p0: Boolean) {
                logI(TAG, "onViewInitFinished, Core is x5:${p0}")
            }
        })
        //是否使用数据流量下载
        QbSdk.setDownloadWithoutWifi(false)
        //初始化x5设置，开启多进程优化
        val settings = mutableMapOf<String, Any>()
        settings[TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER] = true
        settings[TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE] = true
        QbSdk.initTbsSettings(settings)
    }

}