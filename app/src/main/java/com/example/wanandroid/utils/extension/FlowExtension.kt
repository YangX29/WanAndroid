package com.example.wanandroid.utils.extension

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

/**
 * [flowWithLifecycle]的简化调用
 */
@OptIn(InternalCoroutinesApi::class)
suspend fun <T> Flow<T>.collectWithLifecycle(
    lifecycle: Lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    action: (T) -> Unit,
) {
    flowWithLifecycle(lifecycle, minActiveState).collect(FlowCollector {
        action.invoke(it)
    })
}