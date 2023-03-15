package com.example.wanandroid.model

/**
 * @author: Yang
 * @date: 2023/3/15
 * @description: 分类标签
 */
abstract class TagCategory {
    abstract val category: String
    abstract val tags: MutableList<String>
}