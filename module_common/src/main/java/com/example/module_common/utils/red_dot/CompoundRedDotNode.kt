package com.example.module_common.utils.red_dot

/**
 * 复合红点，用于存在多个入口的页面，节点可能存在多个父节点的情况
 */
class CompoundRedDotNode(type: String): RedDotNode(type, null) {

    /**
     * 复合红点父节点集合
     */
    private var compoundParents = mutableMapOf<String, RedDotNode>()

    /**
     * 复制子树
     */
    fun copyTree(node: RedDotNode) {
        children.clear()
        children.putAll(node.children)
        num.value = node.getRedDotNum()
    }

    /**
     * 添加父节点
     */
    fun addParentNode(parent: RedDotNode) {
        if (!compoundParents.containsKey(parent.type)) {
            compoundParents[parent.type] = parent
        }
    }

    /**
     * 获取所有父节点
     */
    fun getParents(): MutableMap<String, RedDotNode> {
        return compoundParents
    }

}