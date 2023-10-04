package com.example.wanandroid.model

import android.os.Parcelable
import android.text.format.DateFormat
import com.example.module_common.utils.app.ContextUtils
import com.example.module_common.utils.extension.getStringRes
import com.example.module_common.utils.extension.isNotNull
import com.example.module_common.utils.extension.isNull
import com.example.module_common.utils.log.logE
import com.example.wanandroid.R
import com.example.wanandroid.app.WanApplication
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.Calendar

/**
 * @author: Yang
 * @date: 2023/8/10
 * @description: 待办事项信息
 */
@Parcelize
data class TodoInfo(
    @SerializedName("completeDate")
    val completeDate: Long,
    @SerializedName("completeDateStr")
    val completeDateStr: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("date")
    val date: Long,
    @SerializedName("dateStr")
    val dateStr: String,
    @SerializedName("id")
    val id: Long,
    @SerializedName("priority")
    val priority: Int,
    @SerializedName("status")
    var status: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: Int,
    @SerializedName("userId")
    val userId: Long,
    var clockCalendar: Calendar? = null//日历提醒时间
) : Parcelable {

    companion object {
        val typeColorList = mutableListOf(
            R.color.todo_tag_0,
            R.color.todo_tag_1,
            R.color.todo_tag_2,
            R.color.todo_tag_3,
            R.color.todo_tag_4,
        )
        val priorityColorList = mutableListOf(
            R.color.todo_priority_1,
            R.color.todo_priority_2,
            R.color.todo_priority_3,
            R.color.todo_priority_4,
        )

        fun getTypeColor(type: Int): Int {
            return if (type in 0..4) typeColorList[type] else R.color.todo_tag_0
        }

        fun getPriorityColor(priority: Int): Int {
            return if (priority in 1..4) priorityColorList[priority - 1] else R.color.todo_priority_4
        }
    }

    //任务是否完成
    var isDone: Boolean
        get() = status == 1
        set(value) {
            status = if (value) 1 else 0
        }

    //标签颜色
    val tagColor: Int
        get() = getTypeColor(type)

    //优先级颜色
    val priorityColor: Int
        get() = getTypeColor(priority)

    //是否已经设置日历提醒
    val hasSetCalendarReminder
        get() = clockCalendar.isNotNull()

}

/**
 * Todo模型，用于更新和创建任务
 */
data class TodoModel(
    val id: Long,
    var title: String,
    var content: String,
    var calendar: Calendar,
    var type: Int,
    var status: Int,
    var priority: Int,
    var hasSetClock: Boolean
) {
    companion object {
        const val FORMAT_DATE = "yyyy-MM-dd"
        const val FORMAT_TIME = "HH:mm"

        fun createFromInfo(todoInfo: TodoInfo?): TodoModel {
            return TodoModel(
                todoInfo?.id ?: -1L,
                todoInfo?.title ?: "",
                todoInfo?.content ?: "",
                Calendar.getInstance().apply {
                    //已设置提醒使用提醒时间，否则使用服务器返回时间
                    (todoInfo?.clockCalendar?.timeInMillis ?: todoInfo?.date)?.let {
                        timeInMillis = it
                    }
                },
                todoInfo?.type ?: 0,
                todoInfo?.status ?: 0,
                todoInfo?.priority ?: 0,
                todoInfo?.clockCalendar.isNotNull()
            )
        }
    }

    //标签
    val tag: String
        get() = if (type == 0) {
            getStringRes(R.string.common_uncategorized)
        } else {
            getStringRes(R.string.todo_tag, type)
        }

    //优先级
    val priorityStr: String
        get() = getStringRes(R.string.todo_priority, priority)

    //日期
    val dateStr: String
        get() = DateFormat.format(FORMAT_DATE, calendar).toString()

    //提醒时间
    val clockStr: String
        get() = if (hasSetClock) {
            DateFormat.format(FORMAT_TIME, calendar).toString()
        } else {
            getStringRes(R.string.todo_set_remind)
        }
}

/**
 * Todo搜索参数
 */
data class TodoSearchParams(
    var status: Int? = null,
    var type: Int? = null,
    var priority: Int? = null,
    var orderBy: Int? = null
) {
    companion object {
        //完成状态
        const val STATUS_DONE = 1

        //未完成状态
        const val STATUS_NOT_DONE = 0

        //完成日期正序
        const val ORDER_BY_DONE_DATE_ASC = 1

        //完成日期倒序
        const val ORDER_BY_DONE_DATE_DESC = 2

        //创建日期正序
        const val ORDER_BY_CREATE_DATE_ASC = 3

        //创建日期倒序
        const val ORDER_BY_CREATE_DATE_DESC = 4

    }
}
