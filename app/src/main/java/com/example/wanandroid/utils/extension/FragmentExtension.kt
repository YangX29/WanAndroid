package com.example.wanandroid.utils.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * 启动协程
 */
fun Fragment.launch(
    action: suspend CoroutineScope.() -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        action.invoke(this)
    }
}

/**
 * 启动协程
 */
fun Fragment.launchWhenCreated(
    action: suspend CoroutineScope.() -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launchWhenCreated {
        action.invoke(this)
    }
}

/**
 * 启动协程
 */
fun Fragment.launchWhenStarted(
    action: suspend CoroutineScope.() -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launchWhenStarted {
        action.invoke(this)
    }
}

/**
 * 启动协程
 */
fun Fragment.launchWhenResumed(
    action: suspend CoroutineScope.() -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launchWhenResumed {
        action.invoke(this)
    }
}

/**
 * 启动协程，检测生命周期状态停止和重启
 */
fun Fragment.launchWithLifecycle(
    state: Lifecycle.State = Lifecycle.State.STARTED,
    action: suspend CoroutineScope.() -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(state) {
            action.invoke(this)
        }
    }
}