package com.example.module_common.redDot

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

/**
 * 红点管理器
 */
object RedDotManager {

    private const val TAG = "RedDotManager"
    //所有红点集合
    private var redDotMap = mutableMapOf<String, RedDotNode>()
    //所有父节点的根节点
    var ROOT = RedDotNode(RedDotType.ROOT, null)

    init {
        redDotMap[RedDotType.ROOT] = ROOT
    }

    /**
     * 获取红点数量
     */
    fun getRedDotNum(type: String): Int {
        return if (redDotMap.containsKey(type)) {
            Log.e(TAG, "get red dot(${type}) num==>num:${redDotMap[type]?.getRedDotNum()}")
            redDotMap[type]?.getRedDotNum() ?: 0
        } else {
            Log.e(TAG, "the red dot(${type}) has registered")
            0
        }
    }

    /**
     * 判断是否存在红点
     */
    fun isRed(type: String): Boolean {
        return if (redDotMap.containsKey(type)) {
            redDotMap[type]?.isRed() ?: false
        } else {
            Log.e(TAG, "the red dot(${type}) has registered")
            false
        }
    }

    /**
     * 注册红点
     */
    fun register(type: String, parent: RedDotNode) {
        //判断父节点是否存在
        if (redDotMap.containsKey(parent.type)) {
            //判断当前红点类型是否已注册
            if (redDotMap.containsKey(type)) {
                //红点类型已注册
                val old = redDotMap[type] ?: return
                if (old.parent?.type == parent.type) {
                    //该节点已注册
                    Log.e(TAG, "register failed : the red dot has registered")
                } else {
                    //同一个节点注册多个父节点
                    if (old is CompoundRedDotNode) {
                        //当前节点属于复合节点，添加父节点
                        old.addParentNode(parent)
                        //父节点添加子节点
                        redDotMap[parent.type]?.addChildNode(old)
                    } else {
                        //不属于复合节点，生成复合节点
                        val compoundRedDotNode = CompoundRedDotNode(type)
                        compoundRedDotNode.copyTree(old)
                        //添加父节点
                        compoundRedDotNode.addParentNode(parent)
                        compoundRedDotNode.addParentNode(old.parent!!)
                        //更新红点集合
                        redDotMap[type] = compoundRedDotNode
                        //父节点添加子节点
                        redDotMap[parent.type]?.addChildNode(compoundRedDotNode)
                        //原先的父节点更新
                        redDotMap[old.parent.type]?.removeChildNode(old.type)
                        redDotMap[old.parent.type]?.addChildNode(compoundRedDotNode)
                    }
                }
            } else {
                //红点类型未注册
                val node = RedDotNode(type, parent)
                //注册子节点
                redDotMap[parent.type]?.addChildNode(node)
                //添加到红点集合
                redDotMap[type] = node
            }
        } else {
            //父节点未注册
            Log.e(TAG, "register failed : the parent type(${parent.type}) do not register")
        }
    }

    /**
     * 注册红点
     */
    fun register(type: String, parentType: String) {
        if (redDotMap.containsKey(parentType)) {
            //获取父节点，注册
            val parent = redDotMap[parentType] ?: return
            register(type, parent)
        } else {
            Log.e(TAG, "register failed : the parent type(${parentType}) do not register")
        }
    }

    /**
     * 观察红点
     */
    fun observeRedDot(type: String, lifecycleOwner: LifecycleOwner, observer: Observer<Int>) {
        if (redDotMap.containsKey(type)) {
            redDotMap[type]?.observeRedDot(lifecycleOwner, observer)
        } else {
            //红点未注册
            Log.e(TAG, "observe failed : the type(${type}) do not register")
        }
    }

    /**
     * 同步红点数量，理论应该只操作叶子节点
     */
    fun syncRedDot(type: String, num: Int) {
        if (redDotMap.containsKey(type)) {
            val node = redDotMap[type] ?: return
            syncRedDot(node, num)
        } else {
            Log.e(TAG, "sync node failed : the type(${type}) do not register")
        }
    }

    /**
     * 同步红点数量
     */
    private fun syncRedDot(node: RedDotNode, num: Int) {
        Log.e(TAG, "sync node===>type:${node.type}, num:${num}, red:${node.getRedDotNum()}")
        //红点数量修改值
        val offset = num-node.getRedDotNum()
        //数量未修改，终止同步
        if (offset == 0) return
        //设置当前红点数量
        node.setRedDotNum(num)
        Log.e(TAG, "sync node, set current node===>type:${node.type}, red:${node.getRedDotNum()}")
        //同步红点数量
        if (node is CompoundRedDotNode) {
            //复合红点
            node.getParents().forEach {
                val parentType = it.key
                val parentNode = it.value
                if (redDotMap.containsKey(parentType)) {
                    //同步红点数量（syncChildRedDot）
//                    parentNode.addRedDot(offset)
                    Log.e(TAG, "sync parent node===>type:${parentNode.type}, num:${parentNode.getRedDotNum()}, offset:${offset}")
                    //递归同步父节点
                    syncRedDot(parentNode, parentNode.getRedDotNum()+offset)
                } else {
                    Log.e(TAG, "sync parent node failed : the parent type(${parentType}) do not register")
                }
            }
        } else {
            if (node.parent == null) {
                Log.e(TAG, "sync node finish")
            } else {
                //同步父节点数量
//                node.parent.addRedDot(offset)
                Log.e(TAG, "sync parent node===>type:${node.parent.type}, num:${node.parent.getRedDotNum()}, offset:${offset}")
                //递归同步父节点
                syncRedDot(node.parent, node.parent.getRedDotNum()+offset)
            }
        }
    }

    /**
     * 重置所有红点，退出登录时使用
     */
    fun resetAll() {
        ROOT.reset()
        redDotMap.clear()
        redDotMap[RedDotType.ROOT] = ROOT
    }

    /**
     * 输出整个红点树
     */
    fun printAllNodes() {
        printNode(ROOT, 0)
    }

    private fun printNode(node: RedDotNode, level: Int) {
        val stringBuilder = StringBuilder()
        for (i in 0 until level) {
            stringBuilder.append("------")
        }
        Log.e(TAG, "${stringBuilder}${node}")
        if (node.children.isNotEmpty()) {
            node.children.forEach {
                printNode(it.value, level + 1)
            }
        }
    }

}