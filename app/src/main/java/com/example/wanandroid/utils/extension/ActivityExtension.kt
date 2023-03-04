package com.example.wanandroid.utils.extension

import androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * 启动协程
 */
fun ComponentActivity.launch(
    action: suspend CoroutineScope.() -> Unit
) {
    lifecycleScope.launch {
        action.invoke(this)
    }
}

/**
 * 启动协程
 */
fun ComponentActivity.launchWhenCreated(
    action: suspend CoroutineScope.() -> Unit
) {
    lifecycleScope.launchWhenCreated {
        action.invoke(this)
    }
}

/**
 * 启动协程
 */
fun ComponentActivity.launchWhenStarted(
    action: suspend CoroutineScope.() -> Unit
) {
    lifecycleScope.launchWhenStarted {
        action.invoke(this)
    }
}

/**
 * 启动协程
 */
fun ComponentActivity.launchWhenResumed(
    action: suspend CoroutineScope.() -> Unit
) {
    lifecycleScope.launchWhenResumed {
        action.invoke(this)
    }
}

/**
 * 启动协程，检测生命周期状态
 */
fun ComponentActivity.launchWithLifecycle(
    state: Lifecycle.State = Lifecycle.State.STARTED,
    action: suspend CoroutineScope.() -> Unit
) {
    lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(state) {
            action.invoke(this)
        }
    }
}