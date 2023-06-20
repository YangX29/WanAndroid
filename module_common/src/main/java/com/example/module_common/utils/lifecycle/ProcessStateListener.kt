package com.example.module_common.utils.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ProcessLifecycleOwner

/**
 * @author: Yang
 * @date: 2023/6/19
 * @description: app状态监听接口，通过[ProcessLifecycleOwner]生命周期实现监听，退入后台回调存在700ms延时
 */
interface ProcessStateListener {

    /**
     * app返回前台，[Lifecycle.Event.ON_START]和[Lifecycle.Event.ON_RESUME]
     */
    fun onForeground() {}

    /**
     * app退入后台，[Lifecycle.Event.ON_STOP]和[Lifecycle.Event.ON_PAUSE]
     */
    fun onBackground() {}

    /**
     * 状态变化，通过监听[ProcessLifecycleOwner]生命周期回调，
     * 其中[Lifecycle.Event.ON_CREATE]只会回调一次，
     * [Lifecycle.Event.ON_DESTROY]永远不会回调，
     * [Lifecycle.Event.ON_START]和[Lifecycle.Event.ON_RESUME]在app进入前台时回调，
     * [Lifecycle.Event.ON_STOP]和[Lifecycle.Event.ON_PAUSE]在app进入前台时回调（存在700ms延时）
     */
    fun onStateChange(event: Lifecycle.Event) {}

}