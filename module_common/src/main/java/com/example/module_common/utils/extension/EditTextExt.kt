package com.example.module_common.utils.extension

import android.content.Context
import android.graphics.Typeface
import android.text.method.PasswordTransformationMethod
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * 显示软键盘
 */
fun EditText.showSoftInput() {
    if (requestFocus()) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }
}

/**
 * 隐藏软键盘
 */
fun EditText.hideSoftInput(clearFocus: Boolean = false) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
    //移除焦点
    if (clearFocus) clearFocus()
}

/**
 * 文本显示为密码模式
 * @param hide 是否隐藏显示为密码样式
 * @param handleSpace 是否处理圆点间距，默认不处理，圆点间距会比文字间距大
 * @param originTypeface 处理圆点间距时，文字默认字体
 */
fun EditText.passwordStyle(
    hide: Boolean,
    handleSpace: Boolean = false,
    originTypeface: Typeface = Typeface.DEFAULT
) {
    val oldStart = selectionStart
    val oldEnd = selectionEnd
    transformationMethod = if (hide) {
        PasswordTransformationMethod.getInstance()
    } else {
        null
    }
    //设置光标位置
    setSelection(oldStart, oldEnd)
    //是否处理圆点间距
    if (handleSpace) {
        //修改密码字体，避免圆点间距过大，恢复明文时需要把字体修改为默认
        typeface = if (hide) Typeface.MONOSPACE else originTypeface
    }
}

/**
 * 软键盘点击完成按钮事件，会覆盖[EditText.setOnEditorActionListener]方法，[EditorInfo.IME_ACTION_DONE]
 */
inline fun EditText.doOnDoneAction(crossinline doOnDone: ((KeyEvent?) -> Boolean) = { true }) {
    doOnAction(doOnDone = doOnDone)
}

/**
 * 软键盘点击完成按钮事件，会覆盖[EditText.setOnEditorActionListener]方法，[EditorInfo.IME_ACTION_GO]
 */
inline fun EditText.doOnGoAction(crossinline doOnGo: ((KeyEvent?) -> Boolean) = { true }) {
    doOnAction(doOnGo = doOnGo)
}

/**
 * 软键盘点击完成按钮事件，会覆盖[EditText.setOnEditorActionListener]方法，[EditorInfo.IME_ACTION_NEXT]
 */
inline fun EditText.doOnNextAction(crossinline doOnNext: ((KeyEvent?) -> Boolean) = { true }) {
    doOnAction(doOnNext = doOnNext)
}

/**
 * 软键盘点击完成按钮事件，会覆盖[EditText.setOnEditorActionListener]方法，[EditorInfo.IME_ACTION_SEARCH]
 */
inline fun EditText.doOnSearchAction(crossinline doOnSearch: ((KeyEvent?) -> Boolean) = { true }) {
    doOnAction(doOnSearch = doOnSearch)
}

/**
 * 软键盘点击完成按钮事件，会覆盖[EditText.setOnEditorActionListener]方法，[EditorInfo.IME_ACTION_SEND]
 */
inline fun EditText.doOnSendAction(crossinline doOnSend: ((KeyEvent?) -> Boolean) = { true }) {
    doOnAction(doOnSend = doOnSend)
}

/**
 * 软键盘点击完成按钮事件，会覆盖[EditText.setOnEditorActionListener]方法，[EditorInfo.IME_ACTION_PREVIOUS]
 */
inline fun EditText.doOnPreviousAction(crossinline doOnPrevious: ((KeyEvent?) -> Boolean) = { true }) {
    doOnAction(doOnPrevious = doOnPrevious)
}


/**
 * 键盘action点击事件，会覆盖[EditText.setOnEditorActionListener]方法
 */
inline fun EditText.doOnAction(
    crossinline doOnDone: ((KeyEvent?) -> Boolean) = { true },
    crossinline doOnGo: ((KeyEvent?) -> Boolean) = { true },
    crossinline doOnNext: ((KeyEvent?) -> Boolean) = { true },
    crossinline doOnSearch: ((KeyEvent?) -> Boolean) = { true },
    crossinline doOnSend: ((KeyEvent?) -> Boolean) = { true },
    crossinline doOnPrevious: ((KeyEvent?) -> Boolean) = { true },
) {
    setOnEditorActionListener { _, actionId, event ->
        return@setOnEditorActionListener when (actionId) {
            EditorInfo.IME_ACTION_DONE -> {
                doOnDone.invoke(event)
            }

            EditorInfo.IME_ACTION_GO -> {
                doOnGo.invoke(event)
            }

            EditorInfo.IME_ACTION_NEXT -> {
                doOnNext.invoke(event)
            }

            EditorInfo.IME_ACTION_SEARCH -> {
                doOnSearch.invoke(event)
            }

            EditorInfo.IME_ACTION_SEND -> {
                doOnSend.invoke(event)
            }

            EditorInfo.IME_ACTION_PREVIOUS -> {
                doOnPrevious.invoke(event)
            }

            else -> {
                true
            }
        }
    }
}