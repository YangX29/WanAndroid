package com.example.wanandroid.utils.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * 启动协程，检测生命周期状态
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