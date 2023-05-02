package com.example.wanandroid.mvi.message

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wanandroid.model.Message
import com.example.wanandroid.mvi.list.SimpleListViewModel

/**
 * @author: Yang
 * @date: 2023/5/2
 * @description: 列表ViewModel
 */
class MessageListViewModel(private val type: String) : SimpleListViewModel<Message>() {

    class Factory(val type: String) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MessageListViewModel(type) as T
        }
    }

    override suspend fun getList() = apiService.getMessageList(type, page?.page ?: 1)


}