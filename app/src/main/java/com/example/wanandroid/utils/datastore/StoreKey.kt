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
}