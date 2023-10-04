package com.example.wanandroid.view.dialog.date_picker

import android.content.Context
import android.os.Bundle
import com.example.wanandroid.base.BaseDialog
import com.example.wanandroid.databinding.DialogTimePickerBinding
import java.util.Calendar

/**
 * @author: Yang
 * @date: 2023/10/2
 * @description: 时间选择弹窗
 */
class TimePickerDialog(context: Context) : BaseDialog<DialogTimePickerBinding>(context) {

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
        //时间选择器
        mBinding.timePicker.apply {
            //设置为24小时制
            setIs24HourView(true)
            //设置默认值
            hour = defaultDate.get(Calendar.HOUR_OF_DAY)
            minute = defaultDate.get(Calendar.MINUTE)
            //监听，修改时间
            setOnTimeChangedListener { _, hourOfDay, minute ->
                defaultDate.set(Calendar.HOUR_OF_DAY, hourOfDay)
                defaultDate.set(Calendar.MINUTE, minute)
            }
        }
        //确定取消按钮
        mBinding.tvCancel.setOnClickListener { dismiss() }
        mBinding.tvConfirm.setOnClickListener {
            confirmListener?.invoke(defaultDate)
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