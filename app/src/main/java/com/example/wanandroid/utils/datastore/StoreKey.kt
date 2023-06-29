package com.example.wanandroid.utils.datastore

import androidx.datastore.preferences.core.stringPreferencesKey

/**
 * @author: Yang
 * @date: 2023/3/30
 * @description: DataStore键值
 */
object StoreKey {
    //用户密码
    val KEY_PASSWORD = stringPreferencesKey("key_password")

    //用户名
    val KEY_ACCOUNT = stringPreferencesKey("key_account")

    //用户信息
    val KEY_USER_INFO = stringPreferencesKey("key_user_info")

    //用户积分信息
    val KEY_COIN_INFO = stringPreferencesKey("key_user_coin_info")

    //搜索记录
    val KEY_SEARCH_HISTORY = stringPreferencesKey("key_search_history")
}