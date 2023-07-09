package com.example.module_common.utils.extension

import android.graphics.Canvas

/**
 * 保存并恢复
 */
fun Canvas.saveAndRestore(action: Canvas.() -> Unit) {
    //保存
    save()
    //画布转换操作
    action()
    //恢复
    restore()
}