package com.example.modele_net.scope_v1

import com.example.modele_net.common.error.NetError
import com.example.modele_net.common.error.NetErrorCode
import com.google.gson.JsonParseException
import kotlinx.coroutines.*
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException
import javax.net.ssl.SSLHandshakeException

/**
 * @author: Yang
 * @date: 2023/1/7
 * @description: 接口挂起函数执行工具类，用于除了接口调用的回调，错误处理，线程切换等操作
 */
object NetExecutor {

    /**
     * 接口调用执行方法,挂起函数,可以直接调用顶级方法executeSuspend
     */
    suspend fun <T : IResult> executeSuspend(
        requestCall: (suspend () -> T),
        clientKey: String = NetManager.CLIENT_KEY_DEFAULT
    ): ResultWrapper<T> {
        return try {
            val result = requestCall.invoke()
            //错误处理
            val errorHandler = NetManager.errorHandler(clientKey)
            val error = errorHandler?.handleError(result)
            //构造请求结果
            ResultWrapper(result, error)
        } catch (e: Exception) {
            e.printStackTrace()
            ResultWrapper(null, handleException(e))
        }
    }

    /**
     * 接口调用执行方法,通过回调方式获取结果,可以直接调用顶级方法execute
     */
    fun <T : IResult> execute(
        scope: CoroutineScope,
        requestCall: (suspend () -> T),
        onSuccess: (T) -> Unit,
        onFailed: ((NetError) -> Unit)? = null,
        onStatusChange: ((Status) -> Unit)? = null,
        clientKey: String = NetManager.CLIENT_KEY_DEFAULT
    ) {
        try {
            scope.launch(Dispatchers.IO) {
                onStatusChange?.invoke(Status.LOADING)
                //接口调用
                val result = requestCall.invoke()
                //错误处理
                val errorHandler = NetManager.errorHandler(clientKey)
                val error = errorHandler?.handleError(result)
                //切换到主线程
                withContext(Dispatchers.Main) {
                    //回调处理
                    if (error != null) {
                        onStatusChange?.invoke(Status.FAILED)
                        onFailed?.invoke(error)
                    } else {
                        onStatusChange?.invoke(Status.SUCCESS)
                        onSuccess.invoke(result)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            //失败回调
            onFailed?.invoke(handleException(e))
        }
    }

    /**
     * 异常处理
     */
    private fun handleException(exception: Throwable): NetError {
        //常见异常处理
        return when (exception) {
            is HttpException -> NetError(
                NetErrorCode.HTTP_EXCEPTION,
                "Http exception(${exception.code()}), ${exception.message()}"
            )
            is SocketTimeoutException -> NetError(
                NetErrorCode.CONNECTION_TIMEOUT,
                "Socket connection timeout."
            )
            is SSLHandshakeException -> NetError(
                NetErrorCode.HTTPS_HANDSHAKE_FAILED,
                "Https handshake failed."
            )
            is JSONException, is JsonParseException, is ParseException -> NetError(
                NetErrorCode.JSON_ERROR,
                "Json parse error."
            )
            is UnknownHostException, is ConnectException, is SocketException -> NetError(
                NetErrorCode.CONNECTION_ERROR,
                "Connection error, ${exception.message}"
            )
            else -> NetError(NetErrorCode.UNKNOWN, "Unknown error, ${exception.message}")
        }
    }

}