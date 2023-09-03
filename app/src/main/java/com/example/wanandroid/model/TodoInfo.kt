package com.example.wanandroid.model

import android.os.Parcelable
import android.text.format.DateFormat
import com.example.module_common.utils.extension.getStringRes
import com.example.wanandroid.R
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
    val userId: Long
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

}

/**
 * Todo模型，用于更新和创建任务
 */
data class TodoModel(
    var title: String,
    var content: String,
    var date: String,
    var type: Int,
    var status: Int,
    var priority: Int
) {
    companion object {
        const val FORMAT_DATE = "yyyy-MM-dd"

        fun createFromInfo(todoInfo: TodoInfo?): TodoModel {
            return TodoModel(
                todoInfo?.title ?: "",
                todoInfo?.content ?: "",
                todoInfo?.dateStr ?: DateFormat.format(FORMAT_DATE, Calendar.getInstance())
                    .toString(),
                todoInfo?.type ?: 0,
                todoInfo?.status ?: 0,
                todoInfo?.priority ?: 0
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
