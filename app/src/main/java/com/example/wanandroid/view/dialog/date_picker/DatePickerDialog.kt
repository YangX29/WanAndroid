package com.example.wanandroid.view.dialog.date_picker

import android.content.Context
import android.os.Bundle
import android.text.format.DateFormat
import com.example.wanandroid.base.BaseDialog
import com.example.wanandroid.databinding.DialogDatePickerBinding
import com.example.wanandroid.model.TodoModel
import java.util.Calendar
import java.util.Date

/**
 * @author: Yang
 * @date: 2023/9/3
 * @description: 日期选择器弹窗
 */
class DatePickerDialog(context: Context) : BaseDialog<DialogDatePickerBinding>(context) {

    //默认选中时间
    private var defaultDate = Calendar.getInstance().time

    //监听
    private var confirmListener: ((String) -> Unit)? = null

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
        mBinding.datePicker.apply {
            Calendar.getInstance().let { calendar ->
                calendar.time = defaultDate
                init(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH),
                    null
                )
            }
        }
        //确定取消按钮
        mBinding.tvCancel.setOnClickListener { dismiss() }
        mBinding.tvConfirm.setOnClickListener {
            val dateStr = Calendar.getInstance().let {
                it.set(
                    mBinding.datePicker.year,
                    mBinding.datePicker.month,
                    mBinding.datePicker.dayOfMonth
                )
                DateFormat.format(TodoModel.FORMAT_DATE, it).toString()
            }
            confirmListener?.invoke(dateStr)
            dismiss()
        }
    }

    /**
     * 设置日期
     */
    fun setDate(date: Date) {
        defaultDate = date
    }

    /**
     * 确定监听
     */
    fun setOnConfirm(listener: (String) -> Unit) {
        confirmListener = listener
    }

}