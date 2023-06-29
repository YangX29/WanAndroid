package com.example.wanandroid.view.widget

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import com.example.module_common.utils.extension.dp2px
import com.example.module_common.utils.extension.removeFromParent
import com.example.module_common.utils.extension.visible
import com.example.wanandroid.R
import com.example.wanandroid.databinding.ViewClipboardTipBinding

/**
 * @author: Yang
 * @date: 2023/6/28
 * @description: 截切版提示View
 */
class ClipboardTipView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val mBinding: ViewClipboardTipBinding

    init {
        mBinding = ViewClipboardTipBinding.inflate(LayoutInflater.from(context), this, true)
        initView()
    }

    /**
     * 初始化View
     */
    private fun initView() {
        //关闭
        mBinding.ivCloseClipboard.setOnClickListener { hide() }
        //背景
        mBinding.clClipboard.background =
            roundDrawable(10.dp2px().toFloat(), resources.getColor(R.color.common_background))
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        //显示
        show()
    }

    /**
     * 显示
     */
    fun show() {
        //显示动画
        mBinding.clClipboard.let {
            ObjectAnimator.ofFloat(
                it, "translationX",
                it.translationX, 0f
            ).run {
                doOnStart { _ -> it.visible() }
                duration = 200L
                start()
            }
        }
    }

    /**
     * 隐藏
     */
    fun hide() {
        //隐藏动画
        mBinding.clClipboard.let {
            ObjectAnimator.ofFloat(
                it, "translationX",
                it.translationX, -270.dp2px().toFloat()
            ).run {
                doOnEnd { _ -> it.removeFromParent() }
                duration = 200L
                start()
            }
        }
    }

    /**
     * 设置内容
     */
    fun setContent(content: String, btnText: String, copy: (String) -> Unit) {
        mBinding.tvClipboard.text = content
        mBinding.tvCopy.text = btnText
        mBinding.tvCopy.setOnClickListener {
            hide()
            copy.invoke(content)
        }
    }

}