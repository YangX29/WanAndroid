package com.example.module_common.utils.lifecycle

import android.app.Application

/**
 * @author: Yang
 * @date: 2023/6/19
 * @description: app状态变化监听接口，通过[Application.ActivityLifecycleCallbacks]实现
 */
interface AppStateListener {

    /**
     * app返回前台
     */
    fun onForeground() {}

    /**
     * app退入后台
     */
    fun onBackground() {}


}