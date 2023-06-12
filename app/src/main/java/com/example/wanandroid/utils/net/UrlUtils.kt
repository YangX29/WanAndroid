package com.example.wanandroid.utils.net

import android.util.Patterns
import org.jsoup.Jsoup

/**
 * @author: Yang
 * @date: 2023/6/12
 * @description: url工具类
 */
object UrlUtils {

    /**
     * 通过url获取标题，不要在主线程中调用
     */
    fun getTitleFromUrl(url: String): String? {
        return try {
            val document = Jsoup.connect(url).timeout(10*1000).get()
            val titleLinks = document.select("head")[0].select("title")
            titleLinks[0].text()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 检查url合法性
     */
    fun checkUrl(url: String): Boolean {
        return Patterns.WEB_URL.matcher(url).matches()
    }

}