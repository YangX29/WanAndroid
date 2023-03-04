package com.example.wanandroid.utils.extension

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

//TODO 指定Dispatchers.DEFAULT会导致闪屏页闪退
fun ViewModel.launch(
    action: suspend CoroutineScope.() -> Unit
) {
    viewModelScope.launch {
        action.invoke(this)
    }
}

fun ViewModel.launchByIo(action: suspend CoroutineScope.() -> Unit) {
    viewModelScope.launch(Dispatchers.IO) {
        action.invoke(this)
    }
}