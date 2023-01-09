package com.example.modele_net.common.interceptor

import com.example.modele_net.common.utils.isProbablyUtf8
import com.example.modele_net.common.utils.logD
import okhttp3.*
import okhttp3.internal.http.promisesBody
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import okio.GzipSource
import org.json.JSONArray
import org.json.JSONObject
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * @author: Yang
 * @date: 2023/1/8
 * @description: OkHttp日志拦截器，基于[HttpLoggingInterceptor]修改，加强可读性
 *
 */
class LogInterceptor(private val printAfterResponse: Boolean = false) : Interceptor {

    companion object {
        private const val TAG = "LogInterceptor"
        private const val INDENT_SPACES = 4
    }

    @Volatile
    private var headersToRedact = emptySet<String>()

    //分行标记
    private val separator = System.getProperty("line.separator") ?: "\n"

    //所有日志
    private val allLog = StringBuilder()

    override fun intercept(chain: Interceptor.Chain): Response {

        // 获取请求信息
        val request = chain.request()
        val connection = chain.connection()

        // 输出接口请求信息
        printLine(true)
        printRequest(request, connection)
        printLine(false)
        // 发起请求，获取响应
        val startNs = System.nanoTime() //记录耗时
        val response: Response
        try {
            response = chain.proceed(request)
        } catch (e: Exception) {
            if (printAfterResponse) {
                appendLog(allLog, "║ <-- HTTP FAILED: $e")
            } else {
                logD(TAG, "║ <-- HTTP FAILED: $e")
            }
            printLine(false)
            throw e
        }
        // 粗略计算耗时
        printLine(true)
        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)
        // 输出响应数据信息
        printResponse(response, tookMs)
        printLine(false)
        // 统一输出日志
        if (printAfterResponse) {
            printLog(allLog)
        }
        return response
    }

    fun redactHeader(name: String) {
        val newHeadersToRedact = TreeSet(String.CASE_INSENSITIVE_ORDER)
        newHeadersToRedact += headersToRedact
        newHeadersToRedact += name
        headersToRedact = newHeadersToRedact
    }

    /**
     * 输出日志
     */
    private fun printLog(log: StringBuilder) {
        //分行
        val list = log.split(separator)
        //输出日志
        list.forEach log@{
            if (it.isEmpty()) return@log//跳过空字符串
            if (it.startsWith("╔") || it.startsWith("╚")) {
                logD(TAG, it)
            } else {
                logD(TAG, "║ $it")
            }
        }
    }


    /**
     * 打印request数据
     */
    private fun printRequest(request: Request, connection: Connection?) {
        val log = StringBuilder()
        appendLog(log, "Request:")
        //请求基本信息
        log.append(("--> ${request.method} ${request.url}${if (connection != null) " " + connection.protocol() else ""}"))
        val requestBody = request.body
        if (requestBody != null) {
            log.append(" (${requestBody.contentLength()}-byte body)")
        }
        log.append(separator)
        //请求头
        val headers = request.headers
        if (requestBody != null) {
            requestBody.contentType()?.let {
                if (headers["Content-Type"] == null) {
                    appendLog(log, "Content-Type: $it")
                }
            }
            if (requestBody.contentLength() != -1L) {
                if (headers["Content-Length"] == null) {
                    appendLog(log, "Content-Length: ${requestBody.contentLength()}")
                }
            }
        }
        for (i in 0 until headers.size) {
            appendHeader(log, headers, i)
        }
        // 请求信息结束
        if (requestBody == null) {
            appendLog(log, "--> END ${request.method}")
        } else if (bodyHasUnknownEncoding(request.headers)) {
            appendLog(log, "--> END ${request.method} (encoded body omitted)")
        } else if (requestBody.isDuplex()) {
            appendLog(log, "--> END ${request.method} (duplex request body omitted)")
        } else if (requestBody.isOneShot()) {
            appendLog(log, "--> END ${request.method} (one-shot body omitted)")
        } else {
            val buffer = Buffer()
            requestBody.writeTo(buffer)

            val contentType = requestBody.contentType()
            val charset = contentType?.charset(StandardCharsets.UTF_8) ?: StandardCharsets.UTF_8

            if (buffer.isProbablyUtf8()) {
                appendLog(log, buffer.readString(charset))
                appendLog(
                    log,
                    "--> END ${request.method} (${requestBody.contentLength()}-byte body)"
                )
            } else {
                appendLog(
                    log,
                    "--> END ${request.method} (binary ${requestBody.contentLength()}-byte body omitted)"
                )
            }
        }
        //如果未设置响应后输出，直接打印日志，否则先添加到allLog中
        if (!printAfterResponse) {
            printLog(log)
        } else {
            allLog.append(log)
        }
    }

    /**
     * 打印response数据
     */
    private fun printResponse(response: Response, tookMs: Long) {
        val log = StringBuilder()
        appendLog(log, "Response:")
        //基本响应信息
        val responseBody = response.body!!
        val contentLength = responseBody.contentLength()
        val bodySize = if (contentLength != -1L) "$contentLength-byte" else "unknown-length"
        appendLog(
            log,
            "<-- ${response.code}${if (response.message.isEmpty()) "" else ' ' + response.message} ${response.request.url} (${tookMs}ms, $bodySize body)"
        )

        //响应报文头部
        val headers = response.headers
        for (i in 0 until headers.size) {
            appendHeader(log, headers, i)
        }
        //响应报体
        if (!response.promisesBody()) {
            appendLog(log, "<-- END HTTP")
        } else if (bodyHasUnknownEncoding(response.headers)) {
            appendLog(log, "<-- END HTTP (encoded body omitted)")
        } else {
            val source = responseBody.source()
            source.request(Long.MAX_VALUE) // Buffer the entire body.
            var buffer = source.buffer

            var gzippedLength: Long? = null
            if ("gzip".equals(headers["Content-Encoding"], ignoreCase = true)) {
                gzippedLength = buffer.size
                GzipSource(buffer.clone()).use { gzippedResponseBody ->
                    buffer = Buffer()
                    buffer.writeAll(gzippedResponseBody)
                }
            }

            val contentType = responseBody.contentType()
            val charset: Charset =
                contentType?.charset(StandardCharsets.UTF_8) ?: StandardCharsets.UTF_8

            if (!buffer.isProbablyUtf8()) {
                appendLog(log, "<-- END HTTP (binary ${buffer.size}-byte body omitted)")
            } else {
                if (contentLength != 0L) {
                    //添加响应数据
                    appendJson(log, buffer.clone().readString(charset))
                    appendLog(log, separator)
                }

                if (gzippedLength != null) {
                    appendLog(
                        log,
                        "<-- END HTTP (${buffer.size}-byte, $gzippedLength-gzipped-byte body)"
                    )
                } else {
                    appendLog(log, "<-- END HTTP (${buffer.size}-byte body)")
                }
            }
        }
        //如果未设置响应后输出，直接打印日志，否则先添加到allLog中
        if (!printAfterResponse) {
            printLog(log)
        } else {
            allLog.append(log)
        }
    }

    /**
     * 添加json数据
     */
    private fun appendJson(log: StringBuilder, json: String) {
        // json字符串格式化
        if (json.startsWith("{")) {
            val jsonObject = JSONObject(json)
            log.append(jsonObject.toString(INDENT_SPACES))
        } else if (json.startsWith("[")) {
            val jsonArray = JSONArray(json)
            log.append(jsonArray.toString(INDENT_SPACES))
        } else {
            log.append(json)
        }
    }

    /**
     * 打印分割线
     */
    private fun printLine(isTop: Boolean) {
        val log = StringBuilder()
        if (isTop) {
            log.append("╔═══════════════════════════════════════════════════════════════════════════════════════")
        } else {
            log.append("╚═══════════════════════════════════════════════════════════════════════════════════════")
        }
        //是否直接输出
        if (printAfterResponse) {
            appendLog(allLog, log.toString())
        } else {
            logD(TAG, log.toString())
        }
    }

    /**
     * 添加日志，自动添加换行符
     */
    private fun appendLog(log: StringBuilder, str: String, addSeparator: Boolean = true) {
        log.append(str)
        if (addSeparator) {
            log.append(separator)
        }
    }

    /**
     * 添加header
     */
    private fun appendHeader(log: StringBuilder, headers: Headers, index: Int) {
        val value = if (headers.name(index) in headersToRedact) "██" else headers.value(index)
        appendLog(log, "${headers.name(index)}: $value")
    }

    /**
     * 是否包含未知编码格式
     */
    private fun bodyHasUnknownEncoding(headers: Headers): Boolean {
        val contentEncoding = headers["Content-Encoding"] ?: return false
        return !contentEncoding.equals("identity", ignoreCase = true) &&
                !contentEncoding.equals("gzip", ignoreCase = true)
    }

}