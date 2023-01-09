package com.example.module_common.utils.red_dot

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * 红点树节点
 */
open class RedDotNode(val type: String, val parent: RedDotNode?) {

    //子节点
    val children = mutableMapOf<String, RedDotNode>()
    //红点数量，等于子节点红点数量的和
    val num = MutableLiveData<Int>()

    init {
        num.value = 0
    }

    /**
     * 判断是否存在红点
     */
    fun isRed(): Boolean {
        return num.value!!>0
    }

    /**
     * 添加红点
     */
    fun addRedDot(value: Int) {
        num.value = getRedDotNum()+value
    }

    /**
     * 设置红点数量
     */
    fun setRedDotNum(value: Int) {
        num.value = value
    }

    /**
     * 同步子节点红点数量
     */
    fun syncChildRedDot() {
        var value = 0
        children.forEach {
            value += it.value.getRedDotNum()
        }
        num.value = value
    }

    /**
     * 获取红点数量
     */
    fun getRedDotNum(): Int {
        return num.value ?: 0
    }

    /**
     * 添加子节点
     */
    fun addChildNode(child: RedDotNode) {
        children[child.type] = child
    }

    /**
     * 移除子节点
     */
    fun removeChildNode(childType: String) {
        children.remove(childType)
    }

    /**
     * 观察红点
     */
    fun observeRedDot(lifecycleOwner: LifecycleOwner, observer: Observer<Int>) {
        num.observe(lifecycleOwner, observer)
    }

    /**
     * 重置
     */
    fun reset() {
        children.clear()
        num.value = 0
    }

    override fun toString(): String {
        return "RedDotNode: type:${type}, num:${num.value}"
    }

}