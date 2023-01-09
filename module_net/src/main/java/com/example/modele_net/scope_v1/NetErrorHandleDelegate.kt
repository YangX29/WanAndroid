package com.example.modele_net.scope_v1

import com.example.modele_net.common.error.NetError

/**
 * @author: Yang
 * @date: 2023/1/7
 * @description: 接口错误处理委托接口
 */
interface NetErrorHandleDelegate {

    /**
     * 服务器返回数据错误处理，注意：此时网络接口调用已成功，该错误为接口返回数据错误，返回错误后会走onFailed方法
     * @see NetExecutor.execute
     */
    fun handleError(result: IResult): NetError?

}