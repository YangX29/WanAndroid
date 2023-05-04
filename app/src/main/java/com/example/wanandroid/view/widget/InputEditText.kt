package com.example.wanandroid.view.widget

import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.util.TypedValue
import android.view.KeyEvent
import android.view.LayoutInflater
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doAfterTextChanged
import com.example.module_common.utils.extension.*
import com.example.wanandroid.R
import com.example.wanandroid.databinding.ViewInputEditTextBinding

/**
 * @author: Yang
 * @date: 2023/4/6
 * @description: 通用输入文本框
 */
class InputEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val mBinding = ViewInputEditTextBinding.inflate(LayoutInflater.from(context), this)

    //文字大小
    private val textSize: Float

    //文字颜色
    @ColorInt
    private val textColor: Int

    //提示文案
    private val hint: String

    //提示文字颜色
    @ColorInt
    private val hintTextColor: Int

    //光标
    @DrawableRes
    private val cursor: Int

    //icon
    @DrawableRes
    private val icon: Int

    //icon大小
    private val iconSize: Int

    //icon颜色
    @ColorInt
    private val iconTint: Int

    //文本框padding
    private val inputPadding: Int

    //是否显示密码按钮
    private var showPasswordIcon = false

    //是否显示清除按钮
    private var showClear = true

    //是否为密码类型
    private var isPasswordType = false

    //键盘监听
    private var editorActionListener: ((Int, KeyEvent?) -> Boolean)? = null

    init {
        //初始化参数
        val ta = context.obtainStyledAttributes(attrs, R.styleable.InputEditText)
        textSize = ta.getDimension(R.styleable.InputEditText_input_text_size, 14.sp2px().toFloat())
        textColor = ta.getColor(
            R.styleable.InputEditText_input_text_color,
            context.resources.getColor(R.color.common_content)
        )
        hint = ta.getString(R.styleable.InputEditText_input_hint) ?: ""
        hintTextColor = ta.getColor(
            R.styleable.InputEditText_input_hint_text_color,
            context.resources.getColor(R.color.common_hint)
        )
        cursor =
            ta.getResourceId(R.styleable.InputEditText_input_cursor, R.drawable.drawable_cursor)
        icon = ta.getResourceId(R.styleable.InputEditText_input_icon, -1)
        iconSize = ta.getDimensionPixelSize(R.styleable.InputEditText_input_icon_size, 30.dp2px())
        showPasswordIcon = ta.getBoolean(R.styleable.InputEditText_input_show_password_icon, false)
        showClear = ta.getBoolean(R.styleable.InputEditText_input_show_clear, true)
        isPasswordType = ta.getBoolean(R.styleable.InputEditText_input_password_type, false)
        inputPadding = ta.getDimensionPixelSize(
            R.styleable.InputEditText_input_padding,
            5.dp2px()
        )
        iconTint = ta.getColor(
            R.styleable.InputEditText_input_icon_tint,
            context.resources.getColor(R.color.common_hint)
        )
        ta.recycle()
        //初始化view
        initView()
    }

    /**
     * 初始化参数
     */
    private fun initView() {
        //设置背景
        setBackgroundResource(R.drawable.bg_common_blue_et)
        //输入框
        mBinding.etInput.apply {
            setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
            setTextColor(textColor)
            setPadding(inputPadding, 0, inputPadding, 0)
            //提示
            hint = this@InputEditText.hint
            setHintTextColor(hintTextColor)
            //光标
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                setTextCursorDrawable(cursor)
            }
            //监听输入
            doAfterTextChanged { updateClearButton() }
            //监听软键盘按键
            setOnEditorActionListener { _, actionId, event ->
                return@setOnEditorActionListener editorActionListener?.invoke(actionId, event)
                    ?: false
            }
            //密码类型默认隐藏
            if (isPasswordType) {
                mBinding.etInput.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }
        //icon
        if (icon != -1) {
            mBinding.ivIcon.apply {
                visible()
                //icon
                setImageResource(icon)
                //大小
                changeLayoutParams<LayoutParams> {
                    width = iconSize
                    height = iconSize
                }
            }
        }
        //密码显示icon
        mBinding.ivPassword.apply {
            //颜色
            imageTintList = ColorStateList.valueOf(iconTint)
            //是否显示
            gone(!showPasswordIcon || !isPasswordType)
            //大小
            changeLayoutParams<LayoutParams> {
                width = iconSize
                height = iconSize
            }
            //点击事件
            setOnClickListener { switchInputVisible() }
        }
        //清除按钮
        mBinding.ivClear.apply {
            //颜色
            imageTintList = ColorStateList.valueOf(iconTint)
            //显示
            updateClearButton()
            //大小
            changeLayoutParams<LayoutParams> {
                width = iconSize
                height = iconSize
            }
            //点击事件
            setOnClickListener { clearInput() }
        }
    }

    /**
     * 更新清除按钮
     */
    private fun updateClearButton() {
        if (showClear) {
            val notEmpty = !mBinding.etInput.text.isNullOrEmpty()
            mBinding.ivClear.visible(notEmpty)
        } else {
            mBinding.ivClear.gone()
        }
    }

    /**
     * 切换输入框文字是否可见
     */
    private fun switchInputVisible() {
        if (mBinding.ivPassword.isSelected) {//可见->不可见
            mBinding.ivPassword.isSelected = false
            mBinding.etInput.transformationMethod = PasswordTransformationMethod.getInstance()
        } else {
            mBinding.ivPassword.isSelected = true
            mBinding.etInput.transformationMethod = HideReturnsTransformationMethod.getInstance()
        }
    }

    /**
     * 清除输入
     */
    private fun clearInput() {
        mBinding.etInput.text.clear()
    }

    /**
     * 设置光标位置
     */
    fun setSelection(index: Int) {
        if (index < 0 || index > mBinding.etInput.text.length) return
        mBinding.etInput.setSelection(index)
    }

    /**
     * 获取焦点
     */
    fun requestInputFocus() {
        mBinding.etInput.requestFocus()
    }

    /**
     * 设置键盘监听
     */
    fun setOnEditorActionListener(listener: (Int, KeyEvent?) -> Boolean) {
        editorActionListener = listener
    }

    /**
     * 获取输入
     */
    fun getInputText(): String {
        return mBinding.etInput.text.toString()
    }

    /**
     * 设置输入
     */
    fun setInputText(text: String?) {
        mBinding.etInput.setText(text)
    }

    /**
     * 设置输入类型
     */
    fun setInputType(type: Int) {
        mBinding.etInput.inputType = type
    }

    /**
     * 设置键盘按钮类型
     */
    fun setImeOptions(option: Int) {
        mBinding.etInput.imeOptions = option
    }

}