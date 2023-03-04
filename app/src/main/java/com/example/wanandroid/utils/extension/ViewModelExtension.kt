package com.example.wanandroid.utils.extension

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

fun ViewModel.launch(
    context: CoroutineContext = Dispatchers.Default,
    action: suspend CoroutineScope.() -> Unit
) {
    viewModelScope.launch(context) {
        action.invoke(this)
    }
}

fun ViewModel.launchByIo(action: suspend CoroutineScope.() -> Unit) {
    viewModelScope.launch(Dispatchers.IO) {
        action.invoke(this)
    }
}