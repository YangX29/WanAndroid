package com.example.wanandroid.utils.extension

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun ViewModel.launch(action: suspend CoroutineScope.() -> Unit) {
    viewModelScope.launch {
        action.invoke(this)
    }
}