package com.example.wanandroid.ui.todo

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.module_common.utils.extension.getColorRes
import com.example.module_common.utils.extension.getStringRes
import com.example.module_common.utils.extension.isNotNull
import com.example.module_common.utils.extension.visible
import com.example.wanandroid.R
import com.example.wanandroid.model.TodoInfo
import com.example.wanandroid.utils.extension.tintColor
import com.example.wanandroid.utils.extension.tintColorRes
import java.util.Calendar

/**
 * @author: Yang
 * @date: 2023/8/13
 * @description: todo列表
 */
class TodoListAdapter : BaseQuickAdapter<TodoInfo, BaseViewHolder>(R.layout.item_todo),
    LoadMoreModule {

    companion object {
        const val PAYLOAD_CHECK = 1
    }

    init {
        addChildClickViewIds(R.id.ivChecked)
    }

    override fun convert(holder: BaseViewHolder, item: TodoInfo, payloads: List<Any>) {
        super.convert(holder, item, payloads)
        payloads.forEach {
            if (it == PAYLOAD_CHECK) {
                holder.getView<ImageView>(R.id.ivChecked).isSelected = item.isDone
                //时间，已完成时设置为完成时间
                holder.setText(R.id.tvDate, if (item.isDone) getStringRes(R.string.todo_done, item.completeDateStr) else item.dateStr)
            }
        }
    }

    override fun convert(holder: BaseViewHolder, item: TodoInfo) {
        holder.apply {
            //任务是否完成
            getView<ImageView>(R.id.ivChecked).isSelected = item.isDone
            //TODO 标题，已完成划掉
            setText(R.id.tvTitle, item.title)
            //时间，已完成时设置为完成时间
            setText(R.id.tvDate, if (item.isDone) getStringRes(R.string.todo_done, item.completeDateStr) else item.dateStr)
            //是否已添加到日历提醒
            getView<ImageView>(R.id.ivDate).apply {
                visible(item.hasSetCalendarReminder)
                tintColor(if (item.clockCalendar?.before(Calendar.getInstance()) == true) getColorRes(R.color.todo_reminder_expired) else null)
            }
            //设置优先级和标签颜色
            getView<ImageView>(R.id.ivTag).tintColorRes(item.tagColor)
            getView<ImageView>(R.id.ivPriority).tintColorRes(item.priorityColor)
        }
    }

}