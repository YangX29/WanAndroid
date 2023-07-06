package com.example.wanandroid.utils.red_dot

import com.example.module_common.utils.red_dot.RedDotManager

/**
 * @author: Yang
 * @date: 2023/7/5
 * @description: WanAndroid红点注册器
 */
object WanRedDotRegister {

    /**
     * 注册WanAndroid红点
     */
    fun registerWARedDot() {
        RedDotManager.register(WanRedDotType.TAB_HOME, RedDotManager.ROOT)
        RedDotManager.register(WanRedDotType.TAB_GUIDE, RedDotManager.ROOT)
        RedDotManager.register(WanRedDotType.TAB_OFFICIAL, RedDotManager.ROOT)
        RedDotManager.register(WanRedDotType.TAB_PROJECT, RedDotManager.ROOT)
        registerMineTabRedDot()
    }

    /**
     * 我的tab红点
     */
    private fun registerMineTabRedDot() {
        RedDotManager.register(WanRedDotType.TAB_MINE, RedDotManager.ROOT)
        RedDotManager.register(WanRedDotType.MINE_MESSAGE, WanRedDotType.TAB_MINE)
    }

}