package com.example.modele_net.scope_v1

import com.example.modele_net.common.error.NetError

/**
 * @author: Yang
 * @date: 2023/2/24
 * @description: 接口执行包装类,用于挂起函数执行方法
 */
data class ResultWrapper<T: IResult>(
    val result: T?,
    val netError: NetError?
)
