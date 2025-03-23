package com.example.module_common.utils.extension

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

/**
 * ON_CREATE回调
 */
inline fun Lifecycle.doOnCreate(
    crossinline action: (LifecycleOwner) -> Unit, doOnlyOnce: Boolean = true
) {
    doOnLife(createAction = action, doOnlyOnce = doOnlyOnce)
}

/**
 * ON_START回调
 */
inline fun Lifecycle.doOnStart(
    crossinline action: (LifecycleOwner) -> Unit, doOnlyOnce: Boolean = true
) {
    doOnLife(startAction = action, doOnlyOnce = doOnlyOnce)
}

/**
 * ON_RESUME回调
 */
inline fun Lifecycle.doOnResume(
    crossinline action: (LifecycleOwner) -> Unit, doOnlyOnce: Boolean = true
) {
    doOnLife(resumeAction = action, doOnlyOnce = doOnlyOnce)
}

/**
 * ON_PAUSE回调
 */
inline fun Lifecycle.doOnPause(
    crossinline action: (LifecycleOwner) -> Unit, doOnlyOnce: Boolean = true
) {
    doOnLife(pauseAction = action, doOnlyOnce = doOnlyOnce)
}

/**
 * ON_STOP回调
 */
inline fun Lifecycle.doOnStop(
    crossinline action: (LifecycleOwner) -> Unit, doOnlyOnce: Boolean = true
) {
    doOnLife(stopAction = action, doOnlyOnce = doOnlyOnce)
}

/**
 * ON_DESTROY回调
 */
inline fun Lifecycle.doOnDestroy(
    crossinline action: (LifecycleOwner) -> Unit, doOnlyOnce: Boolean = true
) {
    doOnLife(destroyAction = action, doOnlyOnce = doOnlyOnce)
}

/**
 * 所有生命周期回调
 */
inline fun Lifecycle.doOnAny(crossinline action: (LifecycleOwner, Lifecycle.Event) -> Unit) {
    addObserver(object : LifecycleEventObserver {
        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            action.invoke(source, event)
        }
    })
}

/**
 * Lifecycle各生命周期回调
 */
inline fun Lifecycle.doOnLife(
    crossinline createAction: ((LifecycleOwner) -> Unit) = {},
    crossinline startAction: ((LifecycleOwner) -> Unit) = {},
    crossinline resumeAction: ((LifecycleOwner) -> Unit) = {},
    crossinline pauseAction: ((LifecycleOwner) -> Unit) = {},
    crossinline stopAction: ((LifecycleOwner) -> Unit) = {},
    crossinline destroyAction: ((LifecycleOwner) -> Unit) = {},
    doOnlyOnce: Boolean
) {
    addObserver(object : DefaultLifecycleObserver {
        override fun onCreate(owner: LifecycleOwner) {
            createAction.invoke(owner)
            if (doOnlyOnce) {
                removeObserver(this)
            }
        }

        override fun onStart(owner: LifecycleOwner) {
            startAction.invoke(owner)
            if (doOnlyOnce) {
                removeObserver(this)
            }
        }

        override fun onResume(owner: LifecycleOwner) {
            resumeAction.invoke(owner)
            if (doOnlyOnce) {
                removeObserver(this)
            }
        }

        override fun onPause(owner: LifecycleOwner) {
            pauseAction.invoke(owner)
            if (doOnlyOnce) {
                removeObserver(this)
            }
        }

        override fun onStop(owner: LifecycleOwner) {
            stopAction.invoke(owner)
            if (doOnlyOnce) {
                removeObserver(this)
            }
        }

        override fun onDestroy(owner: LifecycleOwner) {
            destroyAction.invoke(owner)
            if (doOnlyOnce) {
                removeObserver(this)
            }
        }
    })
}