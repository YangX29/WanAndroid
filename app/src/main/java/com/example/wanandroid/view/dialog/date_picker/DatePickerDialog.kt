package com.example.wanandroid.view.dialog.date_picker

import android.content.Context
import android.os.Bundle
import com.example.wanandroid.base.BaseDialog
import com.example.wanandroid.databinding.DialogDatePickerBinding
import java.util.Calendar
import java.util.Date

/**
 * @author: Yang
 * @date: 2023/9/3
 * @description: 日期选择器弹窗
 */
class DatePickerDialog(context: Context) : BaseDialog<DialogDatePickerBinding>(context) {

    //默认选中时间
    private var defaultDate = Calendar.getInstance()

    //监听
    private var confirmListener: ((Calendar) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //初始化
        initView()
    }

    /**
     * 初始化View
     */
    private fun initView() {
        //日期选择器
        mBinding.datePicker.init(
            defaultDate.get(Calendar.YEAR),
            defaultDate.get(Calendar.MONTH),
            defaultDate.get(Calendar.DAY_OF_MONTH),
            null
        )
        //确定取消按钮
        mBinding.tvCancel.setOnClickListener { dismiss() }
        mBinding.tvConfirm.setOnClickListener {
            val calendar = Calendar.getInstance().apply {
                set(
                    mBinding.datePicker.year,
                    mBinding.datePicker.month,
                    mBinding.datePicker.dayOfMonth
                )
            }
            confirmListener?.invoke(calendar)
            dismiss()
        }
    }

    /**
     * 设置日期
     */
    fun setDate(date: Calendar) {
        defaultDate = date
    }

    /**
     * 确定监听
     */
    fun setOnConfirm(listener: (Calendar) -> Unit) {
        confirmListener = listener
    }

}